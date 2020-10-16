package com.bado.economyforcrafters.objects.tileentity;

import com.bado.economyforcrafters.init.EfcTileEntityTypes;
import com.bado.economyforcrafters.util.helpers.NBTHelper;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class MarketizerTE extends TileEntity implements ITickableTileEntity {
	public int x, y, z, tick;
	boolean initialized = false;

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
		//SOME CODE
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("initvalues", NBTHelper.toNBT(this));
		return super.write(compound);
	}
}
