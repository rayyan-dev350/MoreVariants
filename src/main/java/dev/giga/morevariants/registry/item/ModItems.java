package dev.giga.morevariants.registry.item;

import dev.giga.morevariants.MoreVariants;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {
    public static final Item SPORE_SAC = registerItem("spore_sac", Item::new);
    public static final Item ROOTED_PEARL = registerItem("rooted_pearl", Item::new);
    public static final Item VOID_RESIDUE = registerItem("void_residue", Item::new);
    public static final Item DARKNESS_VIAL = registerItem("darkness_vial", Item::new);

    private static Item registerItem(String name, Function<Item.Settings, Item> function) {
        return Registry.register(Registries.ITEM, Identifier.of(MoreVariants.MOD_ID, name),
                function.apply(new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MoreVariants.MOD_ID, name)))));
    }

    public static void registerModItems() {
        MoreVariants.LOGGER.info("Registering MoreVariants Items.");
    }
}
