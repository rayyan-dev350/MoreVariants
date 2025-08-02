package dev.giga.morevariants.datagen;

import dev.giga.morevariants.registry.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLanguageProvider extends FabricLanguageProvider {
    public ModLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.SPORE_SAC, "Spore Sac");
        translationBuilder.add(ModItems.DARKNESS_VIAL, "Darkness Vial");
        translationBuilder.add(ModItems.ROOTED_PEARL, "Rooted Pearl");
        translationBuilder.add(ModItems.VOID_RESIDUE, "Void Residue");

        translationBuilder.add("itemgroup.morevariants.more_variants_tab", "More Variants Tab");
    }
}