package dev.giga.morevariants.items;

import dev.giga.morevariants.entities.DarknessVialEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class DarknessVialItem extends Item {

    public DarknessVialItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        world.playSound(null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.NEUTRAL,
                0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));

        if (!world.isClient) {
            DarknessVialEntity darknessVialEntity = new DarknessVialEntity(world, user);
            darknessVialEntity.setItem(itemStack);
            darknessVialEntity.setVelocity(user, user.getPitch(), user.getYaw(), -20.0F, 0.5F, 1.0F);
            world.spawnEntity(darknessVialEntity);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return ActionResult.SUCCESS;
    }
}