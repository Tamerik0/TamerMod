package tamermod.blocks.blockentities.components;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.tiles.readers.IEUStorage;
import ic2.core.IC2;
import ic2.core.platform.events.ChunkSaver;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import tamermod.blocks.blockentities.AbstractBlockEntityComponent;
import tamermod.blocks.blockentities.ComponentBlockEntity;

public class EUBlockEntityComponent extends AbstractBlockEntityComponent implements IEnergySink, IEUStorage {
    public int energy = 0;
    boolean addedToEnet;
    private boolean isServer;
    boolean loaded = false;
    public int maxEnergy;
    public EUBlockEntityComponent(int maxEnergy){
        this.maxEnergy = maxEnergy;
    }

    @Override
    public void init(ComponentBlockEntity be) {
        super.init(be);
        this.isServer = IC2.PLATFORM.isSimulating();
    }
    @Override
    public int getStoredEU() {
        return energy;
    }
    @Override
    public int getMaxEU() {
        return maxEnergy;
    }
    @Override
    public int getSinkTier() {
        return 6;
    }
    @Override
    public int getRequestedEnergy() {
        return maxEnergy <= 0 ? 0 : maxEnergy - energy;
    }
    @Override
    public int acceptEnergy(Direction direction, int amount, int voltage) {
        int added = Math.min(amount, maxEnergy - energy);
        energy += added;
        getBlockEntity().getLevel().sendBlockUpdated(getBlockEntity().getBlockPos(), getBlockEntity().getLevel().getBlockState(getBlockEntity().getBlockPos()), getBlockEntity().getLevel().getBlockState(getBlockEntity().getBlockPos()), 2);
        return amount > 0 ? amount - added : 0;
    }
    @Override
    public boolean canAcceptEnergy(IEnergyEmitter iEnergyEmitter, Direction direction) {
        return true;
    }
    @Override
    public int getTier() {
        return 6;
    }
    public void saveAdditional(CompoundTag compound) {
        compound.putInt("eu_energy", this.energy);
    }

    public void load(CompoundTag compound) {
        this.energy = compound.getInt("eu_energy");
    }
    @Override
    public void setRemoved() {
        if (this.loaded) {
            this.onUnloaded(false);
        }
    }
    public void onLoaded() {
        this.loaded = true;
        if (this.isServer) {
            ChunkSaver.getSaver(getBlockEntity().getLevel()).addPosition(getBlockEntity().getBlockPos());
        }
        if (this.isServer && !addedToEnet){
            addedToEnet = true;
            EnergyNet.INSTANCE.addTile(this);
        }
    }
    public void onUnloaded(boolean chunk) {
        if (isServer && addedToEnet){
            addedToEnet = false;
            EnergyNet.INSTANCE.removeTile(this);
        }
        loaded = false;
        if (this.isServer) {
            ChunkSaver.getSaver(getBlockEntity().getLevel()).removePosition(getBlockEntity().getBlockPos());
        }
    }
    @Override
    public void handleUpdateTag(CompoundTag tag) {
        IC2.NETWORKING.get(this.isServer).handleInitialChange(getBlockEntity(), tag);
    }
    @Override
    public void onChunkUnloaded() {
        if (loaded) {
            onUnloaded(true);
        }
    }
    @Override
    public void onLoad() {
        if (!loaded) {
            if (isServer) {
                IC2.TICK_HANDLER.addWorldCallback(getBlockEntity().getLevel(), (Level world) -> {
                        EUBlockEntityComponent.this.onLoaded();
                        return 0;
                    });
            } else {
                this.onLoaded();
            }
        }
    }
    @Override
    public Level getWorldObj() {
        return getBlockEntity().getLevel();
    }
    @Override
    public BlockPos getPosition() {
        return getBlockEntity().getBlockPos();
    }
}