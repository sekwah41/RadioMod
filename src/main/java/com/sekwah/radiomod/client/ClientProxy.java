package com.sekwah.radiomod.client;

import com.sekwah.radiomod.generic.CommonProxy;
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

    public void registerBlockRenderers(){
        //ClientRegistry.bindTileEntitySpecialRenderer();
    }

}
