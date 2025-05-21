package com.csdy.tcondiadema.event;


import com.csdy.tcondiadema.ModMain;
import com.csdy.tcondiadema.diadema.DiademaRegister;
import com.csdy.tcondiadema.diadema.warden.WardenDiadema;
import com.csdy.tcondiadema.effect.register.EffectRegister;
import com.csdy.tcondiadema.item.register.ItemRegister;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;

@Mod.EventBusSubscriber(modid = ModMain.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
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

}


