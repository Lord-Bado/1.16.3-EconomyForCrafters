package com.bado.economyforcrafters.init;

import com.bado.economyforcrafters.objects.items.BlockItemBase;
import com.bado.economyforcrafters.objects.items.ItemBase;
import com.bado.economyforcrafters.EconomyForCrafters;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid=EconomyForCrafters.MOD_ID,bus=Mod.EventBusSubscriber.Bus.MOD)
public class EfcItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EconomyForCrafters.MOD_ID);

    public static void register(){
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
    }
    //Items
    public static final RegistryObject<Item> COIN = ITEMS.register("coin", ItemBase::new);
    public static final RegistryObject<Item> GOLD_PLATE = ITEMS.register("gold_plate", ItemBase::new);
    //Block-Items
    public static final RegistryObject<Item> BANK_ITEM= ITEMS.register("bank", ()-> new BlockItemBase(EfcBlocks.BANK.get()));
    public static final RegistryObject<Item> MINT_ITEM= ITEMS.register("mint", ()-> new BlockItemBase(EfcBlocks.MINT.get()));
    public static final RegistryObject<Item> MARKETIZER_ITEM= ITEMS.register("marketizer", ()-> new BlockItemBase(EfcBlocks.MARKETIZER.get()));
}
