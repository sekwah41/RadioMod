package com.sekwah.radiomod.blocks.renderers;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.blocks.RadioBlock;
import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;

import com.sekwah.radiomod.client.model.tile.ModelComputer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
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
    public void renderRadio(double x, double y, double z, TileEntityRadio par1TileEntityBonsai, double partialTicks) {
        
        int dir = par1TileEntityBonsai.getBlockMetadata();
        this.bindTexture(new ResourceLocation(RadioMod.modid + ":textures/blocks/computer.png"));
        this.model.render(x,y,z,dir * (45F));
    }
}
