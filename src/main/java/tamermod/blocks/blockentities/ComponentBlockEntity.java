package tamermod.blocks.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
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
    boolean needsUpdateAndSync = false;
    boolean post_init = false;

    public void scheduleUpdateAndSync() {
        needsUpdateAndSync = true;
    }

    List<AbstractBlockEntityComponent> components;

    public ComponentBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, AbstractBlockEntityComponent... components) {
        super(type, pos, state);
        this.components = List.of(components);
        for (AbstractBlockEntityComponent component : this.components) {
            component.init(this);
        }
        post_init = true;
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
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        for (AbstractBlockEntityComponent component : components) {
            component.handleUpdateTag(tag);
        }
        setChanged();
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag compoundtag = pkt.getTag();
        load(compoundtag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithFullMetadata();
    }

    @Override
    public void setRemoved() {
        for (AbstractBlockEntityComponent component : components) {
            component.preSetRemoved();
        }
        super.setRemoved();
        for (AbstractBlockEntityComponent component : components) {
            component.postSetRemoved();
        }
    }

    @Override
    public void onChunkUnloaded() {
        for (AbstractBlockEntityComponent component : components) {
            component.preOnChunkUnloaded();
        }
        super.onChunkUnloaded();
        for (AbstractBlockEntityComponent component : components) {
            component.postOnChunkUnloaded();
        }
    }

    @Override
    public void onLoad() {
        for (AbstractBlockEntityComponent component : components) {
            component.preOnLoad();
        }
        super.onLoad();
        for (AbstractBlockEntityComponent component : components) {
            component.postOnLoad();
        }
        setChanged();
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
        for (AbstractBlockEntityComponent component : components) {
            if (post_init) {
                if (component.tick_cd >= component.tick_delay) {
                    component.postInit();
                    component.tick_cd = 0;
                } else {
                    component.tick_cd += 1;
                }
            }
            component.tick();
        }
        post_init = false;
        if (needsUpdateAndSync) {
            setChanged();
            getLevel().sendBlockUpdated(getBlockPos(), getLevel().getBlockState(getBlockPos()), getLevel().getBlockState(getBlockPos()), 2);
            needsUpdateAndSync = false;
        }
    }

    public void setChanged() {
        super.setChanged();
    }
}
