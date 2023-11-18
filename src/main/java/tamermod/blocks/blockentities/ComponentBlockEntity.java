package tamermod.blocks.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import java.util.List;

public class ComponentBlockEntity extends BlockEntity {
    boolean needsSync = true;
    int initFhase = 0;

    public void scheduleUpdateAndSync() {
        needsSync = true;
    }

    List<AbstractBlockEntityComponent> components;

    public ComponentBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, AbstractBlockEntityComponent... components) {
        super(type, pos, state);
        this.components = List.of(components);
        for (AbstractBlockEntityComponent component : this.components) {
            component.init(this);
        }
    }

    public <T> T getComponent(Class<T> type) {
        for (AbstractBlockEntityComponent component : components) {
            if (type.isInstance(component))
                return (T) component;
        }
        return null;
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        for (AbstractBlockEntityComponent component : components) {
            component.saveAdditional(compound);
        }
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        for (AbstractBlockEntityComponent component : components) {
            component.load(compound);
        }
        setChanged();
        if(initFhase == 0)
            initFhase = 1;
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithFullMetadata();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        for (AbstractBlockEntityComponent component : components) {
            component.handleUpdateTag(tag);
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void setRemoved() {
        for (AbstractBlockEntityComponent component : components) {
            component.setRemoved();
        }
        super.setRemoved();
    }

    @Override
    public void onChunkUnloaded() {
        for (AbstractBlockEntityComponent component : components) {
            component.onChunkUnloaded();
        }
        super.onChunkUnloaded();
    }

    @Override
    public void onLoad() {
//        System.out.println("onLoad/");
        if (!getLevel().isClientSide() && initFhase == 0) {
//            System.out.println("kek");
            initFhase = 2;
            for (AbstractBlockEntityComponent component : components) {
//                System.out.println("rofl");
                component.postInit();
            }
        }
        for (AbstractBlockEntityComponent component : components) {
            component.onLoad();
        }
        super.onLoad();
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        for (AbstractBlockEntityComponent component : components) {
            LazyOptional<T> ans = component.getCapability(capability, facing);
            if (ans != null)
                return ans;
        }
        return super.getCapability(capability, facing);
    }

    public void tick(Level lvl, BlockPos pos, BlockState state) {
//        System.out.println("TETick/");
        if (needsSync) {
            if (!lvl.isClientSide()) {
                setChanged();
                getLevel().sendBlockUpdated(getBlockPos(), getLevel().getBlockState(getBlockPos()), getLevel().getBlockState(getBlockPos()), 0);
                needsSync = false;
            }
        }
        for (AbstractBlockEntityComponent component : components) {
            if (initFhase >= 1) {
                if (initFhase == 1) {
//                    System.out.println("PostInit");
                    component.postInit();
                }
                if (component.tick_cd >= component.tick_delay) {
                    component.tick();
                    component.tick_cd = 0;
                } else {
                    component.tick_cd += 1;
                }
            }
        }
//        System.out.println("aaaaaa");
        if (initFhase == 1)
            initFhase = 2;
//        System.out.println(initFhase);
    }
}
