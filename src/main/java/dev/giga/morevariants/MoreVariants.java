package dev.giga.morevariants;

import dev.giga.morevariants.registry.entity.ModEntityTypes;
import dev.giga.morevariants.registry.group.ModItemGroups;
import dev.giga.morevariants.registry.item.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreVariants implements ModInitializer {
    public static final String MOD_ID = "morevariants";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItems.registerModItems();
        ModItemGroups.registerItemGroups();
        ModEntityTypes.registerEntityTypes();
    }
}
