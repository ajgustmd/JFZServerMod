package org.joyman.jfzservermod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

import static org.joyman.jfzservermod.commands.Util.hasPermission;
import static org.joyman.jfzservermod.commands.Util.isPlayer;

public class TestCommand {

    public static final Predicate<CommandSourceStack> require = source -> {
        return isPlayer.test(source) && hasPermission(source, 2);
    };
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("JFZtest").requires(isPlayer)
                        .then(Commands.argument("val", IntegerArgumentType.integer())
                .executes(TestCommand::execute)));
    }

    private static int execute(CommandContext<CommandSourceStack> ctx) {
        return test(ctx.getSource().getEntity());
    }

    private static int test(Entity entity) {
        // When we need to test something.
        return 1;
    }


}
