package tamermod.blocks.blockentities.components.injectors.base;

import ic2.api.energy.tile.IEnergyEmitter;
import net.minecraft.core.Direction;
import tamermod.blocks.BaseEntityBlock;
import tamermod.blocks.blockentities.components.EUBlockEntityComponent;

public class BaseInjectorEUComponent extends EUBlockEntityComponent {
    public BaseInjectorEUComponent(int maxEnergy) {
        super(maxEnergy);
    }
    @Override
    public boolean canAcceptEnergy(IEnergyEmitter iEnergyEmitter, Direction direction) {
        return blockEntity.getBlockState().getValue(BaseEntityBlock.FACING) == direction;
    }
}
