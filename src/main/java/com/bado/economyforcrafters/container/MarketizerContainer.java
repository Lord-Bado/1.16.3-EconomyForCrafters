package com.bado.economyforcrafters.container;

import java.util.Objects;

import com.bado.economyforcrafters.init.EfcBlocks;
import com.bado.economyforcrafters.init.EfcContainerTypes;
import com.bado.economyforcrafters.objects.tileentity.MarketizerTE;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

public class MarketizerContainer extends Container {

	public final MarketizerTE tileEntity;
	private final IWorldPosCallable canInteractWithCallable;

	public MarketizerContainer(final int windowId, final PlayerInventory playerInv, final MarketizerTE te) {
		super(EfcContainerTypes.MARKETIZER_CONTAINER.get(), windowId);
		this.tileEntity = te;
		this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

		// Trade Inventory
		int startX = 5;
		int startY = 18;
		int slotSizePlus2 = 18; // Pixelsize of slots + 2
		for (int row = 0; row < 1; ++row) {
			for (int col = 0; col < 9; ++col) {
				this.addSlot(new Slot(this.tileEntity, (row * 9) + col, startX + (col * slotSizePlus2),
						startY + (row * slotSizePlus2)));
			}
		}

		// Main player inventory
		startX = 108;
		startY = 84;
		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 9; ++col) {
				this.addSlot(new Slot(playerInv, 9 + (row * 9) + col, startX + (col * slotSizePlus2),
						startY + (row * slotSizePlus2)));
			}
		}

		// Player Hotbar
		int hotbarY = startY + (startY / 2) + 16; // 58 more that start Y (142)
		for (int col = 0; col < 9; ++col) {
			this.addSlot(new Slot(playerInv, col, startX + (col * slotSizePlus2), hotbarY));
		}
	}

	private static MarketizerTE getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
		Objects.requireNonNull(playerInv, "playerInventory cannot be null.");
		Objects.requireNonNull(data, "data cannot be null.");
		final TileEntity tileAtPos = playerInv.player.world.getTileEntity(data.readBlockPos());
		if (tileAtPos instanceof MarketizerTE) {
			return (MarketizerTE) tileAtPos;
		}
		throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
	}

	public MarketizerContainer(final int windowId, final PlayerInventory playerInv, final PacketBuffer data) {
		this(windowId, playerInv, getTileEntity(playerInv, data));
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(canInteractWithCallable, playerIn, EfcBlocks.MARKETIZER.get());
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemStack1 = slot.getStack();
			itemStack = itemStack1.copy();
			if (index < 36) {
				if (!this.mergeItemStack(itemStack1, 36, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemStack1, 0, 36, false)) {
				return ItemStack.EMPTY;
			}
			if (itemStack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}
		return itemStack;
	}
}
