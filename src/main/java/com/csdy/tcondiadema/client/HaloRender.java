package com.csdy.tcondiadema.client;

import com.csdy.tcondiadema.TconDiadema;
import com.csdy.tcondiadema.diadema.DiademaRegister;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import static com.csdy.tcondiadema.client.DiademaRenderType.HALO_RENDER;

@Mod.EventBusSubscriber(modid = TconDiadema.MODID, value = Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class HaloRender extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private static final ResourceLocation HALO = new ResourceLocation("tcondiadema:textures/models/halo.png");

    // 头部位置和尺寸的预计算值
    private final float modelUnitsToWorldUnits = 1.0F / 16.0F;
    private final float headMeshOriginY_modelUnits = 0.0F;
    private final float headMeshHeight_modelUnits = 8.0F;
    private final float headTopY_modelUnits = headMeshOriginY_modelUnits + headMeshHeight_modelUnits;
    private final float haloYOffset_modelUnits = -8.0F; // 可适当调整，光环距离头部的高度
    private final float headMeshOriginZ_modelUnits = -4.0F; // 头部的前方 Z 坐标
    private final float headMeshDepth_modelUnits = 8.0F; // 头部深度
    private final float desiredDistanceBehindHead_worldUnits = 0.2F; // 光环距离头部后方的距离

    public HaloRender(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> layerParent) {
        super(layerParent);
    }

    public void render(@NotNull PoseStack matrix, @NotNull MultiBufferSource renderer, int light,
                       @NotNull AbstractClientPlayer player, float limbSwing, float limbSwingAmount,
                       float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        if (!player.isInvisible() && isWearingMartyrDiadema(player)) {
            matrix.pushPose(); // 1. 保存初始状态

            ModelPart headPart = this.getParentModel().head;

            // 2. 应用头部枢轴点平移和所有旋转
            matrix.translate(headPart.x / 16.0F, headPart.y / 16.0F, headPart.z / 16.0F);

            // 应用头部所有旋转（偏航、俯仰和侧倾）
            if (headPart.zRot != 0.0F) matrix.mulPose(Axis.ZP.rotation(headPart.zRot));
            if (headPart.yRot != 0.0F) matrix.mulPose(Axis.YP.rotation(headPart.yRot));
            if (headPart.xRot != 0.0F) matrix.mulPose(Axis.XP.rotation(headPart.xRot));

            // 3. 计算光环在头部后方的位置
            float finalYTranslation_worldUnits = (headTopY_modelUnits + haloYOffset_modelUnits) * modelUnitsToWorldUnits;

            // 头部后方的 Z 坐标，确保光环在头部后方
            float headBackSurfaceZ_worldUnits = headMeshOriginZ_modelUnits + headMeshDepth_modelUnits * modelUnitsToWorldUnits;

            // 需要将光环放置在头部的后方
            float finalZTranslation_worldUnits = headBackSurfaceZ_worldUnits - desiredDistanceBehindHead_worldUnits;

            // 4. 应用平移使光环位于头部后方
            matrix.translate(0.0F, finalYTranslation_worldUnits, -finalZTranslation_worldUnits);

            // 5. 旋转光环，使它朝向玩家的背部
//            matrix.mulPose(Axis.YP.rotationDegrees(180.0F)); // 旋转180度，让光环背对玩家

            // 6. 缩放
            matrix.pushPose();
            matrix.scale(1.5F, 1.5F, 1.5F); // 适当缩放光环

            // 7. 渲染光环
            VertexConsumer builder = renderer.getBuffer(HALO_RENDER.apply(HALO));
            Matrix4f pose = matrix.last().pose();
            float s = 0.5F; // 光环的半尺寸

            // 直接预计算 UV 和颜色值，减少多次计算
            int color = 0x00FF00FF; // 绿色 (ARGB)
            float[] uv = new float[] { 0.0F, 1.0F, 1.0F, 0.0F };

            // 渲染顶面四边形
            builder.vertex(pose, -s, -s, 0.0F).color(color).uv(uv[0], uv[1]).uv2(light).endVertex();
            builder.vertex(pose, s, -s, 0.0F).color(color).uv(uv[2], uv[1]).uv2(light).endVertex();
            builder.vertex(pose, s, s, 0.0F).color(color).uv(uv[2], uv[3]).uv2(light).endVertex();
            builder.vertex(pose, -s, s, 0.0F).color(color).uv(uv[0], uv[3]).uv2(light).endVertex();

            // 渲染背面四边形
            builder.vertex(pose, -s, s, 0.0F).color(color).uv(uv[0], uv[3]).uv2(light).endVertex();
            builder.vertex(pose, s, s, 0.0F).color(color).uv(uv[2], uv[3]).uv2(light).endVertex();
            builder.vertex(pose, s, -s, 0.0F).color(color).uv(uv[2], uv[1]).uv2(light).endVertex();
            builder.vertex(pose, -s, -s, 0.0F).color(color).uv(uv[0], uv[1]).uv2(light).endVertex();

            matrix.popPose(); // 恢复缩放
            matrix.popPose(); // 恢复初始状态
        }
    }

    private boolean isWearingMartyrDiadema(Player player) {
        return DiademaRegister.MARTYR.get().isAffected(player);
    }
}


