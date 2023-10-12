package tamermod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.*;
import tamermod.blocks.blockentities.ComponentBlockEntity;
import tamermod.blocks.blockentities.components.InventoryComponent;
import tamermod.blocks.blockentities.components.injectors.base.InjectorComponent;
import tamermod.init.TamerModBlockEntities;

public class BaseInjectorBlock extends BaseEntityBlock {
    public BaseInjectorBlock() {
        super(TamerModBlockEntities.BASE_INJECTOR_BLOCK_ENTITY_TYPE::get);
        for (int i = 0; i < 6; i++) {
            shape_cache[2] = rotateShapeXZ(2, shape);
            shape_cache[4] = rotateShapeXZ(1, shape);
            shape_cache[3] = rotateShapeXZ(0, shape);
            shape_cache[5] = rotateShapeXZ(3, shape);
        }
    }

    VoxelShape shape = makeShape();
    VoxelShape[] shape_cache = new VoxelShape[6];

    public static VoxelShape rotateShapeXZ(int times, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{shape, Shapes.empty()};

        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1], Shapes.create(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }

    public static VoxelShape rotateShapeXY(int times, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{shape, Shapes.empty()};

        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1], Shapes.create(1 - maxY, minX, minZ, 1 - minY, maxX, maxZ)));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }

    public static VoxelShape rotateShapeYZ(int times, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{shape, Shapes.empty()};

        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1], Shapes.create(minX, 1 - maxZ, minY, maxX, 1 - minZ, maxY)));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }

    public VoxelShape makeShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.08080582617584076, 0.08080582617584064, 0, 0.16919417381959068, 0.14330582617584076, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.125, 0.375, 0.9375, 0.875, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.8125, 0.75, 0.875, 0.875, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.08080582617584064, 0.8308058261804093, 0, 0.14330582617584076, 0.9191941738241592, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8308058261804093, 0.8566941738241592, 0, 0.9191941738241592, 0.9191941738241594, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8566941738241592, 0.08080582617584076, 0, 0.9191941738241594, 0.16919417381959068, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.1875, 0.75, 0.1875, 0.8125, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.125, 0.75, 0.875, 0.1875, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.1875, 0.75, 0.875, 0.8125, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.0625, 0.375, 0.875, 0.9375, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.875, 0.75, 0.875, 0.9375, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.0625, 0, 0.9375, 0.9375, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.3125, 0.4375, 1, 0.6875, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.1875, 0, 1, 0.8125, 0.4375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0, 0.8125, 1, 0.4375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0, 0.4375, 0.6875, 1, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.125, 0.75, 0.9375, 0.875, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.1875, 0, 1, 0.8125, 0), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.8125, 0, 0.9375, 0.9375, 0), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.9375, 0, 0.8125, 1, 0), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.0625, 0, 0.9375, 0.1875, 0), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0, 0.8125, 0.0625, 0), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.0625, 0.75, 0.875, 0.125, 0.75), BooleanOp.OR);
        return shape;
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        var dir = state.getValue(BaseEntityBlock.FACING);
        return shape_cache[dir.ordinal()];
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if(!((ComponentBlockEntity)level.getBlockEntity(pos)).getComponent(InjectorComponent.class).isCrafting
                &&
                FaceAccess.fromFacing.accessDirection(hit.getDirection(),state))
            return RMBInventoryAccess.use(state,level, pos, player, hand, hit);
        return InteractionResult.FAIL;
    }
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        getBlockEntity(level, pos).getComponent(InjectorComponent.class).setBroken();
        super.onRemove(state, level, pos, newState, isMoving);
    }
}
