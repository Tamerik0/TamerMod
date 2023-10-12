package tamermod.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Widget;

import java.util.ArrayList;
import java.util.List;

public class GUIContainer implements Widget, ITooltipRenderer{
    List<ITooltipRenderer> tooltipRenderers = new ArrayList();
    List<Widget> renderables = new ArrayList();

    public <T> T addWidget(T widget) {
        if(widget instanceof ITooltipRenderer tooltipRenderer)
            tooltipRenderers.add(tooltipRenderer);
        if(widget instanceof Widget renderable) {
            renderables.add(renderable);
        }
        return widget;
    }
    public <T> void removeWidget(T widget) {
        if(widget instanceof ITooltipRenderer tooltipRenderer)
            tooltipRenderers.remove(tooltipRenderer);
        if(widget instanceof Widget renderable)
            renderables.remove(renderable);
    }

    public void clearWidgets() {
        tooltipRenderers.clear();
        renderables.clear();
    }
    public void renderMain(PoseStack poseStack,int mouseX,int mouseY,float partialTicks){
        for(Widget widget:renderables)
            widget.render(poseStack,mouseX,mouseY,partialTicks);
    }
    public void renderTooltip(PoseStack poseStack,int mouseX,int mouseY,float partialTicks){
        for(ITooltipRenderer tooltipRenderer:tooltipRenderers)
            tooltipRenderer.renderTooltip(poseStack,mouseX,mouseY,partialTicks);
    }
    public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
        this.renderMain(ms,mouseX,mouseY,partialTicks);
        this.renderTooltip(ms, mouseX, mouseY,partialTicks);
    }

}
