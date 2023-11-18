package tamermod.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;
import tamermod.TamerMod;
import tamermod.fluids.FluidUtils;

import java.util.ArrayList;
import java.util.List;

public class FusionRecipe implements Recipe<ExtendedCraftDataContainer> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<IngredientStack> itemInput;
    public int euInput, rfInput, craftTime, tier;
    public FluidStack fluidInput;

    public FusionRecipe(ResourceLocation id, ItemStack output,
                        NonNullList<IngredientStack> itemInput, int euInput, int rfInput, int craftTime, FluidStack fluidInput, int tier) {
        this.id = id;
        this.output = output;
        this.itemInput = itemInput;
        this.euInput = euInput;
        this.rfInput = rfInput;
        this.craftTime = craftTime;
        this.fluidInput = fluidInput;
        this.tier = tier;
    }

    private boolean recCheck(ArrayList<IngredientStack> ingr, ArrayList<ItemStack> stacks) {
        if (ingr.size() > stacks.size())
            return false;
        if (ingr.size() == 0)
            return true;
        for (int i = 0; i < stacks.size(); i++) {
            if (ingr.get(0).test(stacks.get(i))) {
                var ingr1 = (ArrayList<IngredientStack>) ingr.clone();
                ingr1.remove(0);
                var stacks1 = (ArrayList<ItemStack>) stacks.clone();
                stacks1.remove(i);
                if (recCheck(ingr1, stacks1))
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean matches(ExtendedCraftDataContainer pContainer, Level pLevel) {
//        System.out.println("matches/");
//        System.out.println("bbaa");
//        System.out.println(pContainer.getContainerSize());
//        var g = pContainer.getContainerSize() + 1 - 1;
//        for(int i = 0; i < g; i++){
//            System.out.println("aaaabbxgsdf");
//            System.out.println(pContainer.getContainerSize());
//            System.out.println(i);
//            System.out.println(pContainer.getItem(i));
//            System.out.println("ffaaaabbxaa");
//        }
//        System.out.println("ksjdbmsd");
        if (!itemInput.get(0).test(pContainer.getItem(0)))
            return false;
        var items = (ArrayList<ItemStack>) pContainer.getItems().clone();
        items.remove(0);
        var ingr = new ArrayList<IngredientStack>();
        for (var item : itemInput) {
            if (item != IngredientStack.EMPTY) {
                ingr.add(item);
            }
        }
        ingr.remove(0);
//        for(int i = 0; i<ingr.size(); i++){
//            System.out.println("b");
//            System.out.println(ingr.get(i));
//        }
//        System.out.println();
//        System.out.println("/matches");
        return recCheck(ingr, items);
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ans = NonNullList.create();
        for (int i = 0; i < itemInput.size(); i++)
            ans.add(itemInput.get(i).ingredient);
        return ans;
    }

    public NonNullList<IngredientStack> getIngredientsWithAmount() {
        return itemInput;
    }

    @Override
    public ItemStack assemble(ExtendedCraftDataContainer pContainer) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return type;
    }

    public static RecipeType<FusionRecipe> type = new RecipeType<>() {};

    public static class Serializer implements RecipeSerializer<FusionRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public FusionRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            int euInput = GsonHelper.getAsInt(pSerializedRecipe, "euInput", 0);
            int rfInput = GsonHelper.getAsInt(pSerializedRecipe, "rfInput", 0);
            int craftTime = GsonHelper.getAsInt(pSerializedRecipe, "craftTime");
            int tier = GsonHelper.getAsInt(pSerializedRecipe, "tier");
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<IngredientStack> inputs = NonNullList.withSize(ingredients.size(), IngredientStack.EMPTY);
            FluidStack fluidInput = FluidStack.EMPTY;
            if (pSerializedRecipe.has("fluidInput"))
                fluidInput = FluidUtils.getFluidStackFromJson(pSerializedRecipe.getAsJsonObject("fluidInput"));

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, IngredientStack.fromJson(ingredients.get(i)));
            }
            return new FusionRecipe(pRecipeId, output, inputs, euInput, rfInput, craftTime, fluidInput, tier);
        }

        @Override
        public @Nullable FusionRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<IngredientStack> inputs = NonNullList.withSize(buf.readInt(), IngredientStack.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, IngredientStack.fromNetwork(buf));
            }
            ItemStack output = buf.readItem();
            int euInput = buf.readInt();
            int rfInput = buf.readInt();
            int craftTime = buf.readInt();
            int tier = buf.readInt();
            FluidStack fluidInput = buf.readFluidStack();
            return new FusionRecipe(id, output, inputs, euInput, rfInput, craftTime, fluidInput, tier);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, FusionRecipe recipe) {
            buf.writeInt(recipe.getIngredientsWithAmount().size());
            for (IngredientStack ing : recipe.getIngredientsWithAmount()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(), false);
            buf.writeInt(recipe.euInput);
            buf.writeInt(recipe.rfInput);
            buf.writeInt(recipe.craftTime);
            buf.writeInt(recipe.tier);
            buf.writeFluidStack(recipe.fluidInput);
        }
    }
}
