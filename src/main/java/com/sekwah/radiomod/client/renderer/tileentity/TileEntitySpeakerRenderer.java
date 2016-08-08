package com.sekwah.radiomod.client.renderer.tileentity;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.blocks.BlockRadio;
import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;
import com.sekwah.radiomod.blocks.tileentities.TileEntitySpeaker;
import com.sekwah.radiomod.client.model.tile.ModelMp3DockRadio;
import com.sekwah.radiomod.client.model.tile.ModelSpeaker;
import com.sekwah.radiomod.music.MusicManager;
import com.sekwah.radiomod.music.MusicSource;
import com.sekwah.radiomod.music.MusicTracker;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

/**
 * Created by on 04/08/2016.
 *
 * @author sekwah41
 */
public class TileEntitySpeakerRenderer extends TileEntitySpecialRenderer<TileEntitySpeaker> {
    public static final ModelSpeaker MODEL = new ModelSpeaker();
    public static final ResourceLocation TEXTURE = new ResourceLocation(RadioMod.modid + ":textures/blocks/speaker.png");
    public static final ResourceLocation TEXTURE_ON = new ResourceLocation(RadioMod.modid + ":textures/blocks/speaker_on.png");
    //This method is called when minecraft renders a tile entity
    public void renderTileEntityAt(TileEntitySpeaker te, double x, double y, double z, float partialTicks, int destroyStage)
    {
        this.renderRadio(x, y, z, te, (double)partialTicks);
    }

    // this method isnt called yet sadly, make sure its called before fixing this code
    public void renderRadio(double x, double y, double z, TileEntitySpeaker par1TileEntity, double partialTicks) {

        EnumFacing enumfacing = par1TileEntity != null ? EnumFacing.getFront(par1TileEntity.getBlockMetadata() & 7) : EnumFacing.NORTH;

        float rotation = 0;

        switch (enumfacing)
        {
            case NORTH:
                break;
            case SOUTH:
                rotation = 180.0F;
                break;
            case WEST:
                rotation = 270.0F;
                break;
            case EAST:
            default:
                rotation = 90.0F;
        }
        
        boolean hasOwner = (par1TileEntity != null && par1TileEntity.getOwnerUUID() != null && !par1TileEntity.getOwnerUUID().equals(""));
        
        /*if(hasOwner && RadioMod.instance.musicManager.radioSources.get(par1TileEntity.getOwnerUUID()) != null) {
        	MusicSource source = RadioMod.instance.musicManager.radioSources.get(par1TileEntity.getOwnerUUID());
        	int sampleRate = source.getSampleRatePow2();

        	if(source.getPlayer() != null && source.getIsPlaying() && sampleRate != 0){
	        	float average = 0;
	            double[] freqData = source.getFrequencyData(0);
	            for(int i = 0; i < 100; i++) {
	            	average += freqData[i];
	            }
	            average /= 100;
	            this.MODEL.bassAmp = average*0.1f;
        	}
        }*/
        
        this.bindTexture(hasOwner ? TEXTURE_ON : TEXTURE);

        GlStateManager.enableRescaleNormal();
        this.MODEL.render(x,y,z,rotation);
        GlStateManager.disableRescaleNormal();
    }
}
