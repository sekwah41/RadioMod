package com.sekwah.radiomod.blocks;

import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by on 04/08/2016.
 *
 * @author sekwah41
 */
public class RadioBlocks {


    // Radio block
    public static RadioBlock RADIOBLOCK;

    public static void registerBlocks() {

        RADIOBLOCK = new RadioBlock();
        registerTileBlock(RADIOBLOCK, "radio_block", TileEntityRadio.class);

    }

    public static void registerBlock(Block block, String name){
        block.setRegistryName(name);
        GameRegistry.register(block);
        GameRegistry.register(new ItemBlock(block), block.getRegistryName());
    }

    public static void registerTileBlock(Block block, String name, Class<? extends TileEntity> tileEntityClass){
        registerBlock(block, name);
        GameRegistry.registerTileEntity(tileEntityClass, name);
    }


    // Actually wont be needed as models are java files.
    /*public void registerBlockModel(Block block){
        ModelLoader.setCustomMeshDefinition();
    }*/
}
