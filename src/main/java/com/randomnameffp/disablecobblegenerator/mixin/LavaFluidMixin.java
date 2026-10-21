package com.randomnameffp.disablecobblegenerator.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraft.world.level.LevelAccessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LavaFluid.class)
public abstract class LavaFluidMixin {

    @Inject(
            method = "spreadTo",
            at = @At("HEAD"),
            cancellable = true
    )
    private void examplemod$disableVerticalStoneGeneration(
            LevelAccessor level,
            BlockPos pos,
            BlockState blockState,
            Direction direction,
            FluidState fluidState,
            CallbackInfo ci
    ) {
        // Перехватываем только распространение лавы вниз
        // в блок, содержащий воду.
        if (direction == Direction.DOWN) {
            FluidState fluidstate = level.getFluidState(pos);
            if (fluidstate.is(FluidTags.WATER)) {
                ci.cancel();
            }
        }

    }
}