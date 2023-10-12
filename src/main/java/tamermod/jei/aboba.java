package tamermod.jei;

import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import tamermod.client.gui.BaseScreen;

import java.util.Collection;
import java.util.Collections;

public class aboba implements IGuiContainerHandler<BaseScreen<?>> {
    public Collection<IGuiClickableArea> getGuiClickableAreas(BaseScreen containerScreen, double guiMouseX, double guiMouseY) {
        return containerScreen.getClickableAreas((int)guiMouseX,(int)guiMouseY);
    }
}
