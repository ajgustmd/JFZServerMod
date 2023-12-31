package org.joyman.jfzservermod.event;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joyman.jfzservermod.JFZServerMod;
import org.joyman.jfzservermod.item.JFCaseItem;
import org.joyman.jfzservermod.pouch.IPouchGetter;

@Mod.EventBusSubscriber(modid = JFZServerMod.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if(event.getEntity() instanceof Player player) {
            ((IPouchGetter)player).getPouch().save(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        ((IPouchGetter)player).getPouch().load(player);
    }
}
