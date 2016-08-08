package com.sekwah.radiomod.client.renderer.tileentity;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.blocks.BlockRadio;
import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;
import com.sekwah.radiomod.blocks.tileentities.TileEntitySpeaker;
import com.sekwah.radiomod.blocks.tileentities.TileEntityVisualizer;
import com.sekwah.radiomod.client.model.tile.ModelMp3DockRadio;
import com.sekwah.radiomod.client.model.tile.ModelSpeaker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

/**
 * Created by on 04/08/2016.
 *
 * @author sekwah41
 */
public class TileEntityVisualizerRenderer extends TileEntitySpecialRenderer<TileEntityVisualizer> {
	
    //This method is called when minecraft renders a tile entity
    public void renderTileEntityAt(TileEntityVisualizer te, double x, double y, double z, float partialTicks, int destroyStage)
    {
        this.renderRadio(x, y, z, te, (double)partialTicks);
    }

    // this method isnt called yet sadly, make sure its called before fixing this code
    public void renderRadio(double x, double y, double z, TileEntityVisualizer par1TileEntity, double partialTicks) {

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
        
        GlStateManager.pushMatrix();
        	GlStateManager.translate(x, y, z);
        	GlStateManager.rotate(rotation, 0, 1, 0);
        	
            GlStateManager.disableLighting();
            GlStateManager.disableTexture2D();
            
            float[] bands = new float[10];
            for(int i = 0; i < bands.length; i++) {
            	bands[i] = (float) Math.abs((Minecraft.getMinecraft().thePlayer.ticksExisted+partialTicks)*0.03f+i*0.1f);
            	
            	float bandW = 1;
            	float bandH = 1;
            	float spacing = 0.1f;
            	float bandX = i*(bandW+spacing);
            	float bandY = 0;
            	float r = 1;
            	float g = 0;
            	float b = 0;
            	float a = 1;
            	
                Tessellator tessellator = Tessellator.getInstance();
                VertexBuffer vertexbuffer = tessellator.getBuffer();
                vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                vertexbuffer.pos(bandX, bandY + bandH, 0D).color(r, g, b, a).endVertex();
                vertexbuffer.pos(bandX + bandW, bandY + bandH, 0D).color(r, g, b, a).endVertex();
                vertexbuffer.pos(bandX + bandW, bandY + 0.0D, 0D).color(r, g, b, a).endVertex();
                vertexbuffer.pos(bandX, bandY + 0.0D, 0D).color(r, g, b, a).endVertex();
                tessellator.draw();
            }
            
            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
