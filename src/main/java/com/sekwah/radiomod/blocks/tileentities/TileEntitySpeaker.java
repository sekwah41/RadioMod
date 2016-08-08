package com.sekwah.radiomod.blocks.tileentities;

import java.util.UUID;

import com.sekwah.radiomod.RadioMod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntitySpeaker extends TileEntityAddon {
	private int rotation = 0;
	
	@Override
    public void update() {
		if(RadioMod.proxy.isClient()){
            this.updateDistance();
        }
    }
	
	@SideOnly(Side.CLIENT)
    private void updateDistance() {
        EntityPlayer playerSP = FMLClientHandler.instance().getClient().thePlayer;
        if(playerSP != null){
            float distance = (float) this.getDistanceSq(playerSP.posX, playerSP.posY, playerSP.posZ);
            if(RadioMod.instance.musicManager.sourceDistances.containsKey(this.getOwnerUUID())){
            	try {
	                float curDistance = RadioMod.instance.musicManager.sourceDistances.get(this.getOwnerUUID());
	                if(curDistance > distance){
	                    RadioMod.instance.musicManager.sourceDistances.put(this.getOwnerUUID(), distance);
	                }
            	}catch(NullPointerException e) {
            		RadioMod.logger.info("Error with uuid");
            	}
            }
            else{
                RadioMod.instance.musicManager.sourceDistances.put(this.getOwnerUUID(),distance);
            }
        }
    }
	
	public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.rotation = compound.getByte("Rot");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setByte("Rot", (byte)(this.rotation & 255));
        return compound;
    }
}
