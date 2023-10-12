package tamermod.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class KekBlock extends FenceBlock {
    public KekBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE).dynamicShape());
    }
}
