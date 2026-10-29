package com.randomnameffp.disablecobblegenerator.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.portal.PortalShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(Entity.class)
public abstract class PortalMixin {

    @Inject(
            method = "setAsInsidePortal",
            at = @At("HEAD"),
            cancellable = true
    )
    public void setAsInsidePortal(Portal portal, BlockPos pos, CallbackInfo ci) {
        ci.cancel();
    }
}