package com.randomnameffp.disablecobblegenerator.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.portal.PortalShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PortalShape.class)
public abstract class PortalCreatingMixin {

    @Inject(
            method = "isValid",
            at = @At("HEAD"),
            cancellable = true
    )
    public void isValid(CallbackInfoReturnable<Boolean> ci) {
        ci.setReturnValue(false);
    }
}