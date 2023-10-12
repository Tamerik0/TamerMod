
package tamermod.client.gui;

import net.minecraft.world.inventory.DataSlot;
import tamermod.init.TamerModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MegaMachineGUIMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
	public final Level world;
	public final Player entity;
	public BlockPos pos;
	private IItemHandler internal;
	private final Map<Integer, Slot> customSlots = new HashMap<>();
	public MegaMachineGUIMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		super(TamerModMenus.MEGA_MACHINE_GUI.get(), id);
		this.entity = inv.player;
		this.world = inv.player.level;
		this.internal = new ItemStackHandler(9);
		pos = extraData.readBlockPos();
		BlockEntity ent = inv.player.level.getBlockEntity(pos);
		ent.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
			this.internal = capability;
		});
		this.customSlots.put(0, this.addSlot(new SlotItemHandler(internal, 0, 45, 9) {
		}));
		this.customSlots.put(1, this.addSlot(new SlotItemHandler(internal, 1, 45, 45) {
		}));
		this.customSlots.put(2, this.addSlot(new SlotItemHandler(internal, 2, 132, 27) {
		}));
		for (int si = 0; si < 3; ++si)
			for (int sj = 0; sj < 9; ++sj)
				this.addSlot(new Slot(inv, sj + (si + 1) * 9, 0 + 8 + sj * 18, 0 + 84 + si * 18));
		for (int si = 0; si < 9; ++si)
			this.addSlot(new Slot(inv, si, 0 + 8 + si * 18, 0 + 142));
	}
	@Override
	public boolean stillValid(Player player) {
		return true;
	}
	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (index < 3) {
				if (!this.moveItemStackTo(itemstack1, 3, this.slots.size(), true))
					return ItemStack.EMPTY;
				slot.onQuickCraft(itemstack1, itemstack);
			} else if (!this.moveItemStackTo(itemstack1, 0, 3, false)) {
				if (index < 3 + 27) {
					if (!this.moveItemStackTo(itemstack1, 3 + 27, this.slots.size(), true))
						return ItemStack.EMPTY;
				} else {
					if (!this.moveItemStackTo(itemstack1, 3, 3 + 27, false))
						return ItemStack.EMPTY;
				}
				return ItemStack.EMPTY;
			}
			if (itemstack1.getCount() == 0)
				slot.set(ItemStack.EMPTY);
			else
				slot.setChanged();
			if (itemstack1.getCount() == itemstack.getCount())
				return ItemStack.EMPTY;
			slot.onTake(playerIn, itemstack1);
		}
		return itemstack;
	}
//	@Override
//	protected boolean moveItemStackTo(ItemStack p_38904_, int p_38905_, int p_38906_, boolean p_38907_) {
//		boolean flag = false;
//		int i = p_38905_;
//		if (p_38907_) {
//			i = p_38906_ - 1;
//		}
//		if (p_38904_.isStackable()) {
//			while (!p_38904_.isEmpty()) {
//				if (p_38907_) {
//					if (i < p_38905_) {
//						break;
//					}
//				} else if (i >= p_38906_) {
//					break;
//				}
//				Slot slot = this.slots.get(i);
//				ItemStack itemstack = slot.getItem();
//				if (slot.mayPlace(itemstack) && !itemstack.isEmpty() && ItemStack.isSameItemSameTags(p_38904_, itemstack)) {
//					int j = itemstack.getCount() + p_38904_.getCount();
//					int maxSize = Math.min(slot.getMaxStackSize(), p_38904_.getMaxStackSize());
//					if (j <= maxSize) {
//						p_38904_.setCount(0);
//						itemstack.setCount(j);
//						slot.set(itemstack);
//						flag = true;
//					} else if (itemstack.getCount() < maxSize) {
//						p_38904_.shrink(maxSize - itemstack.getCount());
//						itemstack.setCount(maxSize);
//						slot.set(itemstack);
//						flag = true;
//					}
//				}
//				if (p_38907_) {
//					--i;
//				} else {
//					++i;
//				}
//			}
//		}
//		if (!p_38904_.isEmpty()) {
//			if (p_38907_) {
//				i = p_38906_ - 1;
//			} else {
//				i = p_38905_;
//			}
//			while (true) {
//				if (p_38907_) {
//					if (i < p_38905_) {
//						break;
//					}
//				} else if (i >= p_38906_) {
//					break;
//				}
//				Slot slot1 = this.slots.get(i);
//				ItemStack itemstack1 = slot1.getItem();
//				if (itemstack1.isEmpty() && slot1.mayPlace(p_38904_)) {
//					if (p_38904_.getCount() > slot1.getMaxStackSize()) {
//						slot1.set(p_38904_.split(slot1.getMaxStackSize()));
//					} else {
//						slot1.set(p_38904_.split(p_38904_.getCount()));
//					}
//					slot1.setChanged();
//					flag = true;
//					break;
//				}
//				if (p_38907_) {
//					--i;
//				} else {
//					++i;
//				}
//			}
//		}
//		return flag;
//	}

	@Override
	public void removed(Player playerIn) {
		super.removed(playerIn);
	}
	public Map<Integer, Slot> get() {
		return customSlots;
	}
}
