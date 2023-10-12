package tamermod.blocks.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import tamermod.blocks.BaseEntityBlock;

public class InjectorRenderer implements BlockEntityRenderer<BaseInjectorBlockEntity> {
    public InjectorRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(BaseInjectorBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = pBlockEntity.getRenderedItemStack();
        if (itemStack != null && itemStack != ItemStack.EMPTY) {
            pPoseStack.pushPose();
            pPoseStack.translate(0.5, 0.5f, 0.5f);
            switch (pBlockEntity.getBlockState().getValue(BaseEntityBlock.FACING)) {
                case NORTH -> pPoseStack.mulPose(Vector3f.YP.rotationDegrees(90));
                case EAST -> pPoseStack.mulPose(Vector3f.YP.rotationDegrees(0));
                case SOUTH -> pPoseStack.mulPose(Vector3f.YP.rotationDegrees(270));
                case WEST -> pPoseStack.mulPose(Vector3f.YP.rotationDegrees(180));
            }
            pPoseStack.translate(0.30f, 0f, 0f);
            pPoseStack.scale(0.5f, 0.5f, 0.5f);
            pPoseStack.mulPose(Vector3f.YP.rotationDegrees(-90));

            itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.FIXED, getLightLevel(pBlockEntity.getLevel(),
                            pBlockEntity.getBlockPos()),
                    OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, 1);
            pPoseStack.popPose();
        }
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
