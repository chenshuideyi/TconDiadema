package com.csdy.tcondiadema.diadema.warden;

import com.csdy.tcondiadema.ModMain;
import com.csdy.tcondiadema.diadema.DiademaRegister;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import lombok.SneakyThrows;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import static com.csdy.tcondiadema.ModMain.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class WardenBlindnessEffect {
    private static PostChain postChain;
    private static EffectInstance effectInstance;
    private static final ResourceLocation POST_CHAIN_LOCATION = new ResourceLocation(ModMain.MODID, "shaders/post/warden_blind.json");

    private static final int LOOP_TIME_MS = 10000;

    @OnlyIn(Dist.CLIENT) @SneakyThrows
    public static void init() {
        Minecraft mc = Minecraft.getInstance();
        if (postChain != null) {
            postChain.close(); // Close existing if any
        }
        postChain = new PostChain(
                mc.getTextureManager(),
                mc.getResourceManager(),
                mc.getMainRenderTarget(), // This is the main screen framebuffer
                POST_CHAIN_LOCATION
        );
    }

    private static boolean isWarden(Object o) {
        return o instanceof Player player
//                && player.hasEffect(EffectRegister.SCARED.get());
                && DiademaRegister.WARDEN.get().isAffected(player)
                && !(WardenDiadema.WhiteList.contains(player));
    }

//    @SubscribeEvent
//    public static void onRenderOverlay(RenderGuiOverlayEvent event) {
//        Minecraft mc = Minecraft.getInstance();
//        if (isWarden(mc.player)) {
//            int width = event.getWindow().getGuiScaledWidth();
//            int height = event.getWindow().getGuiScaledHeight();
//            GuiGraphics guiGraphics = event.getGuiGraphics();
//            // 绘制全屏黑色矩形
//            guiGraphics.fill(0, 0, width, height, 0xFF000000); // ARGB格式：0x80表示50%透明度，000000表示黑色
//        }
//    }

    @SubscribeEvent @OnlyIn(Dist.CLIENT)
    public static void onRenderArm(RenderArmEvent event) {
        if (isWarden(Minecraft.getInstance().player)) event.setCanceled(true);
    }

    @SubscribeEvent @OnlyIn(Dist.CLIENT)
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_LEVEL) return;
        if (!isWarden(Minecraft.getInstance().player)) return; // 我感觉这样检测纯客户端可能有问题，但Csdy说没问题，那就这样

        float time = (System.currentTimeMillis() % LOOP_TIME_MS) / (float) LOOP_TIME_MS;

        Minecraft mc = Minecraft.getInstance();
        postChain.resize(mc.getWindow().getWidth(), mc.getWindow().getHeight());
        if (effectInstance == null) {
            // 一些必要的初始化
            effectInstance = postChain.passes.get(0).getEffect();
        }


        var camera = event.getCamera();
        var viewMat = new Matrix4f()  // mojang 简直死妈了，一个b视图矩阵我试了几万种方法拿不到，最后还得手搓一个
                .rotateLocalY((float) Math.toRadians(camera.getYRot()))
                .rotateLocalX((float) Math.toRadians(camera.getXRot()))
                .translate(
                        (float) camera.getPosition().x,
                        (float) -camera.getPosition().y,
                        (float) camera.getPosition().z
                );
        var invProjMat = RenderSystem.getProjectionMatrix().invert();
        var invViewMat = viewMat.invert();
        effectInstance.safeGetUniform("CycleTime").set(time);
        effectInstance.safeGetUniform("InvProjMat").set(invProjMat);
        effectInstance.safeGetUniform("InvViewMat").set(invViewMat);
        postChain.process(event.getPartialTick());
    }
}
