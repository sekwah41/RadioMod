package com.sekwah.radiomod.blocks;

import javax.annotation.Nullable;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.blocks.tileentities.TileEntityBase;
import com.sekwah.radiomod.generic.guihandler.GuiHandlerRadio;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
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
        BOUNDING_BOX = new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.7D, 0.7D);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World par1World, int meta) {
        RadioMod.logger.info("Test");
        return new TileEntityBase();
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

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    /**
     * The type of render function called. 3 for standard block models, 2 for TESR's, 1 for liquids, -1 is no render
     */
    @Deprecated
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
