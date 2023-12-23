package org.joyman.jfzservermod.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joyman.jfzservermod.JFZServerMod;
import org.joyman.jfzservermod.Util;

public class JFCaseItem extends Item {

    public JFCaseItem(Properties properties) {

        super(properties);
    }

    /*
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        if(event.getItemStack().getItem() != ModItems.JFCASE.get()) return;
        Player player = event.getPlayer();

        if(player == null) return;
        Util.giveItem(player, new ItemStack(ModItems.JFCOIN.get(), 3));
    }
    */

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        JFZServerMod.LOGGER.info("JFCase use method override - isClient: " + level.isClientSide());

        ItemStack caseItem = player.getItemInHand(hand);
        caseItem.setCount(caseItem.getCount() - 1);

        // Decide reward here
        ItemStack reward = new ItemStack(ModItems.JFCOIN.get(), 3);

        Util.giveItem(player, reward);
        return InteractionResultHolder.consume(caseItem);
    }

}
