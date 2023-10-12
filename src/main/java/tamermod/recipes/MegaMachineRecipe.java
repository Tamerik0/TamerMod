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

public class MegaMachineRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<IngredientStack> itemInput;
    public int euInput, rfInput, craftTime;
    public FluidStack fluidInput;

    public MegaMachineRecipe(ResourceLocation id, ItemStack output,
                             NonNullList<IngredientStack> itemInput, int euInput, int rfInput, int craftTime, FluidStack fluidInput) {
        this.id = id;
        this.output = output;
        this.itemInput = itemInput;
        this.euInput = euInput;
        this.rfInput = rfInput;
        this.craftTime = craftTime;
        this.fluidInput = fluidInput;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        for(int i = 0;i<itemInput.size();i++){
            if(!itemInput.get(i).test(pContainer.getItem(i)))
                return false;
        }
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ans = NonNullList.create();
        for(int i=0;i<itemInput.size();i++)
            ans.add(itemInput.get(i).ingredient);
        return ans;
    }
    public NonNullList<IngredientStack> getIngredientsWithAmount() {
        return itemInput;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
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

    public static RecipeType<MegaMachineRecipe> type = new RecipeType<>(){};


    public static class Serializer implements RecipeSerializer<MegaMachineRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(TamerMod.MODID, "mega_machine_crafting");

        @Override
        public MegaMachineRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            int euInput = GsonHelper.getAsInt(pSerializedRecipe, "euInput");
            int rfInput = GsonHelper.getAsInt(pSerializedRecipe, "rfInput");
            int craftTime = GsonHelper.getAsInt(pSerializedRecipe, "craftTime");
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<IngredientStack> inputs = NonNullList.withSize(ingredients.size(), IngredientStack.EMPTY);
            FluidStack fluidInput = FluidUtils.getFluidStackFromJson(pSerializedRecipe.getAsJsonObject("fluidInput"));
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, IngredientStack.fromJson(ingredients.get(i)));
            }
            return new MegaMachineRecipe(pRecipeId, output, inputs, euInput, rfInput, craftTime, fluidInput);
        }

        @Override
        public @Nullable MegaMachineRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<IngredientStack> inputs = NonNullList.withSize(buf.readInt(), IngredientStack.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, IngredientStack.fromNetwork(buf));
            }
            ItemStack output = buf.readItem();
            int euInput = buf.readInt();
            int rfInput = buf.readInt();
            int craftTime = buf.readInt();
            FluidStack fluidInput = buf.readFluidStack();
            return new MegaMachineRecipe(id, output, inputs, euInput, rfInput, craftTime, fluidInput);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, MegaMachineRecipe recipe) {
            buf.writeInt(recipe.getIngredientsWithAmount().size());
            for (IngredientStack ing : recipe.getIngredientsWithAmount()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(), false);
            buf.writeInt(recipe.euInput);
            buf.writeInt(recipe.rfInput);
            buf.writeInt(recipe.craftTime);
            buf.writeFluidStack(recipe.fluidInput);
        }
    }
}
