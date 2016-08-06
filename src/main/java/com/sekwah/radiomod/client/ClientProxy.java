package com.sekwah.radiomod.client;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.blocks.RadioBlocks;
import com.sekwah.radiomod.blocks.renderers.TileEntityRadioRenderer;
import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;
import com.sekwah.radiomod.client.gui.GuiComputer;
import com.sekwah.radiomod.client.gui.GuiMobile;
import com.sekwah.radiomod.generic.CommonProxy;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * Created by on 04/08/2016.
 *
 * @author sekwah41
 */
public class ClientProxy extends CommonProxy {

    public boolean isClient(){
        return true;
    }
    
    public void preInit() {
    	GuiComputer.startupLogo = new ResourceLocation(RadioMod.modid, "textures/gui/startupLogo.png");
    	GuiComputer.computerBg = new ResourceLocation(RadioMod.modid, "textures/gui/computer.png");
    	GuiMobile.mobileBg = new ResourceLocation(RadioMod.modid, "textures/gui/mobile.png");
    }
    
    public void registerBlockRenderers(){
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRadio.class, new TileEntityRadioRenderer());

        ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(RadioBlocks.RADIOBLOCK), 0, TileEntityRadio.class);

        //ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RadioBlocks.RADIOBLOCK), 0, new ModelResourceLocation(RadioMod.modid + ":radio_block", "inventory"));
        //ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RadioBlocks.RADIOBLOCK), 0, new ModelResourceLocation(RadioMod.modid + ":test_radio", "inventory"));
    }

}
