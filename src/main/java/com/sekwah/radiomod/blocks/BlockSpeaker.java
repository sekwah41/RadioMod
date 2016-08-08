package com.sekwah.radiomod.blocks;

import com.sekwah.radiomod.blocks.tileentities.TileEntitySpeaker;
import com.sekwah.radiomod.items.CreativeTabRadio;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * Created by on 07/08/2016.
 *
 * @author sekwah41
 */
public class BlockSpeaker extends BlockContainer implements ITileEntityProvider {
	public static final PropertyDirection FACING = BlockDirectional.FACING;

    private final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.7D, 0.9D);
	
    protected BlockSpeaker() {
        super(Material.IRON);
        this.setCreativeTab(CreativeTabRadio.creativeTabRadio);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntitySpeaker();
    }
}
