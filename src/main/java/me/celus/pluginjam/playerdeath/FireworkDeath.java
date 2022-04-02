package me.celus.pluginjam.playerdeath;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkDeath implements PlayerDeath {

    @Override
    public void killPlayer(final Player player) {
        final ThreadLocalRandom rand = ThreadLocalRandom.current();

        final Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
        final FireworkMeta fireworkMeta = firework.getFireworkMeta();
        for (int i = 0; i < 15; i++) {
            fireworkMeta.addEffect(this.getRandomEffect(rand));
        }
        fireworkMeta.setPower(3);
        firework.setFireworkMeta(fireworkMeta);

        firework.addPassenger(player);
    }

    private FireworkEffect getRandomEffect(final Random rand) {
        final FireworkEffect.Type type = FireworkEffect.Type.values()
                [rand.nextInt(FireworkEffect.Type.values().length)];
        return FireworkEffect.builder()
                .with(type)
                .withColor(Color.fromRGB(
                        rand.nextInt(256),
                        rand.nextInt(256),
                        rand.nextInt(256)
                ))
                .withFlicker()
                .withTrail()
                .build();
    }

}
