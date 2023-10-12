package tamermod.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public interface FaceAccess {
    boolean accessDirection(Direction dir, BlockState state);
    FaceAccess fromAll = new FaceAccess() {
        @Override
        public boolean accessDirection(Direction dir, BlockState state) {
            return true;
        }
    };
    FaceAccess locked = new FaceAccess() {
        @Override
        public boolean accessDirection(Direction dir, BlockState state) {
            return false;
        }
    };
    FaceAccess fromFacing = new FaceAccess() {
        @Override
        public boolean accessDirection(Direction dir, BlockState state) {
            return state.getValue(BaseEntityBlock.FACING) == dir;
        }
    };
}
