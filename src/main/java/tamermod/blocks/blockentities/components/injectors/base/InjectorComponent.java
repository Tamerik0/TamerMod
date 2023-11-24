package tamermod.blocks.blockentities.components.injectors.base;

import codechicken.lib.vec.Vector3;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import tamermod.blocks.BaseEntityBlock;
import tamermod.blocks.blockentities.AbstractBlockEntityComponent;
import tamermod.blocks.blockentities.ComponentBlockEntity;
import tamermod.blocks.blockentities.components.fusion_core.FusionCoreComponent;

import java.util.ArrayList;

public class InjectorComponent extends AbstractBlockEntityComponent {
    public boolean isCrafting;

    public ArrayList<FusionCoreComponent> connectedCores = new ArrayList<>();
    public FusionCoreComponent craftingCore;
    BlockPos _corePos;

    public void init(ComponentBlockEntity be) {
        super.init(be);
        tick_delay = 2;
    }

    public void onLoad() {
        findForCore();
        if (isCrafting) {
            System.out.println(_corePos);
            craftingCore = BaseEntityBlock.getBlockEntity(blockEntity.getLevel(), _corePos).getComponent(FusionCoreComponent.class);
        }
    }

    public void stopCrafting() {
        craftingCore = null;
        isCrafting = false;
        blockEntity.scheduleUpdateAndSync();
    }

    public void setBroken() {
        if (craftingCore != null) {
            craftingCore.stopCrafting();
            for (var core : connectedCores)
                core.connectedInjectors.remove(this);
        }
    }

    int maxCoreDistance = 10;

    public void tick() {
        if (!blockEntity.getLevel().isClientSide() && craftingCore != null) {
            var _self_pos = blockEntity.getBlockPos();
            var self_pos = new Vector3d(_self_pos.getX(), _self_pos.getY(), _self_pos.getZ());
            var _core_pos = craftingCore.getBlockEntity().getBlockPos();
            var core_pos = new Vector3d(_core_pos.getX(), _core_pos.getY(), _core_pos.getZ());
            double scaledProgress = (double) craftingCore.progress / craftingCore.maxProgress;
            ((ServerLevel) blockEntity.getLevel()).sendParticles(ParticleTypes.DRAGON_BREATH, self_pos.x + (core_pos.x - self_pos.x) * scaledProgress + 0.5, self_pos.y + (core_pos.y - self_pos.y) * scaledProgress + 0.5, self_pos.z + (core_pos.z - self_pos.z) * scaledProgress + 0.5, 1, 0, 0, 0, 0);
        }
    }

    public void findForCore() {
        var world = getBlockEntity().getLevel();
        var pos = getBlockEntity().getBlockPos();
        for (int x = pos.getX() - maxCoreDistance; x <= pos.getX() + maxCoreDistance; x++) {
            for (int z = pos.getZ() - maxCoreDistance; z <= pos.getZ() + maxCoreDistance; z++) {
                for (int y = pos.getY() - maxCoreDistance; y <= pos.getY() + maxCoreDistance; y++) {
                    if (world.getBlockEntity(new BlockPos(x, y, z)) instanceof ComponentBlockEntity be) {
                        var core = be.getComponent(FusionCoreComponent.class);
                        if (core != null) {
                            if (!core.connectedInjectors.contains(this)) core.findForInjectors();
                        }
                    }
                }
            }
        }
    }

    public void saveAdditional(CompoundTag compound) {
        compound.putBoolean("isCrafting", isCrafting);
        System.out.println(isCrafting);
        if (isCrafting) {
            var corePos = craftingCore.getBlockEntity().getBlockPos();
            compound.putIntArray("core", new int[]{corePos.getX(), corePos.getY(), corePos.getZ()});
        }
    }

    public void load(CompoundTag compound) {
        isCrafting = compound.getBoolean("isCrafting");
        System.out.println(isCrafting);
        if (isCrafting) {
            var arr = compound.getIntArray("core");
            _corePos = new BlockPos(arr[0], arr[1], arr[2]);
        }
    }
}
