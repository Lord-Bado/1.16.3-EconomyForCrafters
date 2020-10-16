package com.bado.economyforcrafters.init;

import com.bado.economyforcrafters.EconomyForCrafters;
import com.bado.economyforcrafters.objects.tileentity.MarketizerTE;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EfcTileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES= DeferredRegister
            .create(ForgeRegistries.TILE_ENTITIES, EconomyForCrafters.MOD_ID);

    public static final RegistryObject<TileEntityType<MarketizerTE>> MARKETIZER = TILE_ENTITY_TYPES
            .register("marketizer",()-> TileEntityType.Builder.create(MarketizerTE::new, EfcBlocks.MARKETIZER.get()).build(null));
}
