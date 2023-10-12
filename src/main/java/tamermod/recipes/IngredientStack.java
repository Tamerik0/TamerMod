package tamermod.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class IngredientStack {
    public static final IngredientStack EMPTY = new IngredientStack(0,Ingredient.EMPTY);
    public int count;
    public Ingredient ingredient;
    public static IngredientStack fromJson(JsonElement json){
        if(json.isJsonObject()){
            JsonObject obj = json.getAsJsonObject();
            int amount=1;
            Ingredient ingredient;
            if(obj.has("ingredient")){
                ingredient = Ingredient.fromJson(obj.get("ingredient"));
            }else{
                ingredient = Ingredient.fromJson(obj);
            }
            if(obj.has("count")){
                amount=obj.get("count").getAsInt();
            }
            return new IngredientStack(amount, ingredient);
        }
        return null;
    }
    public static IngredientStack fromNetwork(FriendlyByteBuf buf){
        IngredientStack ans = new IngredientStack(0,Ingredient.EMPTY);
        ans.count =buf.readInt();
        ans.ingredient=Ingredient.fromNetwork(buf);
        return ans;
    }
    public void toNetwork(FriendlyByteBuf buf){
        buf.writeInt(count);
        ingredient.toNetwork(buf);
    }
    public IngredientStack(int count, Ingredient ingredient){
        this.count = count;
        this.ingredient=ingredient;
    }
    public List<ItemStack> getItems(){
        List<ItemStack> ans = List.of(ingredient.getItems());
        for(ItemStack stack:ans){
            stack.setCount(count);
        }
        return ans;
    }
    public boolean test(ItemStack itemStack){
        return ingredient.test(itemStack)&&itemStack.getCount()>= count;
    }
    public boolean equals(Object obj){
        if(obj instanceof IngredientStack ingr){
            return count ==ingr.count &&ingredient.equals(ingr.ingredient);
        }
        return false;
    }
}
