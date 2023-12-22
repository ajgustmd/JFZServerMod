package org.joyman.jfzservermod.commands;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = org.joyman.jfzservermod.JFZServerMod.MODID)
public class CommandEvent {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        TpCommand.register(event.getDispatcher());
        TestCommand.register(event.getDispatcher());
    }
}
