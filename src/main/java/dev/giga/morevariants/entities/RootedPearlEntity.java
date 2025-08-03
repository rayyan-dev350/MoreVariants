package dev.giga.morevariants.entities;

import dev.giga.morevariants.registry.entity.ModEntityTypes;
import dev.giga.morevariants.registry.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RootedPearlEntity extends ThrownItemEntity {

    public RootedPearlEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public RootedPearlEntity(World world, LivingEntity owner) {
        super(ModEntityTypes.ROOTED_PEARL, owner, world, new ItemStack(ModItems.ROOTED_PEARL));
    }

    public RootedPearlEntity(World world, double x, double y, double z) {
        super(ModEntityTypes.ROOTED_PEARL, x, y, z, world, new ItemStack(ModItems.ROOTED_PEARL));
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.ROOTED_PEARL;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.createVineArea();
            this.discard();
        }
    }

    private void createVineArea() {
        World world = this.getWorld();
        BlockPos landingPos = this.getBlockPos();

        // Play impact sound
        world.playSound(null, landingPos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 0.8F);

        // Create particles
        if (world instanceof ServerWorld serverWorld) {
            for (int i = 0; i < 30; ++i) {
                serverWorld.spawnParticles(ParticleTypes.HAPPY_VILLAGER,
                        landingPos.getX() + world.random.nextDouble() * 5 - 2.5,
                        landingPos.getY() + 1,
                        landingPos.getZ() + world.random.nextDouble() * 5 - 2.5,
                        1, 0, 0, 0, 0);
            }
        }

        // Store vine positions for later removal
        List<BlockPos> vinePositions = new ArrayList<>();

        // Create a 5x5 area of vines
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                BlockPos basePos = landingPos.add(x, 0, z);

                // Find the ground level
                BlockPos groundPos = findGroundLevel(world, basePos);
                if (groundPos != null) {
                    // Grow vines upward (2-4 blocks high)
                    int vineHeight = 2 + world.random.nextInt(3);
                    for (int y = 1; y <= vineHeight; y++) {
                        BlockPos vinePos = groundPos.add(0, y, 0);
                        if (world.getBlockState(vinePos).isAir()) {
                            // Place vine block
                            BlockState vineState = Blocks.VINE.getDefaultState()
                                    .with(VineBlock.NORTH, true)
                                    .with(VineBlock.SOUTH, true)
                                    .with(VineBlock.EAST, true)
                                    .with(VineBlock.WEST, true);
                            world.setBlockState(vinePos, vineState);
                            vinePositions.add(vinePos);
                        }
                    }
                }
            }
        }

        // Apply slowness effect to entities in the area
        applySlownessEffect(world, landingPos);

        // Schedule vine removal after 5 seconds (100 ticks)
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.getServer().execute(() -> {
                serverWorld.getServer().execute(() -> {
                    // This creates a delayed task
                    new Thread(() -> {
                        try {
                            Thread.sleep(5000); // 5 seconds
                            serverWorld.getServer().execute(() -> removeVines(serverWorld, vinePositions));
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }).start();
                });
            });
        }
    }

    private BlockPos findGroundLevel(World world, BlockPos startPos) {
        // Look down to find solid ground
        for (int y = 0; y >= -10; y--) {
            BlockPos checkPos = startPos.add(0, y, 0);
            if (!world.getBlockState(checkPos).isAir() && world.getBlockState(checkPos.up()).isAir()) {
                return checkPos;
            }
        }
        // Look up if no ground found below
        for (int y = 1; y <= 10; y++) {
            BlockPos checkPos = startPos.add(0, y, 0);
            if (!world.getBlockState(checkPos).isAir() && world.getBlockState(checkPos.up()).isAir()) {
                return checkPos;
            }
        }
        return startPos; // Fallback to original position
    }

    private void applySlownessEffect(World world, BlockPos center) {
        // Create a 5x5x5 area of effect
        Box effectArea = new Box(
                center.getX() - 2.5, center.getY() - 1, center.getZ() - 2.5,
                center.getX() + 2.5, center.getY() + 4, center.getZ() + 2.5
        );

        List<LivingEntity> entitiesInRange = world.getEntitiesByClass(
                LivingEntity.class, effectArea, LivingEntity::isAlive
        );

        // Apply Slowness IV (80% speed reduction) for 8 seconds
        for (LivingEntity entity : entitiesInRange) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 160, 3)); // Level 4 = 80% reduction

            // Add some visual particles to affected entities
            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.SPORE_BLOSSOM_AIR,
                        entity.getX(), entity.getY() + entity.getHeight() / 2, entity.getZ(),
                        5, 0.3, 0.3, 0.3, 0);
            }
        }
    }

    private void removeVines(ServerWorld world, List<BlockPos> vinePositions) {
        for (BlockPos pos : vinePositions) {
            if (world.getBlockState(pos).getBlock() == Blocks.VINE) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());

                // Add removal particles
                world.spawnParticles(ParticleTypes.ASH,
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        3, 0.3, 0.3, 0.3, 0);
            }
        }

        // Play removal sound
        if (!vinePositions.isEmpty()) {
            BlockPos center = vinePositions.get(vinePositions.size() / 2);
            world.playSound(null, center, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 0.5F, 1.2F);
        }
    }
}