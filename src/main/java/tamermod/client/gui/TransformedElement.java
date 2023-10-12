package tamermod.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import net.minecraft.client.gui.components.Widget;

public class TransformedElement implements Widget{
    Widget widget;
    public int xOffset = 0,yOffset = 0,blitOffset = 0;
    public float xScale = 1, yScale = 1;
    public float rotation = 0;
    public TransformedElement(Widget widget){
        this.widget=widget;
    }
    public TransformedElement translate(int x,int y){
        xOffset+=x;
        yOffset+=y;
        return this;
    }
    public TransformedElement setBlitOffset(int blitOffset){
        this.blitOffset=blitOffset;
        return this;
    }
    public TransformedElement scale(float xScale,float yScale){
        this.xScale*=xScale;
        this.yScale*=yScale;
        return this;
    }
    public TransformedElement rotate(float a){
        rotation+=a;
        return this;
    }
    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        poseStack.pushPose();
        poseStack.scale(xScale,yScale,1);
        poseStack.mulPose(Quaternion.fromXYZ(0,0,rotation));
        poseStack.translate(xOffset,yOffset,blitOffset);
        widget.render(poseStack,mouseX,mouseY,partialTick);
        poseStack.popPose();
    }
}
