package com.sekwah.radiomod.client;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.blocks.RadioBlock;
import com.sekwah.radiomod.blocks.RadioBlocks;
import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;
import com.sekwah.radiomod.client.gui.GuiComputer;
import com.sekwah.radiomod.client.gui.GuiMobile;
import com.sekwah.radiomod.client.renderer.tileentity.TileEntityRadioRenderer;
import com.sekwah.radiomod.generic.CommonProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.io.File;

/**
 * Created by on 04/08/2016.
 *
 * @author sekwah41
 */
public class ClientProxy extends CommonProxy {

    public boolean isClient(){
        return true;
    }

    @Override
    public void addEvents(){
        super.addEvents();
        MinecraftForge.EVENT_BUS.register(new EventHook());
    };

    @Override
    public void preInit() {
    	GuiComputer.startupLogo = new ResourceLocation(RadioMod.modid, "textures/gui/startupLogo.png");
    	GuiComputer.computerBg = new ResourceLocation(RadioMod.modid, "textures/gui/computer.png");
    	GuiMobile.mobileBg = new ResourceLocation(RadioMod.modid, "textures/gui/mobile.png");
    }

    @Override
    public void registerBlockRenderers(){
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRadio.class, new TileEntityRadioRenderer());

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RadioBlocks.RADIOBLOCK), 0, new ModelResourceLocation(RadioMod.modid + ":radio_block", "inventory"));
        //ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RadioBlocks.RADIOBLOCK), 0, new ModelResourceLocation(RadioMod.modid + ":test_radio", "inventory"));
    }

    @Override
    public void setupMusic(){
        RadioMod radioMod = RadioMod.instance;
        radioMod.modFolder = new File(Minecraft.getMinecraft().mcDataDir,"mods/"+RadioMod.modid);
        radioMod.modFolder.mkdir();
    }

}
