package com.bado.economyforcrafters.init;

import com.bado.economyforcrafters.EconomyForCrafters;
import com.bado.economyforcrafters.objects.blocks.MarketizerBlock;
import com.bado.economyforcrafters.objects.tileentity.MarketizerTE;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EfcContainerTypes {
	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, EconomyForCrafters.MOD_ID);
	
	public static final RegistryObject<ContainerType<MarketizerTE>> MARKETIZER_CONTAINER = CONTAINER_TYPES
			.register("marketizer", ()->IForgeContainerType
					.create(MarketizerTE::new));
}
