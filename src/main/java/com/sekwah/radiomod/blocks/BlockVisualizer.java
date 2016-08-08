package com.sekwah.radiomod.blocks;

import com.sekwah.radiomod.blocks.tileentities.TileEntityVisualizer;
import com.sekwah.radiomod.items.CreativeTabRadio;

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
public class BlockVisualizer extends BlockContainer implements ITileEntityProvider {

    protected BlockVisualizer() {
        super(Material.IRON);
        this.setCreativeTab(CreativeTabRadio.creativeTabRadio);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityVisualizer();
    }
}
