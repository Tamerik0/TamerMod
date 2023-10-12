package tamermod.client.gui.Primitives;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import tamermod.client.gui.GuiHelper;

public class TextureAtlasSpritePart extends AbstractSprite{
    int x0,y0,x1,y1;
    TextureAtlasSprite sprite;
    public TextureAtlasSpritePart(TextureAtlasSprite sprite, int x0,int y0,int x1,int y1){
        super(sprite.getWidth(), sprite.getHeight());
        this.sprite=sprite;
        this.x0=x0;
        this.y0=y0;
        this.x1=x1;
        this.y1=y1;
    }
    @Override
    public void render(PoseStack poseStack,int blitOffset, int x, int y, int width, int height) {
        GuiHelper.blitSpritePartial(poseStack,blitOffset,x,y,width,height,x0,y0,x1-x0,y1-y0,sprite);
    }

    @Override
    public TextureAtlasSpritePart getPart(int x, int y, int width, int height) {
        double xWarp = (double)(x1-x0)/this.width;
        double yWarp = (double)(y1-y0)/this.height;
        var ret = new TextureAtlasSpritePart(sprite,(int)(x0+x*xWarp),(int)(y0+y*yWarp),(int)(x0+(x+width)*xWarp),(int)(y0+(y+height)*yWarp));
        ret.width=width;
        ret.height=height;
        return ret;
    }
}
