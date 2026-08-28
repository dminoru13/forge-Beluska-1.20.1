package com.seunome.beluska.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BeluskaBiomeSources {

    public static final DeferredRegister<Codec<? extends BiomeSource>> BIOME_SOURCES =
            DeferredRegister.create(
                    Registry.BIOME_SOURCE_REGISTRY,
                    "beluska"
            );

    public static final RegistryObject<Codec<BeluskaBiomeSource>> BELUSKA =
            BIOME_SOURCES.register(
                    "beluska",
                    () -> BeluskaBiomeSource.CODEC
            );
}