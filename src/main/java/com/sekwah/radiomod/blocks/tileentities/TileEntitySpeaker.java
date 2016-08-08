package com.sekwah.radiomod.blocks.tileentities;

import java.util.UUID;

import com.sekwah.radiomod.RadioMod;

import com.sekwah.radiomod.music.MusicSource;
import com.sekwah.radiomod.network.packets.server.ServerLoadPlayerPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntitySpeaker extends TileEntityAddon {
	@Override
    public void update() {
		if(RadioMod.proxy.isClient()){
            this.updateDistance();
        }
    }
	
	@SideOnly(Side.CLIENT)
    private void updateDistance() {
        //RadioMod.logger.info("Test");
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
        this.setupRadio();
        if(RadioMod.proxy.isClient()){
            RadioMod.packetNetwork.sendToServer(new ServerLoadPlayerPacket(ownerUUID));
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        return compound;
    }

    public void onChunkUnload()
    {
        //RadioMod.logger.info("Unload");
        RadioMod.instance.musicManager.radioSources.get(this.getOwnerUUID()).stopMusic();
    }

    public void onLoad()
    {
        RadioMod.instance.musicManager.createMusicSource(this.getOwnerUUID());
        //RadioMod.logger.info("Spawned");
    }

    public void setupRadio(){
        MusicSource source = RadioMod.instance.musicManager.radioSources.get(this.ownerUUID);
        if(RadioMod.instance.musicManager.sourceDistances.containsKey(this.ownerUUID)){
            try {
                float dist = RadioMod.instance.musicManager.sourceDistances.get(this.ownerUUID);
                RadioMod.instance.musicManager.sourceDistances.remove(this.ownerUUID);
                RadioMod.instance.musicManager.sourceDistances.put(ownerUUID, dist);
            }catch(NullPointerException e) {
                throw(e);
            }
        }
        if(RadioMod.instance.musicManager.sourceDistances.containsKey(ownerUUID)){

        }
        RadioMod.instance.musicManager.radioSources.remove(this.ownerUUID);
        RadioMod.instance.musicManager.radioSources.put(ownerUUID, source);
        RadioMod.logger.info("Radio setup on UUID: " + ownerUUID);
        RadioMod.instance.musicManager.createMusicSource(ownerUUID);
    }

    public MusicSource getMusicSource() {
        return RadioMod.instance.musicManager.radioSources.get(this.getOwnerUUID());
    }

    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
    }

    public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }
}
