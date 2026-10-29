package com.randomnameffp.disablecobblegenerator.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(Level.class)
public abstract class MobExplositionMixin {

    @Inject(
            method = "explode",
            at = @At("HEAD"),
            cancellable = true
    )
    public void explode(@Nullable Entity source, double x, double y, double z, float radius, Level.ExplosionInteraction explosionInteraction, CallbackInfoReturnable<Explosion> cir) {
        cir.setReturnValue(null);
    }
}