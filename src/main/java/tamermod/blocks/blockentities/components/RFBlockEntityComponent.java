package tamermod.blocks.blockentities.components;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import tamermod.blocks.blockentities.AbstractBlockEntityComponent;

import javax.annotation.Nullable;

public class RFBlockEntityComponent extends AbstractBlockEntityComponent {
    public final EnergyStorage energyStorage = new EnergyStorage(400000, 999999999, 999999999, 0);
    public int getStoredRF(){
        return energyStorage.getEnergyStored();
    }
    public int getMaxStoredRF(){
        return  energyStorage.getMaxEnergyStored();
    }
    @Override
    public void load(CompoundTag compound){
        if (compound.get("energyStorage") instanceof IntTag intTag)
            energyStorage.deserializeNBT(intTag);
    }
    @Override
    public void saveAdditional(CompoundTag compound){
        compound.put("energyStorage", energyStorage.serializeNBT());
    }
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (capability == ForgeCapabilities.ENERGY)
            return LazyOptional.of(() -> energyStorage).cast();
        return null;
    }
}
