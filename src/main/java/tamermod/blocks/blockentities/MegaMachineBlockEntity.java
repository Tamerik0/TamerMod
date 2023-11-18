package tamermod.blocks.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import tamermod.blocks.blockentities.components.*;

public class MegaMachineBlockEntity extends ComponentBlockEntity {
    public MegaMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state,
                new EUBlockEntityComponent(1000000),
                new RFBlockEntityComponent(),
                new InventoryComponent(3),
                new MegaMachineCraftingComponent(),
                new FluidStorageBlockEntityComponent(5000));
    }
}
