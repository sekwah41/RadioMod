package com.sekwah.radiomod.blocks.tileentities;

import java.util.UUID;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.blocks.BlockRadio;
import com.sekwah.radiomod.music.MusicSource;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by on 05/08/2016.
 *
 * Tile entities update when users are in a radius of about 200 blocks.
 *
 * @author sekwah41
 */
public class TileEntityRadio extends TileEntity implements ITickable {
    private String uuid = UUID.randomUUID().toString();

    private int runState = BlockRadio.RUNSTATE_OFF;
    private int framePaused = 0;

    private boolean isSetup = false;

    /**
     * Server side tracking data for
     */
    private boolean runningMusic = false;

    private int musicTick = 0;

    public TileEntityRadio(){
        RadioMod.instance.musicManager.createMusicSource(this.uuid);
        //RadioMod.instance.musicManager. = new MusicSource();
    }

    @Override
    public void update() {
        if(RadioMod.proxy.isClient()){
            this.updateDistance();
        }

        switch(this.getRunState()) {
            case BlockRadio.RUNSTATE_BOOTINGUP:
            break;
            case BlockRadio.RUNSTATE_PLAYING:
            break;
        }
    }

    @SideOnly(Side.CLIENT)
    private void updateDistance() {
        //RadioMod.logger.info("Test");
        EntityPlayer playerSP = FMLClientHandler.instance().getClient().thePlayer;
        if(playerSP != null){
            float distance = (float) this.getDistanceSq(playerSP.posX, playerSP.posY, playerSP.posZ);
            if(RadioMod.instance.musicManager.sourceDistances.containsKey(this.uuid)){
            	try {
	                float curDistance = RadioMod.instance.musicManager.sourceDistances.get(this.uuid);
	                if(curDistance > distance){
	                    RadioMod.instance.musicManager.sourceDistances.put(this.uuid, distance);
	                }
            	}catch(NullPointerException e) {
            		RadioMod.logger.info("Error with uuid");
            	}
            }
            else{
                RadioMod.instance.musicManager.sourceDistances.put(this.uuid,distance);
            }
        }
    }

    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
    }

    public NBTTagCompound getUpdateTag()
    {
        NBTTagCompound nbttagcompound = this.writeToNBT(new NBTTagCompound());
        return nbttagcompound;
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.runState = compound.getInteger("RunState");
        String tempuuid = compound.getString("RadioID");
        this.framePaused = compound.getInteger("FramePaused");
        if(this.uuid.equals("")){
            tempuuid = UUID.randomUUID().toString();
        }
        this.setUUID(tempuuid);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("RunState", this.runState);
        compound.setString("RadioID", this.uuid);
        compound.setInteger("FramePaused", this.framePaused);
        /**
         * Check chest for largs nbt writing {@link net.minecraft.tileentity.TileEntityChest}
         * Need to start using links more. They are pretty useful :D
         */

        return compound;
    }

    public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SPacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.getNbtCompound());
    }

    public void setUUID(String uuid){
        if(RadioMod.instance.musicManager.sourceDistances.containsKey(uuid)){
            return;
        }
        MusicSource source = RadioMod.instance.musicManager.radioSources.get(this.uuid);
        if(RadioMod.instance.musicManager.sourceDistances.containsKey(this.uuid)){
        	try {
	            float dist = RadioMod.instance.musicManager.sourceDistances.get(this.uuid);
	            RadioMod.instance.musicManager.sourceDistances.remove(this.uuid);
	            RadioMod.instance.musicManager.sourceDistances.put(uuid, dist);
        	}catch(NullPointerException e) {
        		throw(e);
        	}
        }
        if(RadioMod.instance.musicManager.sourceDistances.containsKey(uuid)){

        }
        RadioMod.instance.musicManager.radioSources.remove(this.uuid);
        RadioMod.instance.musicManager.radioSources.put(uuid, source);
        RadioMod.logger.info("UUID for radio set as: " + uuid);
        RadioMod.instance.musicManager.createMusicSource(uuid);
        this.uuid = uuid;
    }

    public String getUUID(){
        return this.uuid;
    }

    public int getRunState() {
        return this.runState;
    }
    
    /**
     * Called when the chunk this TileEntity is on is Unloaded.
     */
    public void onChunkUnload()
    {
        RadioMod.logger.info("Unload");
        //RadioMod.instance.musicManager.radioSources.get(this.uuid).stopMusic();
    }

    public void onLoad()
    {
        RadioMod.instance.musicManager.createMusicSource(this.uuid);
        //RadioMod.logger.info("Spawned");
    }

    public void bootUp() {
        this.runState = BlockRadio.RUNSTATE_BOOTINGUP;
        
        this.markDirty();
    }

    public void shutdown() {
        this.runState = BlockRadio.RUNSTATE_OFF;
        
        this.markDirty();
    }

    public void setRunState(int runStateIn) {
        if(runStateIn == BlockRadio.RUNSTATE_BOOTINGUP && this.runState != BlockRadio.RUNSTATE_BOOTINGUP){
            this.bootUp();
        }
        this.runState = runStateIn;
        this.markDirty();
    }

	public MusicSource getMusicSource() {
		return RadioMod.instance.musicManager.radioSources.get(this.uuid);
	}
	
	public int getFramePaused() {
		return this.framePaused;
	}
	
	public void setFramePaused(int framePausedIn) {
		this.framePaused = framePausedIn;
		this.markDirty();
	}
}