package tamermod.client.gui.bars;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import tamermod.client.gui.GuiHelper;
import tamermod.client.gui.Primitives.BaseTexture;
import tamermod.client.gui.SlotGui;

public class EUEnergyBar extends VerticalBar {

    public EUEnergyBar(int x, int y) {
        super(x, y, 1, 1, new SlotGui(6,54),
                new BaseTexture("tamermod:textures/gui/bars/energybarfull1.png",4,52));
    }
    @Override
    public void renderTooltipInternal(PoseStack ps, int mouseX, int mouseY, float pt){
        GuiHelper.renderTooltip(ps, Component.literal(value + " / " + maxvalue + " EU"),mouseX,mouseY);
    }
}
