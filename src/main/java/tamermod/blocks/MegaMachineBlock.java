package tamermod.blocks;

import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import tamermod.client.gui.MegaMachineGUIMenu;

import java.util.function.Supplier;

public class MegaMachineBlock extends BaseEntityBlock{
    public MegaMachineBlock(Supplier<BlockEntityType> blockEntityTypeSupplier) {
        super(blockEntityTypeSupplier);
    }

    @Override
    public InteractionResult use(BlockState blockstate, Level world, BlockPos pos, Player entity, InteractionHand hand, BlockHitResult hit) {
        super.use(blockstate, world, pos, entity, hand, hit);
        if (entity instanceof ServerPlayer player) {
            NetworkHooks.openScreen(player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("Mega Machine");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                    return new MegaMachineGUIMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
                }
            }, pos);
        }
        return InteractionResult.SUCCESS;
    }
    @Override
    public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
        return new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.literal("Mega Machine");
            }

            @Override
            public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                return new MegaMachineGUIMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(pos));
            }
        };
    }
}
