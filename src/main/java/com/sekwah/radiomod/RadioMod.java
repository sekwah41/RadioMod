package com.sekwah.radiomod;

import java.io.File;

import com.sekwah.radiomod.items.RadioItems;
import com.sekwah.radiomod.music.MusicTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sekwah.radiomod.blocks.RadioBlocks;
import com.sekwah.radiomod.client.sound.RadioSounds;
import com.sekwah.radiomod.generic.CommonProxy;
import com.sekwah.radiomod.generic.guihandler.GuiHandlerRadio;
import com.sekwah.radiomod.music.FileManager;
import com.sekwah.radiomod.music.MusicManager;
import com.sekwah.radiomod.music.song.SongBuiltIn;
import com.sekwah.radiomod.network.packets.client.*;
import com.sekwah.radiomod.network.packets.server.*;
import com.sekwah.radiomod.onlineservices.soundcloud.SoundCloud;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

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

    public MusicTracker musicTracker;

    public SoundCloud soundCloud;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        proxy.addEvents();

    	NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandlerRadio());

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	proxy.postInit();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        this.packetNetwork();

        configFolder = event.getModConfigurationDirectory();

        proxy.loadSettings(event);

        proxy.setupMusic();
        
        //this.soundCloud = new SoundCloud("23c5983facf3240a2f14515f05f34873");

        proxy.preInit();

        RadioBlocks.registerBlocks();
        RadioItems.registerItems();
        RadioSounds.registerSounds();

        SongBuiltIn.registerBuiltInSongs();

        FileManager.preInit();

        this.musicManager = new MusicManager();

        this.musicTracker = new MusicTracker();

        proxy.registerBlockRenderers();
    }

    private void packetNetwork() {
        /**
         * Internet Radio Mod
         */
        packetNetwork = NetworkRegistry.INSTANCE.newSimpleChannel("IRM");
        packetNetwork.registerMessage(ClientPlaySongBroadcastPacket.Handler.class, ClientPlaySongBroadcastPacket.class, 0, Side.CLIENT);
        packetNetwork.registerMessage(ClientStopSongBroadcastPacket.Handler.class, ClientStopSongBroadcastPacket.class, 1, Side.CLIENT);
        packetNetwork.registerMessage(ClientUpdateComputerPacket.Handler.class, ClientUpdateComputerPacket.class, 2, Side.CLIENT);
        packetNetwork.registerMessage(ClientConfigPacket.Handler.class, ClientConfigPacket.class, 3, Side.CLIENT);
        
        //Server packets starting from 100 and up (just a cosmetic thing for me ;))
        packetNetwork.registerMessage(ServerBootupComputerPacket.Handler.class, ServerBootupComputerPacket.class, 100, Side.SERVER);
        packetNetwork.registerMessage(ServerPlaySongPacket.Handler.class, ServerPlaySongPacket.class, 101, Side.SERVER);
        packetNetwork.registerMessage(ServerStopSongPacket.Handler.class, ServerStopSongPacket.class, 102, Side.SERVER);
    }

}
