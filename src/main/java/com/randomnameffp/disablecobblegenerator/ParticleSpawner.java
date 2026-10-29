package com.randomnameffp.disablecobblegenerator;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ParticleSpawner {
    public static void spawnParticle(Level level, Vec3 pos){

        SimpleParticleType[] effects = new SimpleParticleType[] {
                ParticleTypes.SOUL_FIRE_FLAME,
                ParticleTypes.SOUL,
                ParticleTypes.GLOW_SQUID_INK
        };

        for (int i = 0; i < 25; i++) {
            SimpleParticleType effect = effects[i % 3];
            double offsetX = (level.random.nextDouble() - 0.5) * 2.0;
            double offsetY = level.random.nextDouble() * 2.0;
            double offsetZ = (level.random.nextDouble() - 0.5) * 2.0;

            double particleX = pos.x + offsetX;
            double particleY = pos.y + offsetY;
            double particleZ = pos.z + offsetZ;

            ((ServerLevel)level).sendParticles(effect, particleX, particleY, particleZ, 4, 0.1, 0.1,0.1, 0.1);
        }
    }
}
