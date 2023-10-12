package tamermod.client.gui.Primitives;

import com.mojang.blaze3d.vertex.PoseStack;

public class TextureSprite extends AbstractSprite{
    AbstractTexture texture;
    int x0, y0, tWidth, tHeight;
    public TextureSprite(AbstractTexture texture){
        super(texture.width, texture.height);
        this.texture=texture;
        this.x0=0;
        this.y0=0;
        this.tWidth = texture.width;
        this.tHeight = texture.height;
    }
    public TextureSprite(AbstractTexture texture, int x0,int y0,int tWidth, int tHeight){
        super(tWidth,tHeight);
        this.texture=texture;
        this.x0=x0;
        this.y0=y0;
        this.tWidth = tWidth;
        this.tHeight = tHeight;
    }

    @Override
    public void render(PoseStack poseStack,int blitOffset, int x, int y, int width, int height) {
        texture.renderPart(poseStack,blitOffset,x,y,width,height,x0,y0,tWidth,tHeight);
    }

    @Override
    public TextureSprite getPart(int x, int y, int width, int height) {
        double xWarp = (double)tWidth/this.width;
        double yWarp = (double)tHeight/this.height;
        var ret = new TextureSprite(texture,(int)(x0+x*xWarp),(int)(y0+y*yWarp),(int)(x0+(x+width)*xWarp),(int)(y0+(y+height)*yWarp));
        ret.width = width;
        ret.height = height;
        return ret;
    }
}
