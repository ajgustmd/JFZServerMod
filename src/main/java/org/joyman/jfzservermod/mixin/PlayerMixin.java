package org.joyman.jfzservermod.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.joyman.jfzservermod.JFZServerMod;
import org.joyman.jfzservermod.pouch.IPouchGetter;
import org.joyman.jfzservermod.pouch.Pouch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;

@Mixin(ServerPlayer.class)
public abstract class PlayerMixin extends Player implements IPouchGetter {
    private static final String TAG_POUCH = JFZServerMod.MODID + ".pouch";

    @Unique
    private Pouch pouch;

    @Nonnull
    @Override
    public Pouch getPouch() {
        if(pouch == null) pouch = new Pouch();
        return pouch;
    }

    public PlayerMixin(Level p_36114_, BlockPos p_36115_, float p_36116_, GameProfile p_36117_) {
        super(p_36114_, p_36115_, p_36116_, p_36117_);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    private void onWriteNBT(CompoundTag nbt, CallbackInfo info) {
        ListTag pouchData = getPouch().getData();
        if(pouchData != null) {
            nbt.put(TAG_POUCH, pouchData);
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
    private void onReadNBT(CompoundTag nbt, CallbackInfo info) {
        if(nbt.contains(TAG_POUCH)) {
            getPouch().setData(nbt.getList(TAG_POUCH, 10));
        }
    }

    @Inject(method = "restoreFrom", at = @At("HEAD"))
    private void restoreFrom(ServerPlayer player, boolean p, CallbackInfo info) {
        getPouch().setData(((IPouchGetter)player).getPouch().getData());
    }
}
