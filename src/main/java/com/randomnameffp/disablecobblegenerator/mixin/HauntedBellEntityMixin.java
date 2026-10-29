package com.randomnameffp.disablecobblegenerator.mixin;

import com.randomnameffp.disablecobblegenerator.ParticleSpawner;
import com.simibubi.create.api.behaviour.interaction.ConductorBlockInteractionBehavior;
import com.simibubi.create.content.equipment.bell.HauntedBellBlockEntity;
import com.simibubi.create.content.equipment.bell.HauntedBellPulser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Rotations;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.breeze.Breeze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import net.satisfy.brewery.core.entity.BeerElementalEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

@Mixin(HauntedBellBlockEntity.class)
public abstract class HauntedBellEntityMixin {

    @Inject(method = "ring", at = @At("RETURN"))
    private void disablecobblegenerator$onRing(
            Level world,
            BlockPos pos,
            Direction direction,
            CallbackInfoReturnable<Boolean> cir) {

        if (!cir.getReturnValue()) {
            return;
        }

        if (!(world instanceof ServerLevel serverLevel)) {
            return;
        }
        if(world.isDay()){
            return;
        }

        List<BeerElementalEntity> elementalEntities = world.getEntitiesOfClass(
                BeerElementalEntity.class,
                new AABB(pos).inflate(6.0),
                item -> item.position().distanceTo(
                        Vec3.atCenterOf(pos)
                ) <= 6.0
        );
        for(BeerElementalEntity elementalEntity: elementalEntities){
            toBlaze(elementalEntity);
        }
        List<Breeze> breezeList = world.getEntitiesOfClass(
                Breeze.class,
                new AABB(pos).inflate(6.0),
                item -> item.position().distanceTo(
                        Vec3.atCenterOf(pos)
                ) <= 6.0
        );
        for(Breeze breezes: breezeList){
            toBlaze(breezes);
        }

        List<ItemEntity> itemEntities = world.getEntitiesOfClass(
                ItemEntity.class,
                new AABB(pos).inflate(6.0),
                item -> item.position().distanceTo(
                        Vec3.atCenterOf(pos)
                ) <= 6.0
        );
        for(ItemEntity item: itemEntities){
            ItemStack itemStack = item.getItem();
            ItemStack newItem = null;
            if(itemStack.is(Items.DEAD_BUSH)) {
                newItem = new ItemStack(Items.NETHER_WART);
                newItem.setCount(itemStack.getCount());
            }
//            }else if(itemStack.is(ItemTags.TRIM_TEMPLATES) && itemStack.getCount() == 4){
//                newItem = new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE);

            if(newItem != null){
                item.setItem(newItem);
                ParticleSpawner.spawnParticle(world, item.position());
                item.playSound(SoundEvents.ALLAY_DEATH, 1.0F, 0.6F);
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, world);
                lightningBolt.setPos(item.getX(), item.getY(), item.getZ());
                lightningBolt.setVisualOnly(true);
                world.addFreshEntity(lightningBolt);
            }
        }




    }




    private Blaze toBlaze(Entity entity){
        Blaze blaze = EntityType.BLAZE.create(entity.level());
        if(blaze != null) {
            entity.discard();
            blaze.moveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());

            blaze.yBodyRot = entity.getYRot();

            entity.level().addFreshEntity(blaze);

            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, entity.level());
            lightningBolt.setPos(entity.position());
            lightningBolt.setVisualOnly(true);
            entity.level().addFreshEntity(lightningBolt);
            ParticleSpawner.spawnParticle(blaze.level(), blaze.position());
            blaze.playSound(SoundEvents.ALLAY_DEATH, 1.0F, 0.6F);
        }
        return blaze;
    }

}