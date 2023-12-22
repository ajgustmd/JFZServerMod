package org.joyman.jfzservermod.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joyman.jfzservermod.JFZServerMod;
import org.joyman.jfzservermod.pouch.IPouchGetter;

@Mod.EventBusSubscriber(modid = JFZServerMod.MODID)
public class EventManager {

    public static final String TAG_POUCH = "JFZServerMod.pouch";

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
