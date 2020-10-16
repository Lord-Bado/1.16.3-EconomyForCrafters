package com.bado.economyforcrafters.objects.tileentity;

import javax.annotation.Nullable;

import com.bado.economyforcrafters.init.EfcTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class MarketizerTE extends TileEntity implements ITickableTileEntity {
    public int tick;
    boolean initialized=false;
    public MarketizerTE(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public MarketizerTE(){
        this(EfcTileEntityTypes.MARKETIZER.get());
    }

    @Override
    public void tick() {
        if(!initialized) init();
        tick++;
        if(tick>=40){
            tick=0;
            execute();
        }
    }

    private void init() {
        initialized=true;
        tick=0;
    }

    private void execute() {
        int index=0;
        Block[] blocksRemoved=new Block[9];
    }

    /*
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("initvalues",NBTHelper.toNBT(this));
        return super.write(compound);
    }
    
    
    @Nullable
    @Override
    public void read(CompoundNBT compound){
        super.read(compound);
    }*/
}
