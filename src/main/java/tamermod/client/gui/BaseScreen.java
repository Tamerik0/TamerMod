package tamermod.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import tamermod.jei.TamerModJEIPlugin;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseScreen<TT extends AbstractContainerMenu> extends AbstractContainerScreen<TT> {
    public BaseScreen(TT arg, Inventory arg2, Component arg3) {
        super(arg, arg2, arg3);
    }
    GUIContainer gui=new GUIContainer();
    public void initSlots(){
        gui.addWidget(new TransformedElement(SlotsLayout.fromMenu(getMenu())).translate(leftPos,topPos));
    }
    protected <T extends GuiEventListener & Widget & NarratableEntry> T addRenderableWidget(T widget) {
        super.addWidget(widget);
        return gui.addWidget(widget);
    }
    protected <T extends Widget> T addRenderableOnly(T widget) {
        return gui.addWidget(widget);
    }
    protected void removeWidget(GuiEventListener widget) {
        super.removeWidget(widget);
        gui.removeWidget(widget);
    }
    protected void clearWidgets() {
        super.clearWidgets();
        gui.clearWidgets();
    }
    @Override
    public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(ms);
        gui.render(ms,mouseX,mouseY,partialTicks);
        super.render(ms, mouseX, mouseY, partialTicks);
    }
    public List<IGuiClickableArea> getClickableAreas(int mouseX,int mouseY){
        List<IGuiClickableArea> ans = new ArrayList<IGuiClickableArea>();
        for (var i:gui.renderables) {
            if(i instanceof IJEIClickableArea area){
                var area1 = area.getGuiClickableArea();
                if(area1!=null) {
                    area1.setPosition(area1.getX() -leftPos,area1.getY()-topPos);
                    ans.add(IGuiClickableArea.createBasic(area1.getX(),area1.getY(),area1.getWidth(),area1.getHeight(), TamerModJEIPlugin.MEGA_MACHINE_TYPE));
                }
            }
        }
        return ans;
    }
}
