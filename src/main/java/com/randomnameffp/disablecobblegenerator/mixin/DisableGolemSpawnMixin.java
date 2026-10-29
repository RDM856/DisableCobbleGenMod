package com.randomnameffp.disablecobblegenerator.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public class DisableGolemSpawnMixin {
    @Inject(
        method = "gossip",
        at = @At("HEAD"),
        cancellable = true
    )
    private void gossip(ServerLevel serverLevel, Villager target, long gameTime, CallbackInfo ci) {
        ci.cancel();
    }
}