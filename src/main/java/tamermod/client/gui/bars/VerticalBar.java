package tamermod.client.gui.bars;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import tamermod.client.gui.Primitives.IGuiPrimitive;
import tamermod.client.gui.bars.AbstractBar;

public class VerticalBar extends AbstractBar {
    IGuiPrimitive filler;
    protected int fillerOffsetX, fillerOffsetY;
    public VerticalBar(int x, int y, int fillerOffsetX, int fillerOffsetY, IGuiPrimitive background, IGuiPrimitive filler) {
        super(x, y, background);
        this.filler=filler;
        this.fillerOffsetX = fillerOffsetX;
        this.fillerOffsetY = fillerOffsetY;
    }
    @Override
    protected void fill(PoseStack poseStack) {
        int height = (int)(percent*filler.getHeight());
        filler.getPart(0, filler.getHeight()-height, filler.getWidth(), height).
                render(poseStack,x+fillerOffsetX, y + fillerOffsetY + filler.getHeight()-height);
    }
}
