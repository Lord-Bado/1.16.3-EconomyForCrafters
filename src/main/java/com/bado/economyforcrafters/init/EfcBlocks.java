package com.bado.economyforcrafters.init;

import com.bado.economyforcrafters.objects.blocks.BankBlock;
import com.bado.economyforcrafters.objects.blocks.MarketizerBlock;
import com.bado.economyforcrafters.objects.blocks.MintBlock;
import com.bado.economyforcrafters.EconomyForCrafters;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid=EconomyForCrafters.MOD_ID,bus=Mod.EventBusSubscriber.Bus.MOD)
public class EfcBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EconomyForCrafters.MOD_ID);

    public static void register(final RegistryEvent.Register<Block> event){
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEventBus);
    }

    public static final RegistryObject<Block> BANK= BLOCKS.register("bank", BankBlock::new);
    public static final RegistryObject<Block> MINT= BLOCKS.register("mint", MintBlock::new);
    public static final RegistryObject<Block> MARKETIZER= BLOCKS.register("marketizer", MarketizerBlock::new);
    public static final RegistryObject<Block> MARKETIZER_CHEST= BLOCKS.register("marketizer_chest", MarketizerBlock::new);

}
