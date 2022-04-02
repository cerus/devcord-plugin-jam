package me.celus.pluginjam.playerdeath;

import me.celus.pluginjam.util.EntityUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.PointedDripstone;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;

public class DripStoneDeath implements PlayerDeath {

    @Override
    public void killPlayer(final Player player) {
        final PointedDripstone.Thickness[] thicknessArray = new PointedDripstone.Thickness[] {
                PointedDripstone.Thickness.TIP,
                PointedDripstone.Thickness.FRUSTUM,
                PointedDripstone.Thickness.BASE
        };

        final Location loc = player.getLocation().clone().add(0, 15, 0);
        for (int i = 0; i < 3; i++) {
            final PointedDripstone dripStoneData = (PointedDripstone) Material.POINTED_DRIPSTONE.createBlockData();
            dripStoneData.setVerticalDirection(BlockFace.DOWN);
            dripStoneData.setThickness(thicknessArray[i]);

            final FallingBlock fallingDripStone = player.getWorld().spawnFallingBlock(loc, dripStoneData);
            fallingDripStone.setHurtEntities(true);
            fallingDripStone.setDropItem(false);
            EntityUtil.setFallHurtAmount(fallingDripStone, 100);

            loc.add(0, 1, 0);
        }
    }

}
