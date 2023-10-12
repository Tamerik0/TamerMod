package tamermod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ScreenEvent;
import tamermod.client.gui.Primitives.IGuiPrimitive;

public class StaticDrawable extends GuiComponent implements IDrawable {
    IGuiPrimitive texture;
    public StaticDrawable(IGuiPrimitive texture){
        this.texture=texture;
    }
    @Override
    public int getWidth() {
        return texture.getWidth();
    }

    @Override
    public int getHeight() {
        return texture.getHeight();
    }

    @Override
    public void draw(PoseStack poseStack, int xOffset, int yOffset) {
        texture.render(poseStack,xOffset,yOffset);
    }
}
