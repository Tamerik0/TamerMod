package tamermod.jei;

import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import tamermod.client.gui.GUIContainer;
import tamermod.client.gui.GuiHelper;
import tamermod.client.gui.SlotsLayout;
import tamermod.client.gui.bars.*;
import tamermod.recipes.MegaMachineRecipe;

public class CategoryGui extends GUIContainer {

    public CategoryGui(MegaMachineRecipe recipe, SlotsLayout slots){
        addWidget(slots);
        addWidget(new JeiBar(7,8,recipe.euInput+" EU", EUEnergyBar::new));
        addWidget(new JeiBar(14,8, recipe.rfInput+" RF", RFEnergyBar::new));
        FluidBar b = (FluidBar) addWidget(new JeiBar(22,8,recipe.fluidInput.getFluid().getFluidType().getDescription().getString() + ": " + recipe.fluidInput.getAmount()+" mB",FluidBar::new)).getInternalBar();
        b.fluid=recipe.fluidInput.getFluid();
        var arrow = addWidget(new ProgressArrow(70,30));
        int period =1000;
        arrow.maxvalue=period;
        arrow.value = ((int)(System.currentTimeMillis())%period);
    }
}
