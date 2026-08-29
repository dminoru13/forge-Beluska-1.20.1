package net.notridani.beluska.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.QuartPos;

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

                    Biome.CODEC.fieldOf("oceano_quente")
                            .forGetter(source -> source.oceano_quente),

                    Biome.CODEC.fieldOf("oceano_frio")
                            .forGetter(source -> source.oceano_frio),

                    Biome.CODEC.fieldOf("planicie")
                            .forGetter(source -> source.planicie),

                    Biome.CODEC.fieldOf("montanha")
                            .forGetter(source -> source.montanha)

            ).apply(instance, BeluskaBiomeSource::new));

    private final Holder<Biome> oceano;
    private final Holder<Biome> oceano_quente;
    private final Holder<Biome> oceano_frio;
    private final Holder<Biome> planicie;
    private final Holder<Biome> montanha;

    public BeluskaBiomeSource(
            Holder<Biome> oceano,
            Holder<Biome> oceano_quente,
            Holder<Biome> oceano_frio,
            Holder<Biome> planicie,
            Holder<Biome> montanha
    ) {
        this.oceano = oceano;
        this.oceano_quente = oceano_quente;
        this.oceano_frio = oceano_frio;
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
                oceano_quente,
                oceano_frio,
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

        float temperatura =
                Climate.unquantizeCoord(clima.temperature());

        int blockY = QuartPos.toBlock(quartY);

        // Oceano

        if (blockY < 171) {

            if(temperatura > 0.5) {
                return oceano_quente;
            }


            if(temperatura < -0.5) {
                return oceano_frio;
            }


            return oceano;

        }

        if (blockY > 170) {

            // Montanha
            if (profundidade < -1.0f) {
                return montanha;
            }
        }

        // Planície
        return planicie;







    }
}
