package tamermod.blocks.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import tamermod.blocks.blockentities.components.EUBlockEntityComponent;
import tamermod.blocks.blockentities.components.injectors.base.InjectorComponent;
import tamermod.blocks.blockentities.components.injectors.base.LockedInventoryComponent;
import tamermod.blocks.blockentities.components.InventoryComponent;

public class BaseInjectorBlockEntity extends ComponentBlockEntity {
    public BaseInjectorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state, new InjectorComponent(), new LockedInventoryComponent(1), new EUBlockEntityComponent(10000));
    }
    public ItemStack getRenderedItemStack(){
        return getComponent(InventoryComponent.class).getItem(0);
    }
}
