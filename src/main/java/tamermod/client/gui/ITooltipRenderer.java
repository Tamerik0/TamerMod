package tamermod.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;

public interface ITooltipRenderer {
    public void renderTooltip(PoseStack poseStack, int mouseX, int mouseY, float partialTick);
}
