package tamermod.blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import tamermod.blocks.blockentities.ComponentBlockEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


public class BaseEntityBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    public BaseEntityBlock(Supplier<BlockEntityType> blockEntityTypeSupplier){
        super(BlockBehaviour.Properties.of(Material.AMETHYST));
        this.blockEntityTypeSupplier = blockEntityTypeSupplier;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));

    }
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return InteractionResult.PASS;
    }
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return (Level lvl, BlockPos pos, BlockState st, T entity)->{
            if(entity instanceof ComponentBlockEntity _entity)
                _entity.tick(lvl,pos,st);
        };
    }
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> drops = new ArrayList();
        BlockEntity tile = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        ItemStack stack = new ItemStack(this);
        drops.add(stack);
        return drops;
    }
    Supplier<BlockEntityType> blockEntityTypeSupplier;
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return blockEntityTypeSupplier.get().create(pos,state);
    }
    public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param) {
        super.triggerEvent(state, level, pos, id, param);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity == null ? false : blockEntity.triggerEvent(id, param);
    }
    public static ComponentBlockEntity getBlockEntity(Level level, BlockPos pos){
        return (ComponentBlockEntity)level.getBlockEntity(pos);
    }
}
