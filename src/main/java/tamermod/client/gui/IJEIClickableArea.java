package tamermod.client.gui;

import mezz.jei.api.gui.handlers.IGuiClickableArea;
import net.minecraft.client.renderer.Rect2i;
import tamermod.client.gui.Primitives.AbstractSprite;

public interface IJEIClickableArea {
    Rect2i getGuiClickableArea();
}