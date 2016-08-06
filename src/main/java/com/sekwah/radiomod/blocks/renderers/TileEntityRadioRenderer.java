package com.sekwah.radiomod.blocks.renderers;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.blocks.RadioBlock;
import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;

import com.sekwah.radiomod.client.model.tile.ModelComputer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by on 04/08/2016.
 *
 * @author sekwah41
 */
public class TileEntityRadioRenderer extends TileEntitySpecialRenderer<TileEntityRadio> {

    public final ModelComputer model = new ModelComputer();

    //This method is called when minecraft renders a tile entity
    public void renderTileEntityAt(TileEntityRadio te, double x, double y, double z, float partialTicks, int destroyStage)
    {
        this.renderRadio(x, y, z, te, (double)partialTicks);
    }

    // this method isnt called yet sadly, make sure its called before fixing this code
    public void renderRadio(double x, double y, double z, TileEntityRadio par1TileEntity, double partialTicks) {

        EnumFacing enumfacing = EnumFacing.getFront(par1TileEntity.getBlockMetadata() & 7);

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

        int dir = par1TileEntity.getBlockMetadata();
        switch(par1TileEntity.getRunState()){
            case RadioBlock.RUNSTATE_OFF:
                this.bindTexture(new ResourceLocation(RadioMod.modid + ":textures/blocks/computer.png"));
            case RadioBlock.RUNSTATE_BOOTINGUP:
                this.bindTexture(new ResourceLocation(RadioMod.modid + ":textures/blocks/computer.png"));
            case RadioBlock.RUNSTATE_ON:
                this.bindTexture(new ResourceLocation(RadioMod.modid + ":textures/blocks/computer.png"));
            case RadioBlock.RUNSTATE_PLAYING:
                this.bindTexture(new ResourceLocation(RadioMod.modid + ":textures/blocks/computer.png"));
            default:
                this.bindTexture(new ResourceLocation(RadioMod.modid + ":textures/blocks/computer.png"));
        }
        GlStateManager.enableRescaleNormal();
        this.model.render(x,y,z,rotation);
        GlStateManager.disableRescaleNormal();
    }
}
