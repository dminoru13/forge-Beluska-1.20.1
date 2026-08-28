package com.seumod.beluska.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

public class BeluskaBiomeSource extends BiomeSource {

    public static final Codec<BeluskaBiomeSource> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(
                            Biome.CODEC.fieldOf("biome")
                                    .forGetter(source -> source.biome)
                    ).apply(instance, BeluskaBiomeSource::new)
            );

    private final Holder<Biome> biome;

    public BeluskaBiomeSource(Holder<Biome> biome) {
        super();
        this.biome = biome;
    }

    @Override
    protected Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    public Holder<Biome> getNoiseBiome(
            int quartX,
            int quartY,
            int quartZ,
            Climate.Sampler sampler
    ) {
        return biome;
    }
}