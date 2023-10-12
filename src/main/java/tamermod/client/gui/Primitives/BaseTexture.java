package tamermod.client.gui.Primitives;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import tamermod.client.gui.GuiHelper;

public class BaseTexture extends AbstractTexture{

    ResourceLocation texture;
    public BaseTexture(ResourceLocation texture, int width, int height) {
        super(width, height);
        this.texture=texture;
    }
    public BaseTexture(String texturePath, int width, int height) {
        this(new ResourceLocation(texturePath),width,height);
    }

    @Override
    public void renderPart(PoseStack poseStack, int blitOffset, int x, int y, int width, int height, int textureX, int textureY, int textureWidth, int textureHeight) {
        GuiHelper.blitTexturePartial(poseStack,blitOffset,x,y,width,height,textureX,textureY,textureWidth,textureHeight,this.width,this.height,texture);
    }

    @Override
    public void render(PoseStack poseStack,int blitOffset, int x, int y, int width, int height) {
        renderPart(poseStack,blitOffset,x,y,width,height,0,0,this.width,this.height);
    }
}
