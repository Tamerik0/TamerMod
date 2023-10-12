package tamermod.blocks.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import tamermod.blocks.blockentities.components.fusion_core.FusionCoreComponent;
import tamermod.blocks.blockentities.components.injectors.base.LockedInventoryComponent;

public class BaseFusionCoreBlockEntity extends ComponentBlockEntity {
    public BaseFusionCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state, new FusionCoreComponent(), new LockedInventoryComponent(1));
    }
}
