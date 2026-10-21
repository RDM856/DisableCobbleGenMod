package com.randomnameffp.disablecobblegenerator.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidInteractionRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidInteractionRegistry.class)
public abstract class LiquidBlockMixin {

    @Inject(method = "canInteract", at = @At("HEAD"), cancellable = true)
    private static void examplemod$disableLavaWaterReaction(
            Level level,
            BlockPos pos,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (!level.getBlockState(pos).getFluidState().is(Fluids.LAVA)
                && !level.getBlockState(pos).getFluidState().is(Fluids.FLOWING_LAVA)) {
            return;
        }

        for (Direction direction : Direction.values()) {
            if (direction == Direction.DOWN) {
                continue;
            }

            BlockPos neighbor = pos.relative(direction);

            if (level.getFluidState(neighbor).is(Fluids.WATER)
                    || level.getFluidState(neighbor).is(Fluids.FLOWING_WATER)) {
                cir.setReturnValue(false);
                return;
            }
        }
    }
}