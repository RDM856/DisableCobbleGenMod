package com.randomnameffp.disablecobblegenerator.mixin;

import com.randomnameffp.disablecobblegenerator.ParticleSpawner;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlintAndSteelItem.class)
public class FlintAndSteelItemMixin {

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void onCreateBurningSoulSand(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();


        // Проверяем, что кликнули именно по светильнику Джека
        if (!state.is(Blocks.JACK_O_LANTERN) || level.isDay()) {
            return; // пусть работает ванильный код
        }
        if (!level.getBlockState(pos.above()).is(Blocks.LIGHTNING_ROD)) {
            return;
        }

        // Выполняем только на сервере, чтобы не было рассинхрона
        if (!level.isClientSide()) {
            // 1. Ставим песок душ на место светильника
            level.setBlock(pos, Blocks.SOUL_SOIL.defaultBlockState(), 11);

            // 2. Ставим огонь душ сверху, если там воздух

            BlockPos above = pos.above();
            level.setBlock(above, Blocks.SOUL_FIRE.defaultBlockState(), 11);
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
            lightningBolt.setPos(pos.getCenter());
            level.addFreshEntity(lightningBolt);

            // 3. Звук, событие и износ предмета — как в ваниле
            ParticleSpawner.spawnParticle(level, above.getCenter());

            lightningBolt.playSound(SoundEvents.ALLAY_DEATH, 1.0F, 0.6F);
            level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS,
                    1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);

            if (player != null) {
                context.getItemInHand().hurtAndBreak(1, player,
                        LivingEntity.getSlotForHand(context.getHand()));
            }
        }

        // Сообщаем игре, что действие выполнено успешно
        cir.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide()));
    }
}