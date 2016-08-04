package com.sekwah.radiomod.client;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.client.gui.GuiComputer;
import com.sekwah.radiomod.generic.CommonProxy;

import net.minecraft.util.ResourceLocation;

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
        //ClientRegistry.bindTileEntitySpecialRenderer();
    }

}
