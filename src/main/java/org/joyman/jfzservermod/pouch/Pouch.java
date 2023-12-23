package org.joyman.jfzservermod.pouch;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.joyman.jfzservermod.Util;

public class Pouch {

    ListTag data;

    public ListTag getData() {
        if(data == null) data = new ListTag();
        return data;
    }

    public void setData(ListTag data) { this.data = data; }

    public void save(Player player) {
        ListTag data = new ListTag();

        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if(stack == ItemStack.EMPTY) continue;
            CompoundTag tag = stack.save(new CompoundTag());
            tag.putInt("Slot", i);
            data.add(tag);
            stack.setCount(0);
        }
        this.data = data;
    }
    public void load(Player player) {
        ListTag savedData = getData();
        for (Tag t : savedData) {
            CompoundTag tag = (CompoundTag)t;
            Inventory inv = player.getInventory();
            int slot = tag.getInt("Slot");
            if(inv.getItem(slot) == ItemStack.EMPTY) {
                inv.setItem(slot, ItemStack.of(tag));
            }
            Util.giveItem(player, ItemStack.of(tag));
        }
    }
}
