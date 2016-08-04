package com.sekwah.radiomod.blocks.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by on 04/08/2016.
 *
 * @author sekwah41
 */
public class TileEntityBase extends TileEntity {
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1) {
        super.writeToNBT(par1);
        return par1;
    }

    @Override
    public void readFromNBT(NBTTagCompound par1) {
        super.readFromNBT(par1);
    }

}