package com.randomnameffp.disablecobblegenerator.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.OrientedContraptionEntity;
import com.simibubi.create.content.contraptions.mounted.MinecartContraptionItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin({MinecartContraptionItem.class})
public class CanWrenchMixin {
    @ModifyExpressionValue(
            method = "wrenchCanBeUsedToPickUpMinecartContraptions",
            at = @At(value = "INVOKE", target = "Lcom/simibubi/create/api/contraption/ContraptionMovementSetting;isNoPickup(Ljava/util/Collection;)Z")
    )
    private static boolean cannotPickup(boolean original, PlayerInteractEvent.EntityInteract event) {
        if (original) return true;

        Entity entity = event.getTarget();
        if (entity instanceof AbstractContraptionEntity) entity = entity.getVehicle();
        if (!(entity instanceof AbstractMinecart cart)) return false;

        List<Entity> passengers = cart.getPassengers();
        if (passengers.isEmpty() || !(passengers.get(0) instanceof OrientedContraptionEntity oce)) return false;

        Contraption contraption = oce.getContraption();
        CombinedInvWrapper storage = contraption.getStorage().getAllItems();

        for (int slot = 0; slot < storage.getSlots(); slot++) {
            if (!storage.getStackInSlot(slot).isEmpty()) {
                return true;
            }
        }
        return false;
    }

}
