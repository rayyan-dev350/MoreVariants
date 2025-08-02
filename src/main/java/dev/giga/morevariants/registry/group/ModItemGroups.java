package dev.giga.morevariants.registry.group;

import dev.giga.morevariants.MoreVariants;
import dev.giga.morevariants.registry.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup PINK_GARNET_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(MoreVariants.MOD_ID, "pink_garnet_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.SPORE_SAC))
                    .displayName(Text.translatable("itemgroup.morevariants.more_variants_tab"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.SPORE_SAC);
                        entries.add(ModItems.ROOTED_PEARL);
                        entries.add(ModItems.DARKNESS_VIAL);
                        entries.add(ModItems.VOID_RESIDUE);
                    }).build());

    public static void registerItemGroups() {
        MoreVariants.LOGGER.info("Registering Item Groups.");
    }
}
