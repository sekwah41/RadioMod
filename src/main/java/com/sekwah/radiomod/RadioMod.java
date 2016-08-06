package com.sekwah.radiomod;

import com.sekwah.radiomod.blocks.RadioBlocks;
import com.sekwah.radiomod.client.sound.RadioSounds;
import com.sekwah.radiomod.generic.CommonProxy;
import com.sekwah.radiomod.generic.guihandler.GuiHandlerRadio;
import com.sekwah.radiomod.music.FileManager;
import com.sekwah.radiomod.music.MusicManager;
import com.sekwah.radiomod.network.packets.RadioMessage;
import com.sekwah.radiomod.network.packets.client.ClientPlaySongPacket;
import com.sekwah.radiomod.onlineservices.soundcloud.SoundCloud;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Random;

/**
 * Created by on 04/08/2016.
 *
 * @author sekwah41
 */
@Mod(modid = RadioMod.modid, name = "RadioMod", version = RadioMod.version, acceptedMinecraftVersions="[1.10.2]")
public class RadioMod {

	@Instance
	public static RadioMod instance;
	
    public static final String modid = "radiomod";
    public static final String version = "0.0.1";
    public static final Logger logger = LogManager.getLogger("Radio Mod");

    @SidedProxy(clientSide = "com.sekwah.radiomod.client.ClientProxy", serverSide = "com.sekwah.radiomod.generic.CommonProxy")
    public static CommonProxy proxy;

    private File configFolder;
    public File modFolder;

    public static SimpleNetworkWrapper packetNetwork;
    
    public MusicManager musicManager;

    public SoundCloud soundCloud;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    	NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandlerRadio());

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        this.packetNetwork();

        configFolder = event.getModConfigurationDirectory();
        modFolder = new File(Minecraft.getMinecraft().mcDataDir,"mods/"+RadioMod.modid);
        modFolder.mkdir();
        
        this.soundCloud = new SoundCloud("23c5983facf3240a2f14515f05f34873");

        proxy.preInit();

        RadioBlocks.registerBlocks();
        RadioSounds.registerSounds();
        FileManager.preInit();

        this.musicManager = new MusicManager();


        proxy.registerBlockRenderers();
    }

    private void packetNetwork() {
        /**
         * Internet Radio Mod
         */
        packetNetwork = NetworkRegistry.INSTANCE.newSimpleChannel("IRM");
        packetNetwork.registerMessage(ClientPlaySongPacket.class, RadioMessage.class, 0, Side.CLIENT);
        //packetNetwork.registerMessage(ClientPlaySongPacket.class, ClientPlaySongPacket.class, 0, Side.CLIENT);
        //packetNetwork.registerMessage(ClientPlaySongPacket.class, ClientPlaySongPacket.class, 1, Side.CLIENT);
    }

}
