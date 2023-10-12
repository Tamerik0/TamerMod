package tamermod.client.gui.bars;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.material.Fluid;
import tamermod.client.gui.GuiHelper;
import tamermod.client.gui.Primitives.BaseTexture;
import tamermod.client.gui.Primitives.IGuiPrimitive;
import tamermod.client.gui.Primitives.TextureAtlasSpriteWrapper;
import tamermod.client.gui.SlotGui;
import tamermod.fluids.FluidUtils;

public class FluidBar extends AbstractBar{
    public Fluid fluid;
    IGuiPrimitive overlay = new BaseTexture("tamermod:textures/gui/bars/fluidbaroverlay.png",16,58);
    public FluidBar(int x, int y) {
        super(x, y, new SlotGui(18,60));
    }
    @Override
    public void fill(PoseStack poseStack){
        if(fluid!=null) {
            var sprite = new TextureAtlasSpriteWrapper(FluidUtils.getFluidTexture(fluid));
            float[] color = GuiHelper.hexToFloatArray(FluidUtils.getFluidColor(fluid));
            RenderSystem.setShaderColor(color[1], color[2], color[3], color[0]);
            RenderSystem.disableBlend();
            int height = (int) (percent * 58);
            for (int i = 0; i < height / sprite.getHeight(); i++) {
                sprite.render(poseStack,x+1, y-1 + h - sprite.getHeight() * (i + 1));
            }
            sprite.getPart(0,sprite.getHeight() - height % sprite.getHeight(), sprite.getWidth(),height % sprite.getHeight())
                    .render(poseStack,x+1, y-1 + h - sprite.getHeight() * (height / sprite.getHeight()) - height % sprite.getHeight());
            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1, 1, 1, 1);
        }
        overlay.render(poseStack,x+1,y+1);
    }
    @Override
    public void renderTooltipInternal(PoseStack ps, int mouseX, int mouseY, float pt){
        if(fluid!=null)
            GuiHelper.renderTooltip(ps, Component.literal(fluid.getFluidType().getDescription().getString()+": " + value + " mB / " + maxvalue + " mB"),mouseX,mouseY);
    }
}
