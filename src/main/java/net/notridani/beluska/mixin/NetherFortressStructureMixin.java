package net.notridani.beluska.mixin;

import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(NetherFortressStructure.class)
public abstract class NetherFortressStructureMixin {

    @ModifyArgs(
            method = "generatePieces",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/levelgen/structure/pieces/StructurePiecesBuilder;moveInsideHeights(Lnet/minecraft/util/RandomSource;II)V"
            ),
            require = 1
    )
    private static void seumod$changeFortressHeight(Args args) {
        args.set(1, 5);   // altura mínima
        args.set(2, 25);  // altura máxima
    }
}