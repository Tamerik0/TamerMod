package tamermod.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import tamermod.TamerMod;
import tamermod.client.gui.BaseScreen;
import tamermod.recipes.MegaMachineRecipe;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class TamerModJEIPlugin implements IModPlugin {
    public static RecipeType<MegaMachineRecipe> MEGA_MACHINE_TYPE =
            new RecipeType<>(MegaMachineRecipeCategory.UID, MegaMachineRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(TamerMod.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new MegaMachineRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }
    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registry) {
        registry.addGenericGuiContainerHandler(BaseScreen.class, new aboba());
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<MegaMachineRecipe> recipesInfusing = rm.getAllRecipesFor(MegaMachineRecipe.type);
        registration.addRecipes(MEGA_MACHINE_TYPE, recipesInfusing);
    }
}
