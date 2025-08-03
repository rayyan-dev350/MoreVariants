package dev.giga.morevariants.datagen;

import dev.giga.morevariants.registry.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                createShaped(RecipeCategory.BREWING, ModItems.DARKNESS_VIAL)
                        .pattern("GSG")
                        .pattern("GDG")
                        .pattern("GGG")
                        .input('G', Items.GLASS_PANE)
                        .input('D', ModItems.VOID_RESIDUE)
                        .input('S', Items.SCULK_CATALYST)
                        .criterion(hasItem(ModItems.VOID_RESIDUE), conditionsFromItem(ModItems.VOID_RESIDUE))
                        .offerTo(recipeExporter);
            }
        };
    }

    @Override
    public String getName() {
        return "MoreVariant Recipes";
    }
}