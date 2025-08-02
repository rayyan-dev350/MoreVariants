package dev.giga.morevariants.datagen;

import dev.giga.morevariants.registry.item.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.SPORE_SAC, Models.GENERATED);
        itemModelGenerator.register(ModItems.DARKNESS_VIAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.ROOTED_PEARL, Models.GENERATED);
        itemModelGenerator.register(ModItems.VOID_RESIDUE, Models.GENERATED);
    }
}