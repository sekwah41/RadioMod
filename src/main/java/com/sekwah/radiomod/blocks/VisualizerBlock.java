package com.sekwah.radiomod.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by on 07/08/2016.
 *
 * @author sekwah41
 */
public class VisualizerBlock extends BlockContainer implements ITileEntityProvider {

    protected VisualizerBlock(Material materialIn) {
        super(materialIn);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }
}
