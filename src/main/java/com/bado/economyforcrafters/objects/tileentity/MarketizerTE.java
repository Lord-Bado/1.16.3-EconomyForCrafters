package com.bado.economyforcrafters.objects.tileentity;

import javax.annotation.Nonnull;

import com.bado.economyforcrafters.container.MarketizerContainer;
import com.bado.economyforcrafters.init.EfcTileEntityTypes;
import com.bado.economyforcrafters.objects.blocks.MarketizerBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

public class MarketizerTE extends LockableLootTileEntity implements ITickableTileEntity {
	public int x, y, z, tick;
	boolean initialized = false;
	private NonNullList<ItemStack> contents = NonNullList.withSize(10, ItemStack.EMPTY);
	protected int numPlayersUsing;
	private IItemHandlerModifiable items = createHandler();
	private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);

	public MarketizerTE(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public MarketizerTE() {
		this(EfcTileEntityTypes.MARKETIZER.get());
	}

	@Override
	public void tick() {
		if (!initialized)
			init();
		tick++;
		if (tick >= 40) {
			tick = 0;
			execute();
		}
	}

	private void init() {
		initialized = true;
		tick = 0;
	}

	private void execute() {
		// SOME CODE
	}

	@Override
	public int getSizeInventory() {

		return this.contents.size();
	}

	@Override
	public NonNullList<ItemStack> getItems() {

		return this.contents;
	}

	@Override
	public void setItems(NonNullList<ItemStack> itemsIn) {
		this.contents = itemsIn;
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.marketizer");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new MarketizerContainer(id, player, this);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		if (!this.checkLootAndWrite(compound)) {
			ItemStackHelper.loadAllItems(compound, this.contents);
		}
		return compound;
	}

	// Gross unmapped read function
	@Override
	public void func_230337_a_(BlockState state, CompoundNBT compound) {
		super.func_230337_a_(state, compound);
		this.contents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		if (!this.checkLootAndRead(compound)) {
			ItemStackHelper.loadAllItems(compound, this.contents);
		}
	}

	private void playSound(SoundEvent sound) {
		double dx = (double) this.pos.getX() + 0.5D;
		double dy = (double) this.pos.getY() + 0.5D;
		double dz = (double) this.pos.getZ() + 0.5D;
		this.world.playSound((PlayerEntity) null, dx, dy, dz, sound, SoundCategory.BLOCKS, 0.5f,
				this.world.rand.nextFloat() * 0.1f + 0.9f);
	}

	@Override
	public boolean receiveClientEvent(int id, int type) {
		if (id == 1) {
			this.numPlayersUsing = type;
			return true;
		} else {
			return super.receiveClientEvent(id, type);
		}
	}

	@Override
	public void openInventory(PlayerEntity player) {
		if (!player.isSpectator()) {
			if (numPlayersUsing < 0) {
				this.numPlayersUsing = 0;
			}
			++this.numPlayersUsing;
			this.onOpenOrClose();
		}
		super.openInventory(player);
	}

	@Override
	public void closeInventory(PlayerEntity player) {
		if (!player.isSpectator()) {
			--numPlayersUsing;
			this.onOpenOrClose();
		}
		super.closeInventory(player);
	}

	private void onOpenOrClose() {
		Block block = this.getBlockState().getBlock();
		if (block instanceof MarketizerBlock) {
			this.world.addBlockEvent(this.pos, block, 1, this.numPlayersUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, block);
		}
	}

	public static int getPlayersUsing(IBlockReader reader, BlockPos pos) {
		BlockState state = reader.getBlockState(pos);
		if (state.hasTileEntity()) {
			TileEntity te = reader.getTileEntity(pos);
			if (te instanceof MarketizerTE) {
				return ((MarketizerTE) te).numPlayersUsing;
			}
		}
		return 0;
	}

	public static void swapContents(MarketizerTE te, MarketizerTE otherTe) {
		NonNullList<ItemStack> list = te.getItems();
		te.setItems(otherTe.getItems());
		otherTe.setItems(list);
	}

	@Override
	public void updateContainingBlockInfo() {
		super.updateContainingBlockInfo();
		if (this.itemHandler != null) {
			this.itemHandler.invalidate();
			this.itemHandler = null;
		}
	}

	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nonnull Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return itemHandler.cast();
		}
		return super.getCapability(cap, side);
	}
	
	private IItemHandlerModifiable createHandler() {
		return new InvWrapper(this);
	}
	
	@Override
	public void remove() {
		super.remove();
		if(itemHandler != null) {
			itemHandler.invalidate();
		}
	}
}
