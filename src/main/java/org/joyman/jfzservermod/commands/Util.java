package org.joyman.jfzservermod.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.MutableComponent;

import java.util.function.Predicate;

public class Util {
    public static final Predicate<CommandSourceStack> isPlayer = (source -> {
        try {
            return (source.getPlayerOrException() != null);
        } catch (CommandSyntaxException e) {
            return false;
        }
    });

    public static boolean hasPermission(CommandSourceStack source, int permission) {
        try {
            return source.getPlayerOrException().hasPermissions(permission);
        } catch (CommandSyntaxException e) {
            return false;
        }
    }

    public static void sendMessage(CommandSourceStack source, boolean success, MutableComponent... styled) {
        MutableComponent comp;
        if (styled.length > 0) {
            comp = styled[0];
        }
        else return;
        if (styled.length > 1) {
            for(int i = 1; i < styled.length; i++)
                comp.append(styled[i]);
        }
        if(success) {
            source.sendSuccess(comp, false);
        }
        else {
            source.sendFailure(comp);
        }
    }
}
