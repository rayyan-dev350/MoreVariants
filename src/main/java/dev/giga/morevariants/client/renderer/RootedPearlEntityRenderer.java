package dev.giga.morevariants.client.renderer;

import dev.giga.morevariants.entities.RootedPearlEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class RootedPearlEntityRenderer extends FlyingItemEntityRenderer<RootedPearlEntity> {

    public RootedPearlEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }
}