package dev.giga.morevariants.items;

import dev.giga.morevariants.entities.RootedPearlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class RootedPearlItem extends Item {

    public RootedPearlItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        world.playSound(null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL,
                0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));

        user.getItemCooldownManager().set(this.getDefaultStack(), 20); // 1 second cooldown

        if (!world.isClient) {
            RootedPearlEntity rootedPearlEntity = new RootedPearlEntity(world, user);
            rootedPearlEntity.setItem(itemStack);
            rootedPearlEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(rootedPearlEntity);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return ActionResult.SUCCESS;
    }
}