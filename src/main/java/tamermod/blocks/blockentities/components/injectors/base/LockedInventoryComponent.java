package tamermod.blocks.blockentities.components.injectors.base;

import net.minecraft.core.Direction;
import tamermod.blocks.BaseEntityBlock;
import tamermod.blocks.blockentities.components.InventoryComponent;

public class LockedInventoryComponent extends InventoryComponent {
    public LockedInventoryComponent(int size) {
        super(size);
    }

    @Override
    public boolean accessDirection(Direction dir) {
        return false;
    }
}
