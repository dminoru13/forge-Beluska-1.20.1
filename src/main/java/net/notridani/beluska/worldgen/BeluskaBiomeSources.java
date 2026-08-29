package net.notridani.beluska.worldgen;

import com.mojang.serialization.Codec;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import net.notridani.beluska.Beluska;

public class BeluskaBiomeSources {

    public static final DeferredRegister<Codec<? extends BiomeSource>> BIOME_SOURCES =
            DeferredRegister.create(
                    Registries.BIOME_SOURCE,
                    Beluska.MOD_ID
            );

    public static final RegistryObject<Codec<BeluskaBiomeSource>> BELUSKA =
            BIOME_SOURCES.register(
                    "beluska",
                    () -> BeluskaBiomeSource.CODEC.codec()
            );
}
