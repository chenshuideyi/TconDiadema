package com.csdy.tcondiadema.client;

import com.csdy.tcondiadema.TconDiadema;
import com.csdy.tcondiadema.diadema.DiademaRegister;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import static com.csdy.tcondiadema.client.DiademaRenderType.HALO_RENDER;

@Mod.EventBusSubscriber(modid = TconDiadema.MODID,value = Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class HaloRender extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public HaloRender(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> layerParent) {
        super(layerParent);
    }

    private static final ResourceLocation HALO = new ResourceLocation("tcondiadema:textures/models/halo.png");

    public void render(@NotNull PoseStack matrix, @NotNull MultiBufferSource renderer, int light,
                       @NotNull AbstractClientPlayer player, float limbSwing, float limbSwingAmount,
                       float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {


        if (!player.isInvisible() && isWearingMartyrDiadema(player)) {
            matrix.pushPose(); // 1. 保存初始状态

            ModelPart headPart = this.getParentModel().head;

            // 2. 应用头部枢轴点平移和所有旋转
            matrix.translate(headPart.x / 16.0F, headPart.y / 16.0F, headPart.z / 16.0F);

            // 应用头部所有旋转（偏航、俯仰和侧倾）
            if (headPart.zRot != 0.0F) {
                matrix.mulPose(Axis.ZP.rotation(headPart.zRot));
            }
            if (headPart.yRot != 0.0F) {
                matrix.mulPose(Axis.YP.rotation(headPart.yRot));
            }
            if (headPart.xRot != 0.0F) {
                matrix.mulPose(Axis.XP.rotation(headPart.xRot));
            }

            // 3. 计算头部后方的位置
            float modelUnitsToWorldUnits = 1.0F / 16.0F;

            // 头部尺寸（根据实际模型调整）
            float headMeshOriginY_modelUnits = 0.0F;   // 头部模型Y起点
            float headMeshHeight_modelUnits = 8.0F;   // 头部高度
            float headTopY_modelUnits = headMeshOriginY_modelUnits + headMeshHeight_modelUnits;

            // 让光环位于头顶或更高位置（增加1-2个模型单位）
            float haloYOffset_modelUnits = -8.0F; // 额外高度偏移量
            float finalYTranslation_worldUnits = (headTopY_modelUnits + haloYOffset_modelUnits) * modelUnitsToWorldUnits;

            // 头部后方表面计算
            float headMeshOriginZ_modelUnits = -4.0F;  // 头部模型Z起点
            float headMeshDepth_modelUnits = 8.0F;    // 头部深度
            float headBackSurfaceZ_modelUnits = headMeshOriginZ_modelUnits + headMeshDepth_modelUnits;
            float headBackSurfaceZ_worldUnits = headBackSurfaceZ_modelUnits * modelUnitsToWorldUnits;

            float desiredDistanceBehindHead_worldUnits = 0.5F; // 与头部后表面的距离
            float finalZTranslation_worldUnits = headBackSurfaceZ_worldUnits + desiredDistanceBehindHead_worldUnits;

            // 4. 应用平移使光环位于头部后方上方
            matrix.translate(
                    0.0F,                       // 水平方向不调整
                    finalYTranslation_worldUnits, // Y轴：头顶上方
                    finalZTranslation_worldUnits // Z轴：头部后方
            );

            // 5. 旋转光环使其朝外（与头部前方相反）
            matrix.mulPose(Axis.YP.rotationDegrees(180.0F));

            // 6. 缩放
            matrix.pushPose();
            matrix.scale(1.5F, 1.5F, 1.5F); // 适当缩放

            // 7. 渲染
            VertexConsumer builder = renderer.getBuffer(HALO_RENDER.apply(HALO));
            Matrix4f pose = matrix.last().pose();
            float s = 0.5F; // 四边形半尺寸

            builder.vertex(pose, -s, -s, 0.0F).color(0, 255, 0, 255).uv(0.0F, 1.0F).uv2(light).endVertex();
            builder.vertex(pose,  s, -s, 0.0F).color(0, 255, 0, 255).uv(1.0F, 1.0F).uv2(light).endVertex();
            builder.vertex(pose,  s,  s, 0.0F).color(0, 255, 0, 255).uv(1.0F, 0.0F).uv2(light).endVertex();
            builder.vertex(pose, -s,  s, 0.0F).color(0, 255, 0, 255).uv(0.0F, 0.0F).uv2(light).endVertex();

            // 背面四边形（法线方向相反）
            builder.vertex(pose, -s,  s, 0.0F).color(0, 255, 0, 255).uv(0.0F, 0.0F).uv2(light).endVertex();
            builder.vertex(pose,  s,  s, 0.0F).color(0, 255, 0, 255).uv(1.0F, 0.0F).uv2(light).endVertex();
            builder.vertex(pose,  s, -s, 0.0F).color(0, 255, 0, 255).uv(1.0F, 1.0F).uv2(light).endVertex();
            builder.vertex(pose, -s, -s, 0.0F).color(0, 255, 0, 255).uv(0.0F, 1.0F).uv2(light).endVertex();

            matrix.popPose(); // 恢复缩放
            matrix.popPose(); // 恢复初始状态
        }
    }

    @SubscribeEvent
    public static void onRenderLevelStageEvent(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            return; // 确保在实体渲染阶段之后执行
        }

        Minecraft minecraft = Minecraft.getInstance();
        EntityRenderDispatcher renderDispatcher = minecraft.getEntityRenderDispatcher();

        // 获取 PlayerRenderer
        if (minecraft.player != null && renderDispatcher.getRenderer(minecraft.player) instanceof PlayerRenderer playerRenderer) {
            playerRenderer.addLayer(new HaloRender(playerRenderer));
        }
    }

    private boolean isWearingMartyrDiadema(Player player) {
        return DiademaRegister.MARTYR.get().isAffected(player);
    }
}
