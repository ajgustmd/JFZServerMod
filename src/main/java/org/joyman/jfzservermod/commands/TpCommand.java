package org.joyman.jfzservermod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;

import static org.joyman.jfzservermod.commands.Util.isPlayer;
import static org.joyman.jfzservermod.commands.Util.sendMessage;

public class TpCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("tpa").requires(isPlayer)
                .then(Commands.argument("target", EntityArgument.player())
                        .executes(TpCommand::execute)));
    }

    public static int execute(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player;
        ServerPlayer target;
        try {
            player = ctx.getSource().getPlayerOrException();
            target = EntityArgument.getPlayer(ctx, "target");

            if(target.getGameProfile().getId().equals(player.getGameProfile().getId())) {
                sendMessage(ctx.getSource(), false, new TextComponent("자기 자신에게 텔레포트를 할 수 없습니다.").withStyle(ChatFormatting.RED));
                return 0;
            }
            if(player.isInLava() || !player.isOnGround() && !player.isInWater()) {
                sendMessage(ctx.getSource(), false, new TextComponent("공중이나 용암에서 텔레포트를 할 수 없습니다.").withStyle(ChatFormatting.RED));
                return 0;
            }
            BlockPos flPos = new BlockPos(target.getBlockX(), target.getBlockY() - 1, target.getBlockZ());
            if(!target.isOnGround() && !target.isInWater() && target.getLevel().getBlockState(flPos).isAir()) {
                sendMessage(ctx.getSource(), false, new TextComponent("해당 플레이어가 공중에 있습니다.").withStyle(ChatFormatting.RED));
                return 0;
            }

            player.teleportTo(target.getLevel(), target.getX(), target.getY(), target.getZ(), player.getYRot(), player.getXRot());
            sendMessage(ctx.getSource(), true, new TextComponent(target.getDisplayName() + "에게로 텔레포트 했습니다.").withStyle(ChatFormatting.YELLOW));
            target.sendMessage(new TextComponent(player.getDisplayName() + "이(가) 당신에게로 텔레포트 했습니다.").withStyle(ChatFormatting.YELLOW), target.getUUID());
        } catch (CommandSyntaxException e) {
            sendMessage(ctx.getSource(), false, new TextComponent("해당 플레이어가 존재하지 않습니다.").withStyle(ChatFormatting.RED));
            return 0;
        }

        return 1;
    }

}
