package dev.giga.morevariants.client;

import dev.giga.morevariants.client.renderer.DarknessVialEntityRenderer;
import dev.giga.morevariants.registry.entity.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class MoreVariantsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntityTypes.DARKNESS_VIAL, DarknessVialEntityRenderer::new);
    }
}
