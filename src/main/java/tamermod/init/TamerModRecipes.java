package tamermod.init;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tamermod.TamerMod;
import tamermod.recipes.FusionRecipe;
import tamermod.recipes.MegaMachineRecipe;

public class TamerModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TamerMod.MODID);

    public static final RegistryObject<RecipeSerializer<MegaMachineRecipe>> MEGA_MACHINE_SERIALIZER =
            SERIALIZERS.register("mega_machine_crafting", () -> MegaMachineRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<FusionRecipe>> FUSION_SERIALIZER =
            SERIALIZERS.register("fusion_crafting", () -> FusionRecipe.Serializer.INSTANCE);


    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
