package com.randomnameffp.disablecobblegenerator.mixin;

import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;

import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MovementBehaviour.class)
public interface DrillDropMixin {

    @Inject(
            method = "collectOrDropItem",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/entity/item/ItemEntity"
            ),
            cancellable = true,
            remap = false
    )
    private void examplemod$preventDrillItemDrop(
            MovementContext context,
            ItemStack stack,
            CallbackInfo ci
    ) {
        // Не позволяем создать ItemEntity.
        ci.cancel();
    }
}