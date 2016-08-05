package com.sekwah.radiomod.client;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.blocks.renderers.TileEntityRadioRenderer;
import com.sekwah.radiomod.blocks.renderers.TileEntityTestRadioRenderer;
import com.sekwah.radiomod.blocks.tileentities.TileEntityBase;
import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;
import com.sekwah.radiomod.client.gui.GuiComputer;
import com.sekwah.radiomod.generic.CommonProxy;

import net.minecraft.util.ResourceLocation;
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
    }
    
    public void registerBlockRenderers(){

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBase.class, new TileEntityTestRadioRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRadio.class, new TileEntityRadioRenderer());
    }

}
