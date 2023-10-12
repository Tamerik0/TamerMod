package tamermod.blocks.blockentities.components;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import tamermod.blocks.blockentities.AbstractBlockEntityComponent;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;


public class InventoryComponent extends AbstractBlockEntityComponent implements  WorldlyContainer {
    private final LazyOptional<IItemHandlerModifiable>[] handlers = SidedInvWrapper.create(this, Direction.values());

    public IItemHandlerModifiable getHandler() {
        AtomicReference<IItemHandlerModifiable> handler = new AtomicReference();
        handlers[0].ifPresent((x) -> {
            handler.set(x);
        });
        return handler.get();
    }

    private NonNullList<ItemStack> stacks;

    public InventoryComponent(int size) {
        stacks = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    public int getSize() {
        return stacks.size();
    }
    public boolean accessDirection(Direction dir){
        return true;
    }
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (capability == ForgeCapabilities.ITEM_HANDLER) {
            if (facing == null)
                facing = Direction.DOWN;
            if (accessDirection(facing)) {
                return handlers[facing.ordinal()].cast();
            }
        }
        return null;
    }

    @Override
    public void load(CompoundTag compound) {
        stacks = NonNullList.withSize((compound.contains("Size", Tag.TAG_INT) ? compound.getInt("Size") : stacks.size()), ItemStack.EMPTY);
        ListTag tagList = compound.getCompound("inventory").getList("Items", Tag.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");
            if (slot >= 0 && slot < stacks.size()) {
                getHandler().setStackInSlot(slot, ItemStack.of(itemTags));
            }
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < stacks.size(); i++) {
            if (!stacks.get(i).isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                stacks.get(i).save(itemTag);
                nbtTagList.add(itemTag);
            }
        }
        CompoundTag nbt = new CompoundTag();
        nbt.put("Items", nbtTagList);
        nbt.putInt("Size", stacks.size());
        compound.put("inventory", nbt);
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return IntStream.range(0, this.getContainerSize()).toArray();
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    public int getContainerSize() {
        return stacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.stacks)
            if (!itemstack.isEmpty())
                return false;
        return true;
    }

    public ItemStack getItem(int slot) {
        return stacks.get(slot);
    }

    public ItemStack removeItem(int p_59613_, int p_59614_) {
        ItemStack itemstack = ContainerHelper.removeItem(this.getItems(), p_59613_, p_59614_);
        if (!itemstack.isEmpty()) {
            this.setChanged();
        }
        return itemstack;
    }

    public NonNullList<ItemStack> getItems() {
        return stacks;
    }

    public ItemStack removeItemNoUpdate(int p_59630_) {
        return ContainerHelper.takeItem(this.getItems(), p_59630_);
    }

    public void setItem(int p_59616_, ItemStack p_59617_) {
        this.getItems().set(p_59616_, p_59617_);
        if (p_59617_.getCount() > this.getMaxStackSize()) {
            p_59617_.setCount(this.getMaxStackSize());
        }
        this.setChanged();
    }

    public boolean stillValid(Player p_59619_) {
        if (getBlockEntity().getLevel().getBlockEntity(getBlockEntity().getBlockPos()) != getBlockEntity()) {
            return false;
        } else {
            return !(p_59619_.distanceToSqr((double) getBlockEntity().getBlockPos().getX() + 0.5D, (double) getBlockEntity().getBlockPos().getY() + 0.5D, (double) getBlockEntity().getBlockPos().getZ() + 0.5D) > 64.0D);
        }
    }

    public void clearContent() {
        this.getItems().clear();
    }

    public void setChanged() {
        getBlockEntity().scheduleUpdateAndSync();
    }
}
