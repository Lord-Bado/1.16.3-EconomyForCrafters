package com.bado.economyforcrafters;

import com.bado.economyforcrafters.init.EfcBlocks;
import com.bado.economyforcrafters.init.EfcItems;
import com.bado.economyforcrafters.util.Registration;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("efc")
public class EconomyForCrafters

{

    public  static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "efc";

    public EconomyForCrafters() {

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EfcBlocks.BLOCKS.register(modEventBus);
        EfcItems.ITEMS.register(modEventBus);


        //Registration.register();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

    public static final ItemGroup TAB = new ItemGroup("tab"){
        @Override
        public ItemStack createIcon() {
            return new ItemStack(EfcItems.COIN.get());
        }
    };

}
