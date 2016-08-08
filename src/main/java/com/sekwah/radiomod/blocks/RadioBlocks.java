package com.sekwah.radiomod.blocks;

import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;
import com.sekwah.radiomod.blocks.tileentities.TileEntitySpeaker;
import com.sekwah.radiomod.blocks.tileentities.TileEntityVisualizer;

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
    public static BlockRadio RADIOBLOCK;
    public static BlockSpeaker SPEAKERBLOCK;
    public static BlockVisualizer VISUALIZERBLOCK;

    public static void registerBlocks() {
        RADIOBLOCK = new BlockRadio();
        registerTileBlock(RADIOBLOCK, "radio_block", TileEntityRadio.class);
        
        SPEAKERBLOCK = new BlockSpeaker();
        registerTileBlock(SPEAKERBLOCK, "speaker_block", TileEntitySpeaker.class);
        
        VISUALIZERBLOCK = new BlockVisualizer();
        registerTileBlock(VISUALIZERBLOCK, "visualizer_block", TileEntityVisualizer.class);
    }

    public static void registerBlock(Block block, String name){
        block.setRegistryName(name);
        block.setUnlocalizedName(name);
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
