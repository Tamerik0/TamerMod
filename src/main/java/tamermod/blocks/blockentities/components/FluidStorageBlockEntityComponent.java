package tamermod.blocks.blockentities.components;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;
import tamermod.blocks.BaseEntityBlock;
import tamermod.blocks.blockentities.AbstractBlockEntityComponent;

public class FluidStorageBlockEntityComponent extends AbstractBlockEntityComponent {
    public FluidTank fluidTank;
    public FluidStorageBlockEntityComponent(int size){
        fluidTank = new FluidTank(size);
    }
    @Override
    public void load(CompoundTag compound) {
        if (compound.get("fluidTank") instanceof CompoundTag compoundTag)
            fluidTank.readFromNBT(compoundTag);
    }
    @Override
    public void saveAdditional(CompoundTag compound) {
        compound.put("fluidTank", fluidTank.writeToNBT(new CompoundTag()));
    }
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        Direction blockFacing = getBlockEntity().getBlockState().getValue(BaseEntityBlock.FACING);
        if(capability == ForgeCapabilities.FLUID_HANDLER&&facing.equals(blockFacing))
            return LazyOptional.of(() -> fluidTank).cast();
        return null;
    }
}
