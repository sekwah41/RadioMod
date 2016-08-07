package com.sekwah.radiomod.generic;

import com.sekwah.radiomod.RadioSettings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by on 04/08/2016.
 *
 * @author sekwah41
 */
public class CommonProxy {

    public RadioSettings settings;
	
	public void preInit() {
		
	}
	
	public void postInit() {
		
	}
	
    public boolean isClient(){
        return false;
    }

    public void addEvents(){
        MinecraftForge.EVENT_BUS.register(new EventServerHook());
    };

    public void registerBlockRenderers(){

    }

    public void setupMusic() {

    }

    public void loadSettings(FMLPreInitializationEvent event) {
        this.settings = new RadioSettings();
        this.settings.setupConfigVariables(event);
    }
}
