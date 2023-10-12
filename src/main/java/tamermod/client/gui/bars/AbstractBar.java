package tamermod.client.gui.bars;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import tamermod.client.gui.ITooltipRenderer;
import tamermod.client.gui.Primitives.IGuiPrimitive;

public abstract class AbstractBar extends GuiComponent implements Widget, ITooltipRenderer {
    public float percent=0;
    public int value=0;
    public int maxvalue=99999999;
    public boolean isHovered;
    protected int x,y,w,h;
    IGuiPrimitive background;
    public AbstractBar(int x, int y, IGuiPrimitive background){
        this.x=x;
        this.y=y;
        this.w=background.getWidth();
        this.h=background.getHeight();
        this.background = background;
    }
    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        percent = (float)value/maxvalue;
        background.render(poseStack,x,y);
        fill(poseStack);
    }
    @Override
    public void renderTooltip(PoseStack poseStack, int mouseX, int mouseY, float pt){
        isHovered=mouseX>=x&&mouseX<x+w&&mouseY>=y&&mouseY<y+h;
        if(isHovered) {
            renderTooltipInternal(poseStack, mouseX, mouseY, pt);
        }
    }
    protected abstract void fill(PoseStack poseStack);
    protected void renderTooltipInternal(PoseStack poseStack, int mouseX, int mouseY, float pt){};
}
