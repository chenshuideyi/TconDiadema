package com.csdy.tcondiadema.event;


import com.csdy.tcondiadema.TconDiadema;
import com.csdy.tcondiadema.item.register.ItemRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = TconDiadema.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Event {

    @SubscribeEvent
    public static void death(LivingDeathEvent e) {
        LivingEntity living = e.getEntity();
        if (living instanceof Player player) {
            CompoundTag tag = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
            String firstdeath = "first_death";
            if (!tag.getBoolean(firstdeath)) {
                player.setHealth(player.getMaxHealth());
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ItemRegister.BROKEN_SACRED_RELIC.get()));
                player.displayClientMessage(Component.translatable("item.tcondiadema.sacred_relic.get").withStyle(ChatFormatting.RED), false);
                player.getPersistentData().getBoolean("first_death");
                tag.putBoolean(firstdeath, true);
                e.getEntity().getPersistentData().put(Player.PERSISTED_NBT_TAG, tag);
                e.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        say(event, event.getEntity());
    }

    private static void say(@Nullable net.minecraftforge.eventbus.api.Event event, Entity entity) {
        if (entity == null)
            return;
        if (entity instanceof Player player && !player.getCommandSenderWorld().isClientSide()) {
            player.displayClientMessage(Component.translatable("diadema_message"), false);
        }
    }

}


