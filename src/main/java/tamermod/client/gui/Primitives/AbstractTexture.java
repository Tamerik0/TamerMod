package tamermod.client.gui.Primitives;

import com.mojang.blaze3d.vertex.PoseStack;


public abstract class AbstractTexture implements IGuiPrimitive{

    final int width, height;
    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
    protected AbstractTexture(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(PoseStack poseStack, int blitOffset) {
        render(poseStack,blitOffset,0,0);
    }

    @Override
    public void render(PoseStack poseStack, int blitOffset, int x, int y) {
        render(poseStack,blitOffset, x, y, width, height);
    }
    public abstract void renderPart(PoseStack poseStack, int blitOffset, int x, int y, int width, int height, int textureX,int textureY,int textureWidth,int textureHeight);
    @Override
    public TextureSprite getPart(int x, int y, int width, int height) {
        return new TextureSprite(this,x,y,width,height);
    }

}
