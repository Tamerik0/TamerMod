package tamermod.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import tamermod.TamerMod;
import tamermod.client.gui.Primitives.IGuiPrimitive;
import tamermod.client.gui.SlotsLayout;
import tamermod.client.gui.StaticDrawable;
import tamermod.init.TamerModBlocks;
import tamermod.recipes.MegaMachineRecipe;


public class MegaMachineRecipeCategory implements IRecipeCategory<MegaMachineRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(TamerMod.MODID, "mega_machine");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(TamerMod.MODID, "textures/gui/screens/mega_machine_jei_gui.png");

    private final IDrawable background;
    private final IDrawable icon;
    SlotsLayout slots = new SlotsLayout().addSlot(45,9).addSlot(45,45).addSlot(132,27);
    public MegaMachineRecipeCategory(IGuiHelper helper) {
        this.background = new StaticDrawable(IGuiPrimitive.EMPTY){
            @Override
            public int getWidth() {
                return 160;
            }
            @Override
            public int getHeight() {
                return 80;
            }
        };
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(TamerModBlocks.MEGA_MACHINE_BLOCK.get()));
    }

    @Override
    public RecipeType<MegaMachineRecipe> getRecipeType() {
        return TamerModJEIPlugin.MEGA_MACHINE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("MegaMachine");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }
    public void draw(MegaMachineRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        CategoryGui gui = new CategoryGui(recipe, slots);
        gui.render(poseStack,(int)mouseX,(int)mouseY,0);
        gui.renderTooltip(poseStack,(int)mouseX,(int)mouseY,0);

    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MegaMachineRecipe recipe, IFocusGroup focuses) {
        for(int i = 0;i<2;i++)
            builder.addSlot(RecipeIngredientRole.INPUT, slots.getPos(i).getX(),slots.getPos(i).getY()).addItemStacks(recipe.getIngredientsWithAmount().get(i).getItems());
        builder.addSlot(RecipeIngredientRole.OUTPUT, slots.getPos(2).getX(),slots.getPos(2).getY()).addItemStack(recipe.getResultItem());
    }


}
