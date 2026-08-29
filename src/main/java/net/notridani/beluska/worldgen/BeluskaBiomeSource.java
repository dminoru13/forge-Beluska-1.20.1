package net.notridani.beluska.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

import java.util.Map;
import java.util.stream.Stream;

public class BeluskaBiomeSource extends BiomeSource {

    public static final MapCodec<BeluskaBiomeSource> CODEC =
            Codec.unboundedMap(Codec.STRING, Biome.CODEC)
                    .xmap(BeluskaBiomeSource::new, source -> source.biomas)
                    .fieldOf("biomas");

    private final Map<String, Holder<Biome>> biomas;

    public BeluskaBiomeSource(Map<String, Holder<Biome>> biomas) {
        this.biomas = biomas;
    }

    @Override
    protected Codec<? extends BiomeSource> codec() {
        return CODEC.codec();
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return biomas.values().stream();
    }

    private Holder<Biome> bioma(String nome) {
        Holder<Biome> b = biomas.get(nome);
        if (b == null) {
            throw new IllegalStateException("Bioma não encontrado na config: " + nome);
        }
        return b;
    }

    @Override
    public Holder<Biome> getNoiseBiome(
            int quartX,
            int quartY,
            int quartZ,
            Climate.Sampler sampler
    ) {
        Climate.TargetPoint clima = sampler.sample(quartX, quartY, quartZ);

        float profundidade = Climate.unquantizeCoord(clima.depth());
        float temperatura = Climate.unquantizeCoord(clima.temperature());

        int blockY = QuartPos.toBlock(quartY);

        if (blockY > 170) {
            if(profundidade < -1.0f) {
                return bioma("montanha");
            }

            if (temperatura > 0.6) return bioma("taiga_antiga");
            if (temperatura > 0.3) return bioma("praia_de_pedra");
            if (temperatura > -0.2) return bioma("taiga_nevada");
            if (temperatura > -1.8) return bioma("espinhos_de_gelo");

            }

        if (blockY > 150) {
            if (temperatura < -0.7) return bioma("oceano_muito_frio");
            if (temperatura < 0.5) return bioma("oceano_frio");
            return bioma("oceano");
        }

        if (blockY > 100 && blockY < 150) {
           return bioma("oceano_quente");
        }


        return bioma("oceano_frio");
    }
}