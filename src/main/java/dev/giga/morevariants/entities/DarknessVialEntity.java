package dev.giga.morevariants.entities;

import dev.giga.morevariants.registry.entity.ModEntityTypes;
import dev.giga.morevariants.registry.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class DarknessVialEntity extends ThrownItemEntity {

    public DarknessVialEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public DarknessVialEntity(World world, LivingEntity owner) {
        super(ModEntityTypes.DARKNESS_VIAL, owner, world, new ItemStack(ModItems.DARKNESS_VIAL));
    }

    public DarknessVialEntity(World world, double x, double y, double z) {
        super(ModEntityTypes.DARKNESS_VIAL, x, y, z, world, new ItemStack(ModItems.DARKNESS_VIAL));
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.DARKNESS_VIAL;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.splash();
            this.getWorld().syncWorldEvent(2002, this.getBlockPos(), 0x000000); // Black particle color for darkness
            this.discard();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (entityHitResult.getEntity() instanceof LivingEntity livingEntity) {
            // Apply darkness effect directly to hit entity with stronger effect
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 200, 0)); // 10 seconds
        }
    }

    private void splash() {
        World world = this.getWorld();

        // Play splash sound
        world.playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.NEUTRAL, 1.0F, 1.0F);

        // Create splash particles
        for (int i = 0; i < 20; ++i) {
            world.addParticle(ParticleTypes.SMOKE,
                    this.getX() + (world.random.nextDouble() - 0.5) * 2.0,
                    this.getY() + world.random.nextDouble(),
                    this.getZ() + (world.random.nextDouble() - 0.5) * 2.0,
                    (world.random.nextDouble() - 0.5) * 0.2,
                    world.random.nextDouble() * 0.2,
                    (world.random.nextDouble() - 0.5) * 0.2);
        }

        // Apply darkness effect to nearby entities
        Box splashArea = new Box(
                this.getX() - 4.0, this.getY() - 2.0, this.getZ() - 4.0,
                this.getX() + 4.0, this.getY() + 2.0, this.getZ() + 4.0
        );

        List<LivingEntity> entitiesInRange = world.getEntitiesByClass(
                LivingEntity.class, splashArea, LivingEntity::isAlive
        );

        for (LivingEntity entity : entitiesInRange) {
            double distance = entity.squaredDistanceTo(this);
            if (distance < 16.0) { // 4 block radius
                // Stronger effect for closer entities
                int duration = (int) (160 - (distance * 10)); // 8-3 seconds based on distance
                duration = Math.max(duration, 60); // Minimum 3 seconds
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, duration, 0));
            }
        }
    }
}