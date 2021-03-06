package com.sekwah.radiomod.client;

import java.io.File;
import java.util.Map;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.RadioSettings;
import com.sekwah.radiomod.blocks.RadioBlocks;
import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;
import com.sekwah.radiomod.blocks.tileentities.TileEntitySpeaker;
import com.sekwah.radiomod.blocks.tileentities.TileEntityVisualizer;
import com.sekwah.radiomod.client.gui.GuiComputer;
import com.sekwah.radiomod.client.gui.GuiMobile;
import com.sekwah.radiomod.client.renderer.layers.LayerHeadphones;
import com.sekwah.radiomod.client.renderer.tileentity.TileEntityRadioRenderer;
import com.sekwah.radiomod.client.renderer.tileentity.TileEntitySpeakerRenderer;
import com.sekwah.radiomod.generic.CommonProxy;
import com.sekwah.radiomod.items.RadioItems;
import com.sekwah.radiomod.music.MobileManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by on 04/08/2016.
 *
 * @author sekwah41
 */
public class ClientProxy extends CommonProxy {

    public RadioSettings settings;

    public boolean isClient() {
        return true;
    }

    @Override
    public void addEvents() {
        super.addEvents();
        MinecraftForge.EVENT_BUS.register(new EventHook());
    }

    @Override
    public void preInit() {
        GuiComputer.startupLogo = new ResourceLocation(RadioMod.modid, "textures/gui/startupLogo.png");
        GuiComputer.computerBg = new ResourceLocation(RadioMod.modid, "textures/gui/computer.png");
        GuiMobile.mobileBg = new ResourceLocation(RadioMod.modid, "textures/gui/mobile.png");
    }
    
    @Override
    public void postInit() {
        Map<String, RenderPlayer> playerSkins = Minecraft.getMinecraft().getRenderManager().getSkinMap();
        String[] keys = playerSkins.keySet().toArray(new String[0]);
        for (int i = 0; i < keys.length; i++) {
            playerSkins.get(keys[i]).addLayer(new LayerHeadphones(playerSkins.get(keys[i]).getMainModel().bipedHead));
        }

        ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(RadioBlocks.RADIOBLOCK), 0, TileEntityRadio.class);
        ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(RadioBlocks.SPEAKERBLOCK), 0, TileEntitySpeaker.class);
        ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(RadioBlocks.VISUALIZERBLOCK), 0, TileEntityVisualizer.class);
        ItemModelMesher itemModelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
        itemModelMesher.register(RadioItems.MOBILE, 0, new ModelResourceLocation("radiomod:mobile_player", "inventory"));
        itemModelMesher.register(RadioItems.JACK, 0, new ModelResourceLocation("radiomod:wireless_jack", "inventory"));
    }

    @Override
    public void registerBlockRenderers(){
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRadio.class, new TileEntityRadioRenderer());
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RadioBlocks.RADIOBLOCK), 0, new ModelResourceLocation(RadioMod.modid + ":radio_block", "inventory"));
        
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySpeaker.class, new TileEntitySpeakerRenderer());
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RadioBlocks.SPEAKERBLOCK), 0, new ModelResourceLocation(RadioMod.modid + ":speaker_block", "inventory"));
        
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySpeaker.class, new TileEntitySpeakerRenderer());
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RadioBlocks.VISUALIZERBLOCK), 0, new ModelResourceLocation(RadioMod.modid + ":visualizer_block", "inventory"));
    }

    @Override
    public void setupMusic() {
        RadioMod radioMod = RadioMod.instance;
        radioMod.modFolder = new File(Minecraft.getMinecraft().mcDataDir, "mods/" + RadioMod.modid);
        radioMod.modFolder.mkdir();
    }

    @Override
    public void loadSettings(FMLPreInitializationEvent event) {
        super.loadSettings(event);
        this.settings = new RadioSettings();
        this.settings.setupConfigVariables(event);
    }
    
    @Override
    public void setPlayerLocalRunstate(int state) {
    	EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		player.getDataManager().set(MobileManager.PARAMETER_LOCALSTATE, state);
    }
    
    @Override
    public int getPlayerLocalRunstate() {
    	EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		return player.getDataManager().get(MobileManager.PARAMETER_LOCALSTATE);
    }
}
