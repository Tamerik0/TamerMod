package tamermod.client.gui.Primitives;

import com.mojang.blaze3d.vertex.PoseStack;
import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;

public class NinePatch extends AbstractSprite{
    int innerX,innerY,innerWidth,innerHeight;
    public double scale=1;
    IGuiPrimitive base;
    public NinePatch(int width, int height,int innerX, int innerY, int innerWidth, int innerHeight, IGuiPrimitive base) {
        super(width, height);
        this.base=base;
        this.innerX=innerX;
        this.innerY=innerY;
        this.innerWidth=innerWidth;
        this.innerHeight=innerHeight;
    }
    public NinePatch(int innerX, int innerY, int innerWidth, int innerHeight, IGuiPrimitive base){
        this(base.getWidth(), base.getHeight(),innerX, innerY, innerWidth, innerHeight,base);
    }

    @Override
    public void render(PoseStack poseStack, int blitOffset, int x, int y, int width, int height) {
        var a=new int[]{innerX,innerWidth, base.getWidth()-innerX-innerWidth};
        var b=new int[]{innerY,innerHeight, base.getHeight()-innerY-innerHeight};
        var c=new double[]{innerX*scale,width-(base.getWidth()-innerWidth)*scale,(base.getWidth()-innerX-innerWidth)*scale};
        var d=new double[]{innerY*scale,height-(base.getHeight()-innerHeight)*scale,(base.getHeight()-innerX-innerHeight)*scale};
        for(int i = 0;i<3;i++){
            for(int j = 0;j<3;j++){
                int _innerX = 0;
                for(int i0=0;i0<i;i0++)
                    _innerX+=a[i0];
                int _innerY = 0;
                for(int j0=0;j0<j;j0++)
                    _innerY+=b[j0];
                int _x=x;
                for(int i0=0;i0<i;i0++)
                    _x+=c[i0];
                int _y=y;
                for(int j0=0;j0<j;j0++)
                    _y+=d[j0];
                base.getPart(_innerX,_innerY,a[i],b[j]).render(poseStack,blitOffset,_x,_y,(int)c[i],(int)d[j]);
            }
        }
    }

    @Override
    public IGuiPrimitive getPart(int x, int y, int width, int height) {
        throw new NotImplementedException("GetPart method in NinePatch is not implemented :(");
    }
}
