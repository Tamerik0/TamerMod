package tamermod.blocks.blockentities.components.fusion_core;

import com.mojang.math.Vector3d;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tamermod.blocks.BaseEntityBlock;
import tamermod.blocks.blockentities.AbstractBlockEntityComponent;
import tamermod.blocks.blockentities.ComponentBlockEntity;
import tamermod.blocks.blockentities.components.InventoryComponent;
import tamermod.blocks.blockentities.components.injectors.base.InjectorComponent;
import tamermod.recipes.ExtendedCraftDataContainer;
import tamermod.recipes.FusionRecipe;

import java.util.ArrayList;
import java.util.List;

public class FusionCoreComponent extends AbstractBlockEntityComponent {
    int findInjectorsCD = 9999999;
    int findInjectorsDelay = 200;
    int maxInjectorDistance = 6;
    int maxYInjectorDistance = 1;
    public boolean isCrafting;
    public int progress = 0, maxProgress = 0;
    public FusionRecipe currentRecipe = null;
    public int euCraftBuffer, rfCraftBuffer;
    InventoryComponent inventoryComponent;
    public ArrayList<InjectorComponent> craftingInjectors = new ArrayList<>();
    public List<InjectorComponent> connectedInjectors = new ArrayList<>();
    ArrayList<BlockPos> _injectors = new ArrayList<>();
    boolean lastRedstone;

    public void setBroken() {
//        System.out.println("setBroken/");
        for (var i : connectedInjectors) {
            i.connectedCores.remove(this);
            i.findForCore();
        }
        if (isCrafting) {
            for (var i : craftingInjectors) {
                i.stopCrafting();
            }
        }
    }

    public void postInit() {
//        System.out.println("postInit/");
        if (isCrafting) {
            for (var pos : _injectors) {
                craftingInjectors.add(BaseEntityBlock.getBlockEntity(blockEntity.getLevel(), pos).getComponent(InjectorComponent.class));
            }
            currentRecipe = getRecipe(craftingInjectors);
        }
        findForInjectors();
    }

    public void redstoneSignalUpdated(int value) {
//        System.out.println("redstoneSignalUpdated/");
        if (!lastRedstone && value != 0) {
            lastRedstone = true;
            redstoneOn();
        }
        if(value == 0)
            lastRedstone = false;
    }

    public void redstoneOn() {
//        System.out.println("redstoneOn/");
        if (!isCrafting) {
//            System.out.println("!crafting");
            if (hasRecipe()) {
                startCrafting();
            }
        }
    }

    public void saveAdditional(CompoundTag compound) {
//        System.out.println("saveAdditional/");
        compound.putBoolean("isCrafting", isCrafting);
        compound.putInt("progress", progress);
        if (currentRecipe != null)
            compound.putInt("maxprogress", currentRecipe.craftTime);
        else
            compound.putInt("maxprogress", 0);
        if (isCrafting) {
            var injectors = new ListTag();
            for (var i : craftingInjectors) {
                var pos = i.getBlockEntity().getBlockPos();
                injectors.add(new IntArrayTag(new int[]{pos.getX(), pos.getY(), pos.getZ()}));
            }
            compound.put("crafting_injectors", injectors);
        }
    }

    public void load(CompoundTag compound) {
//        System.out.println("load/");
        isCrafting = compound.getBoolean("isCrafting");
        progress = compound.getInt("progress");
        maxProgress = compound.getInt("maxprogress");
        if (isCrafting) {
            var injectors = compound.getList("crafting_injectors", Tag.TAG_INT_ARRAY);
            for (int i = 0; i < injectors.size(); i++) {
                var pos = injectors.getIntArray(i);
                _injectors.add(new BlockPos(pos[0], pos[1], pos[2]));
            }
        }
    }

    public void findForInjectors() {
//        System.out.println("findForInjectors/");
        connectedInjectors.clear();
        var world = getBlockEntity().getLevel();
        var pos = getBlockEntity().getBlockPos();
        for (int x = pos.getX() - maxInjectorDistance; x <= pos.getX() + maxInjectorDistance; x++) {
            for (int z = pos.getZ() - maxInjectorDistance; z <= pos.getZ() + maxInjectorDistance; z++) {
                for (int y = pos.getY() - maxYInjectorDistance; y <= pos.getY() + maxYInjectorDistance; y++) {
                    if (world.getBlockEntity(new BlockPos(x, y, z)) != null) {
                        if (world.getBlockEntity(new BlockPos(x, y, z)) instanceof ComponentBlockEntity injectorBe) {
                            var injector = injectorBe.getComponent(InjectorComponent.class);
                            if (!connectedInjectors.contains(injector)) {
                                if (injector != null) {
                                    var injectorDir = injectorBe.getBlockState().getValue(tamermod.blocks.BaseEntityBlock.FACING).getNormal();
                                    boolean connect = false;
                                    if (Math.abs(injectorBe.getBlockPos().getX() - getBlockEntity().getBlockPos().getX()) > Math.abs(injectorBe.getBlockPos().getZ() - getBlockEntity().getBlockPos().getZ())) {
                                        if (injectorDir.getX() == Math.signum(getBlockEntity().getBlockPos().getX() - injectorBe.getBlockPos().getX())) {
                                            connect = true;
                                        }
                                    } else if (Math.abs(injectorBe.getBlockPos().getX() - getBlockEntity().getBlockPos().getX()) < Math.abs(injectorBe.getBlockPos().getZ() - getBlockEntity().getBlockPos().getZ())) {
                                        if (injectorDir.getZ() == Math.signum(getBlockEntity().getBlockPos().getZ() - injectorBe.getBlockPos().getZ())) {
                                            connect = true;
                                        }
                                    }
                                    if (connect) {
                                        injector.connectedCores.add(this);
                                        connectedInjectors.add(injector);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public void init(ComponentBlockEntity be) {
//        System.out.println("init/");
        super.init(be);
        inventoryComponent = be.getComponent(InventoryComponent.class);
    }

    public void startCrafting() {
//        System.out.println("startCrafting/");
        currentRecipe = getRecipe();
        isCrafting = true;
        progress = 0;
        maxProgress = currentRecipe.craftTime;
        var ingr = new ArrayList<>(currentRecipe.getIngredients());
        for (int i = 0; i < connectedInjectors.size(); i++) {
            var inv = connectedInjectors.get(i).getBlockEntity().getComponent(InventoryComponent.class);
            for (int j = 0; j < ingr.size(); j++) {
                if (ingr.get(j).test(inv.getItem(0))) {
                    craftingInjectors.add(connectedInjectors.get(i));
                    connectedInjectors.get(i).isCrafting = true;
                    connectedInjectors.get(i).craftingCore = this;
                    connectedInjectors.get(i).getBlockEntity().scheduleUpdateAndSync();
                    ingr.remove(j);
                    break;
                }
            }
        }
    }

    public void stopCrafting() {
//        System.out.println("stopCrafting/");
        progress = 0;
        maxProgress = 0;
        currentRecipe = null;
        isCrafting = false;
        for (var injector : craftingInjectors) {
            injector.stopCrafting();
        }
        findInjectorsCD = 999999;
        blockEntity.scheduleUpdateAndSync();
    }

    public void tick() {
        if (getBlockEntity().getLevel().isClientSide()) {
            return;
        }
//        System.out.println("tick/");
//        System.out.println(connectedInjectors.size());
//        System.out.println(craftingInjectors.size());
        findInjectorsCD++;
        if (findInjectorsCD >= findInjectorsDelay) {
            findForInjectors();
            findInjectorsCD = 0;
        }
        if (isCrafting) {
//            System.out.println("crafting...");
            System.out.println(craftingInjectors.size());
            System.out.println(connectedInjectors.size());
            if (currentRecipe == getRecipe(craftingInjectors)) {
                if (currentRecipe == null) {
                    stopCrafting();
                    getBlockEntity().scheduleUpdateAndSync();
                    return;
                }
                int euptick = currentRecipe.euInput / currentRecipe.craftTime;
                int rfptick = currentRecipe.rfInput / currentRecipe.craftTime;
                if (euCraftBuffer >= euptick && rfCraftBuffer >= rfptick) {
                    euCraftBuffer -= euptick;
                    rfCraftBuffer -= rfptick;
                    progress++;
                }
                if (progress >= currentRecipe.craftTime) {
                    craftItem();
                    stopCrafting();
                }
            } else {
                stopCrafting();
            }
        }
        getBlockEntity().scheduleUpdateAndSync();
    }

    FusionRecipe getRecipe() {
//        System.out.println("getRecipe1/");
        ArrayList<InjectorComponent> injectors = new ArrayList<>();
        for(var i:connectedInjectors){
            if(!i.isCrafting)
                injectors.add(i);
        }
        return getRecipe(injectors);
    }
    FusionRecipe getRecipe(List<InjectorComponent> injectors) {
//        System.out.println("getRecipe/");
        System.out.println(injectors.size());
        Level level = getBlockEntity().getLevel();
        System.out.println(injectors.size() + 1);
        ExtendedCraftDataContainer inventory = new ExtendedCraftDataContainer(injectors.size() + 1);
        System.out.println(inventory.getContainerSize());
        inventory.setItem(0, inventoryComponent.getItem(0));
        for (int i = 0; i < injectors.size(); i++) {
            inventory.setItem(i + 1, injectors.get(i).getBlockEntity().getComponent(InventoryComponent.class).getItem(0));
        }
        System.out.println(inventory.getContainerSize());
        var rec = level.getRecipeManager().getRecipeFor(FusionRecipe.type, inventory, level);
        System.out.println(inventory.getContainerSize());
        if (rec.isPresent()) {
//            System.out.println("recipe");
            return rec.get();
        }
//        System.out.println("null");
        return null;
    }

    private void craftItem() {
//        System.out.println("craftItem/");
        if (currentRecipe != null) {
            var ingr = new ArrayList<>(currentRecipe.getIngredientsWithAmount());
            for (int i = 0; i < craftingInjectors.size(); i++) {
                var inv = craftingInjectors.get(i).getBlockEntity().getComponent(InventoryComponent.class);
                for (int j = 0; j < ingr.size(); j++) {
                    if (ingr.get(j).test(inv.getItem(0))) {
                        inv.getItem(0).shrink(ingr.get(j).count);
                        ingr.remove(j);
                        break;
                    }
                }
            }

            inventoryComponent.getHandler().setStackInSlot(0, currentRecipe.getResultItem());
            stopCrafting();
        }
    }

    private boolean hasRecipe() {
//        System.out.println("hasRecipe/");
        FusionRecipe recipe = getRecipe();
        return recipe != null;
    }
}
