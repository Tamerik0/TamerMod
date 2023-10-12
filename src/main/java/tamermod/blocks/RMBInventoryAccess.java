package tamermod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import tamermod.blocks.blockentities.ComponentBlockEntity;
import tamermod.blocks.blockentities.components.InventoryComponent;

public class RMBInventoryAccess {
    public static InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        var inv = ((ComponentBlockEntity) level.getBlockEntity(pos)).getComponent(InventoryComponent.class);
        ItemStack stack = inv.getItem(0);
        inv.setItem(0, player.getInventory().getItem(player.getInventory().selected));
        player.getInventory().setItem(player.getInventory().selected, stack);
        return InteractionResult.SUCCESS;
    }
}
