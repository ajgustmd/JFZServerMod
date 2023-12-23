package org.joyman.jfzservermod;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Util {
    public static void giveItem(Player player, ItemStack items) {
        if(!(player.getInventory().add(items) && items.isEmpty())) {
            player.drop(items, false);
        }
    }
}
