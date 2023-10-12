package tamermod.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Widget;
import tamermod.client.gui.Primitives.BaseTexture;
import tamermod.client.gui.Primitives.IGuiPrimitive;
import tamermod.client.gui.Primitives.NinePatch;

public class SlotGui extends NinePatch {
    public static final BaseTexture defaultSlotTexture = new BaseTexture("tamermod:textures/gui/slot.png",18,18);
    public SlotGui(int width, int height, IGuiPrimitive customTexture){
        super(width,height,1,1,customTexture.getWidth()-2, customTexture.getHeight()-2,customTexture);
    }
    public SlotGui(int width,int height){
        this(width,height,defaultSlotTexture);
    }
    public SlotGui(){
        this(18,18);
    }
}
