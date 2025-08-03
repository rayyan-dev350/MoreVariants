package dev.giga.morevariants.registry.entity;

import dev.giga.morevariants.MoreVariants;
import dev.giga.morevariants.entities.DarknessVialEntity;
import dev.giga.morevariants.entities.RootedPearlEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntityTypes {

    public static final RegistryKey<EntityType<?>> DARKNESS_VIAL_KEY = RegistryKey.of(
            RegistryKeys.ENTITY_TYPE,
            Identifier.of(MoreVariants.MOD_ID, "darkness_vial")
    );

    public static final EntityType<DarknessVialEntity> DARKNESS_VIAL = Registry.register(
            Registries.ENTITY_TYPE,
            DARKNESS_VIAL_KEY,
            EntityType.Builder.<DarknessVialEntity>create(DarknessVialEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25F, 0.25F)
                    .maxTrackingRange(64)
                    .trackingTickInterval(10)
                    .build(DARKNESS_VIAL_KEY)
    );

    public static final RegistryKey<EntityType<?>> ROOTED_PEARL_KEY = RegistryKey.of(
            RegistryKeys.ENTITY_TYPE,
            Identifier.of(MoreVariants.MOD_ID, "rooted_pearl")
    );

    public static final EntityType<RootedPearlEntity> ROOTED_PEARL = Registry.register(
            Registries.ENTITY_TYPE,
            ROOTED_PEARL_KEY,
            EntityType.Builder.<RootedPearlEntity>create(RootedPearlEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25F, 0.25F)
                    .maxTrackingRange(64)
                    .trackingTickInterval(10)
                    .build(ROOTED_PEARL_KEY)
    );

    public static void registerEntityTypes() {
        MoreVariants.LOGGER.info("Registering Entity Types.");
    }
}