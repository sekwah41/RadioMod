package com.sekwah.radiomod.blocks;

import javax.annotation.Nullable;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.generic.guihandler.GuiHandlerRadio;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by on 04/08/2016.
 *
 * @author sekwah41
 */
public class TestRadio extends Block implements ITileEntityProvider {

    private final AxisAlignedBB BOUNDING_BOX;
    
    public static final int RUNSTATE_OFF = -1;
    public static final int RUNSTATE_BOOTINGUP = 0;
    public static final int RUNSTATE_ON = 1;
    
    public TestRadio() {
        super(Material.GLASS);
        BOUNDING_BOX = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }
    
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	playerIn.openGui(RadioMod.instance, GuiHandlerRadio.GUIID_COMPUTER, worldIn, (int)hitX, (int)hitY, (int)hitZ);
    	
        return true;
    }
}
