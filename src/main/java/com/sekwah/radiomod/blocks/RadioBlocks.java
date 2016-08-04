package com.sekwah.radiomod.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by on 04/08/2016.
 *
 * @author sekwah41
 */
public class RadioBlocks {


    // Radio blocks
    private static TestRadio testRadio;

    public static void registerBlocks() {

        testRadio = new TestRadio();
        registerBlock(testRadio, "test_radio");

    }

    public static void registerBlock(Block block, String name){
        block.setRegistryName(name);
        GameRegistry.register(block);
        GameRegistry.register(new ItemBlock(block), block.getRegistryName());
    }


    // Actually wont be needed as models are java files.
    /*public void registerBlockModel(Block block){
        ModelLoader.setCustomMeshDefinition();
    }*/
}
