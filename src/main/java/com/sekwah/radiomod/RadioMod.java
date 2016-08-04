package com.sekwah.radiomod;

import com.sekwah.radiomod.generic.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
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

    public static final String modid = "radiomod";

    public static final Logger logger = LogManager.getLogger("Radio Mod");

    public static final String version = "0.0.1";

    @SidedProxy(clientSide = "com.sekwah.radiomod.client.ClientProxy", serverSide = "com.sekwah.radiomod.generic.CommonProxy")
    public static CommonProxy proxy;

    private File configFolder;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configFolder = event.getModConfigurationDirectory();
        

    }

}
