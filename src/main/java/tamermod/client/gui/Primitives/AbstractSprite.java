package tamermod.client.gui.Primitives;

import com.mojang.blaze3d.vertex.PoseStack;

public abstract class AbstractSprite implements IGuiPrimitive {
    public int x=0, y=0, blitOffset, width, height;
    protected AbstractSprite(int width,int height){
        this.width = width;
        this.height = height;
    }
    @Override
    public void render(PoseStack poseStack) {
        render(poseStack,blitOffset, x, y);
    }
    @Override
    public void render(PoseStack poseStack, int blitOffset) {
        render(poseStack,blitOffset, x, y);
    }
    @Override
    public void render(PoseStack poseStack, int x, int y) {
        render(poseStack,blitOffset, x, y, width, height);
    }
    @Override
    public void render(PoseStack poseStack,int blitOffset, int x, int y) {
        render(poseStack,blitOffset, x, y, width, height);
    }
    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
