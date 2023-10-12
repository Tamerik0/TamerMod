package tamermod.client.gui.bars;

import mezz.jei.api.gui.handlers.IGuiClickableArea;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import tamermod.client.gui.IJEIClickableArea;
import tamermod.client.gui.Primitives.BaseTexture;
import tamermod.jei.TamerModJEIPlugin;

public class ProgressArrow extends HorizontalBar implements IJEIClickableArea {
    public ProgressArrow(int x, int y) {
        super(x, y, 0, -1, new BaseTexture("tamermod:textures/gui/bars/progressarrowbg.png",21,8),new BaseTexture("tamermod:textures/gui/bars/progressarrowfull.png",21,8));
    }

    @Override
    public Rect2i getGuiClickableArea() {
        return new Rect2i(x,y,w,h);
    }
}
