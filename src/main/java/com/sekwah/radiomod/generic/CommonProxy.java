package com.sekwah.radiomod.generic;

import net.minecraftforge.common.MinecraftForge;

/**
 * Created by on 04/08/2016.
 *
 * @author sekwah41
 */
public class CommonProxy {
	
	public void preInit() {
		
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
}
