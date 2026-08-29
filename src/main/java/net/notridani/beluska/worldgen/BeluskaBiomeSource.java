package net.notridani.beluska.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

import java.util.stream.Stream;

public class BeluskaBiomeSource extends BiomeSource {

    public static final MapCodec<BeluskaBiomeSource> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(

                    Biome.CODEC.fieldOf("oceano")
                            .forGetter(source -> source.oceano),

                    Biome.CODEC.fieldOf("planicie")
                            .forGetter(source -> source.planicie),

                    Biome.CODEC.fieldOf("montanha")
                            .forGetter(source -> source.montanha)

            ).apply(instance, BeluskaBiomeSource::new));

    private final Holder<Biome> oceano;
    private final Holder<Biome> planicie;
    private final Holder<Biome> montanha;

    public BeluskaBiomeSource(
            Holder<Biome> oceano,
            Holder<Biome> planicie,
            Holder<Biome> montanha
    ) {
        this.oceano = oceano;
        this.planicie = planicie;
        this.montanha = montanha;
    }

    @Override
    protected Codec<? extends BiomeSource> codec() {
        return CODEC.codec();
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return Stream.of(
                oceano,
                planicie,
                montanha
        );
    }

    @Override
    public Holder<Biome> getNoiseBiome(
            int quartX,
            int quartY,
            int quartZ,
            Climate.Sampler sampler
    ) {
        Climate.TargetPoint clima =
                sampler.sample(quartX, quartY, quartZ);

        float continente =
                Climate.unquantizeCoord(clima.continentalness());

        float erosao =
                Climate.unquantizeCoord(clima.erosion());

        float profundidade =
                Climate.unquantizeCoord(clima.depth());

        // Oceano
        if (continente < -0.4f) {
            return oceano;
        }

        // Montanha
        if (profundidade < -1.0f) {
            return montanha;
        }

        // Planície
        return planicie;
    }
}
