package tamermod.blocks.blockentities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public abstract class AbstractBlockEntityComponent {
    protected ComponentBlockEntity blockEntity;
    public ComponentBlockEntity getBlockEntity(){
        return blockEntity;
    }
    public void init(ComponentBlockEntity be){
        blockEntity = be;
    };
    public int tick_delay = 1;
    public int tick_cd = 9999;
    public void tick(){};
    public void postInit(){}
    public void saveAdditional(CompoundTag compound){};
    public void load(CompoundTag compound){};
    public void handleUpdateTag(CompoundTag tag){};
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) { return null; }
    public void preSetRemoved(){};
    public void preOnChunkUnloaded(){};
    public void preOnLoad(){};

    public void postSetRemoved(){};
    public void postOnChunkUnloaded(){};
    public void postOnLoad(){};
}
