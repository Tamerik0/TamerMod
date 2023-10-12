package tamermod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import tamermod.blocks.blockentities.BaseFusionCoreBlockEntity;
import tamermod.blocks.blockentities.ComponentBlockEntity;
import tamermod.blocks.blockentities.components.fusion_core.FusionCoreComponent;
import tamermod.blocks.blockentities.components.injectors.base.InjectorComponent;
import tamermod.init.TamerModBlockEntities;

public class BaseFusionCoreBlock extends BaseEntityBlock {
    public BaseFusionCoreBlock() {
        super(TamerModBlockEntities.BASE_FUSION_CORE_BLOCK_ENTITY_TYPE::get);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!((ComponentBlockEntity) level.getBlockEntity(pos)).getComponent(FusionCoreComponent.class).isCrafting)
            return RMBInventoryAccess.use(state, level, pos, player, hand, hit);
        return InteractionResult.FAIL;
    }

    @Override
    public void neighborChanged(BlockState blockstate, Level level, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
        super.neighborChanged(blockstate, level, pos, neighborBlock, fromPos, moving);
        getBlockEntity(level, pos).getComponent(FusionCoreComponent.class).redstoneSignalUpdated(level.getBestNeighborSignal(pos));
    }

    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        getBlockEntity(level, pos).getComponent(FusionCoreComponent.class).setBroken();
        super.onRemove(state, level, pos, newState, isMoving);
    }

}
