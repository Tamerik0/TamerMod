package tamermod.blocks.blockentities.components;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import tamermod.blocks.blockentities.AbstractBlockEntityComponent;
import tamermod.blocks.blockentities.ComponentBlockEntity;
import tamermod.recipes.MegaMachineRecipe;

import java.util.List;

public class MegaMachineCraftingComponent extends AbstractBlockEntityComponent {
    InventoryComponent inventoryComponent;
    EUBlockEntityComponent euComponent;
    RFBlockEntityComponent rfComponent;
    FluidStorageBlockEntityComponent fluidStorageComponent;

    public int progress=0,maxProgress=0;
    public MegaMachineRecipe currentRecipe = null;
    @Override
    public void init(ComponentBlockEntity be) {
        super.init(be);
        inventoryComponent = this.getBlockEntity().getComponent(InventoryComponent.class);
        euComponent = this.getBlockEntity().getComponent(EUBlockEntityComponent.class);
        rfComponent = this.getBlockEntity().getComponent(RFBlockEntityComponent.class);
        fluidStorageComponent = this.getBlockEntity().getComponent(FluidStorageBlockEntityComponent.class);
    }
    public void tick() {
        if(getBlockEntity().getLevel().isClientSide()) {
            return;
        }
        if(hasRecipe()) {
            if(currentRecipe == null){
                currentRecipe=getRecipe();
            }
            if(currentRecipe==getRecipe()){
                int euptick = currentRecipe.euInput/currentRecipe.craftTime;
                int rfptick = currentRecipe.rfInput/currentRecipe.craftTime;
                if(euComponent.getStoredEU()>=euptick&&rfComponent.getStoredRF()>=rfptick){
                    euComponent.energy-=euptick;
                    rfComponent.energyStorage.extractEnergy(rfptick,false);
                    progress++;
                }
                if(progress>=currentRecipe.craftTime){
                    craftItem();
                    progress=0;
                    currentRecipe = null;
                }
            }else{
                progress=0;
                currentRecipe = null;
            }
        }
        else{
            progress=0;
            currentRecipe = null;
        }
        getBlockEntity().scheduleUpdateAndSync();
    }
    MegaMachineRecipe getRecipe(){
        Level level = getBlockEntity().getLevel();
        SimpleContainer inventory = new SimpleContainer(inventoryComponent.getSize());
        for (int i = 0; i < inventoryComponent.getSize(); i++) {
            inventory.setItem(i, inventoryComponent.getItem(i));
        }
        List<MegaMachineRecipe> recipes = level.getRecipeManager().getRecipesFor(MegaMachineRecipe.type, inventory, level);
        for(int i = 0;i<recipes.size();i++){
            if(recipes.get(i).fluidInput==null || recipes.get(i).fluidInput == FluidStack.EMPTY
                    || fluidStorageComponent.fluidTank.getFluid().containsFluid(recipes.get(i).fluidInput)){
                return recipes.get(i);
            }
        }
        return null;
    }
    private void craftItem() {
        if(currentRecipe!=null) {
            for(int i = 0;i<currentRecipe.getIngredients().size();i++) {
                inventoryComponent.getHandler().extractItem(i, currentRecipe.getIngredientsWithAmount().get(i).count, false);
            }
            inventoryComponent.getHandler().setStackInSlot(2, new ItemStack(currentRecipe.getResultItem().getItem(),
                    inventoryComponent.getHandler().getStackInSlot(2).getCount() + currentRecipe.getResultItem().getCount()));
            fluidStorageComponent.fluidTank.drain(currentRecipe.fluidInput, IFluidHandler.FluidAction.EXECUTE);
        }
    }

    private boolean hasRecipe() {
        MegaMachineRecipe recipe = getRecipe();
        return recipe!=null && (inventoryComponent.getItem(2).getMaxStackSize() >= inventoryComponent.getItem(2).getCount()+recipe.getResultItem().getCount() &&
                inventoryComponent.getItem(2).getItem() == recipe.getResultItem().getItem() || inventoryComponent.getItem(2).isEmpty());
    }
    public void saveAdditional(CompoundTag compound){
        compound.putInt("progress", progress);
        if(currentRecipe!=null)
            compound.putInt("maxprogress", currentRecipe.craftTime);
        else
            compound.putInt("maxprogress", 0);
    };
    public void load(CompoundTag compound){
        progress = compound.getInt("progress");
        maxProgress = compound.getInt("maxprogress");
    };
}
