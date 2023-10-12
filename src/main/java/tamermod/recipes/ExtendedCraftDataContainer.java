package tamermod.recipes;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import tamermod.blocks.blockentities.components.InventoryComponent;


import java.util.ArrayList;

public class ExtendedCraftDataContainer extends SimpleContainer {
    public ExtendedCraftDataContainer(int size){
        super(size);
    }
    public ArrayList<FluidStack> fluids = new ArrayList<>();
    public ArrayList<ItemStack> getItems(){
        ArrayList<ItemStack> ans = new ArrayList<>();
        for(int i = 0;i<this.getContainerSize();i++){
            ans.add(getItem(i));
        }
        return ans;
    }
    public int eu = 0;
    public int rf = 0;
}
