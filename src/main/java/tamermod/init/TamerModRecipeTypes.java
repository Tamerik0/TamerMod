package tamermod.init;

import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tamermod.TamerMod;
import tamermod.recipes.FusionRecipe;
import tamermod.recipes.MegaMachineRecipe;

public class TamerModRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, TamerMod.MODID);

    public static final RegistryObject<RecipeType<MegaMachineRecipe>> MEGA_MACHINE_TYPE =
            RECIPE_TYPES.register("mega_machine_crafting", () -> MegaMachineRecipe.type);
    public static final RegistryObject<RecipeType<FusionRecipe>> FUSION_TYPE =
            RECIPE_TYPES.register("fusion_crafting", () -> FusionRecipe.type);


    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
    }
}
