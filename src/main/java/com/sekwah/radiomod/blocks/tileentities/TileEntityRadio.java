package com.sekwah.radiomod.blocks.tileentities;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.blocks.RadioBlock;
import com.sekwah.radiomod.music.MusicSource;

import com.sun.istack.internal.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.UUID;

/**
 * Created by on 05/08/2016.
 *
 * Tile entities update when users are in a radius of about 200 blocks.
 *
 * @author sekwah41
 */
public class TileEntityRadio extends TileEntity implements ITickable {

    public String uuid = UUID.randomUUID().toString();

    private int rotation = 0;
    private int runState = RadioBlock.RUNSTATE_OFF;
    private int bootupSequence = 0;

    private boolean isSetup = false;

    /**
     * Server side tracking data for
     */
    private boolean runningMusic = false;

    private int musicTick = 0;

    public TileEntityRadio(){

        //RadioMod.instance.musicManager. = new MusicSource();
    }

    @Override
    public void update() {
        //RadioMod.logger.info("Update");

        //RadioMod.logger.info(this.uuid);

        if(!this.isSetup){
            this.isSetup = true;
            this.createMusicSource();
        }

        if(RadioMod.proxy.isClient()){
            this.updateDistance();
        }

        switch(this.getRunState()) {
            case RadioBlock.RUNSTATE_BOOTINGUP:
                if(this.bootupSequence > 0)this.bootupSequence--;
                else this.setRunState(RadioBlock.RUNSTATE_ON);
            case RadioBlock.RUNSTATE_PLAYING:

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
                float curDistance = RadioMod.instance.musicManager.sourceDistances.get(this.uuid);
                if(curDistance > distance){
                    RadioMod.instance.musicManager.sourceDistances.put(this.uuid, distance);
                }
            }
            else{
                RadioMod.instance.musicManager.sourceDistances.put(this.uuid,distance);
            }
        }
    }

    @Nullable
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
        this.rotation = compound.getByte("Rot");
        this.runState = compound.getInteger("RunState");
        this.uuid = compound.getString("RadioID");
        if(this.uuid.equals("")){
            this.uuid = UUID.randomUUID().toString();
        }
        //RadioMod.logger.info(this.runState);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setByte("Rot", (byte)(this.rotation & 255));
        compound.setInteger("RunState", this.runState);
        compound.setString("RadioID", this.uuid);
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

    public int getRunState() {
        return this.runState;
    }

    @SideOnly(Side.CLIENT)
    public int getRotation()
    {
        return this.rotation;
    }

    /**
     * Called when the chunk this TileEntity is on is Unloaded.
     */
    public void onChunkUnload()
    {
        RadioMod.logger.info("Unload");
        RadioMod.instance.musicManager.radioSources.get(this.uuid).stopMusic();
    }

    public void onLoad()
    {
        RadioMod.logger.info("Spawned");
    }

    public void setRotation(int rotation)
    {
        this.rotation = rotation;
    }

    public void bootUp() {
        this.runState = RadioBlock.RUNSTATE_BOOTINGUP;
        RadioMod.logger.info("BOOTING UPPPPP!!!!!");
        this.bootupSequence = 50;
    }

    public void shutdown() {
        this.runState = RadioBlock.RUNSTATE_OFF;
    }

    public void setRunState(int runStateIn) {
        if(runStateIn == RadioBlock.RUNSTATE_BOOTINGUP && this.runState != RadioBlock.RUNSTATE_BOOTINGUP){
            this.bootUp();
        }
        this.runState = runStateIn;
        this.markDirty();
    }

    public void createMusicSource(){
        RadioMod.instance.musicManager.radioSources.put(this.uuid, new MusicSource());
    }


}