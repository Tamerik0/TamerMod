package tamermod.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.phys.Vec2;

public class SlotsLayout extends GUIContainer {
    public SlotsLayout addSlot(int x,int y){
        return addSlot(new SlotGui(),x,y);
    }
    public SlotsLayout addSlot(SlotGui slot, int x, int y){
        renderables.add(slot);
        slot.x=x-1;
        slot.y=y-1;
        return this;
    }
    public SlotsLayout addSlot(SlotGui slot){
        renderables.add(slot);
        return this;
    }
    public static SlotsLayout fromMenu(AbstractContainerMenu menu){
        SlotsLayout ans = new SlotsLayout();
        for(int i = 0;i<menu.slots.size();i++) {
            ans.addSlot(menu.slots.get(i).x,menu.slots.get(i).y);
        }
        return ans;
    }
    public SlotGui getSlot(int i){
        return (SlotGui)renderables.get(i);
    }
    public Vec2i getPos(int i){
        return new Vec2i(getSlot(i).x+1,getSlot(i).y+1);
    }
}
