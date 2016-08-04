package com.sekwah.radiomod;

import com.sekwah.radiomod.blocks.RadioBlocks;
import com.sekwah.radiomod.client.sound.RadioSounds;
import com.sekwah.radiomod.generic.CommonProxy;
import com.sekwah.radiomod.generic.guihandler.GuiHandlerRadio;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * Created by on 04/08/2016.
 *
 * @author sekwah41
 */
@Mod(modid = RadioMod.modid, name = "RadioMod", version = RadioMod.version)
public class RadioMod {
	@Instance
	public static RadioMod instance;
	
    public static final String modid = "radiomod";
    public static final String version = "0.0.1";
    public static final Logger logger = LogManager.getLogger("Radio Mod");

    @SidedProxy(clientSide = "com.sekwah.radiomod.client.ClientProxy", serverSide = "com.sekwah.radiomod.generic.CommonProxy")
    public static CommonProxy proxy;

    private File configFolder;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    	NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandlerRadio());

        proxy.registerBlockRenderers();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configFolder = event.getModConfigurationDirectory();

        proxy.preInit();

        RadioBlocks.registerBlocks();
        RadioSounds.registerSounds();
    }

}
