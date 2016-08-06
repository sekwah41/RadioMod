package com.sekwah.radiomod.blocks.renderers;

import com.sekwah.radiomod.blocks.tileentities.TileEntityBase;
import com.sekwah.radiomod.client.model.tile.ModelBonsaiTree;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created by on 04/08/2016.
 *
 * @author sekwah41
 */
public class TileEntityTestRadioRenderer extends TileEntitySpecialRenderer<TileEntityBase> {

    public final ModelBonsaiTree model = new ModelBonsaiTree();

    //This method is called when minecraft renders a tile entity
    public void renderTileEntityAt(TileEntityBase te, double x, double y, double z, float partialTicks, int destroyStage)
    {
        this.renderRadio(x, y, z, te, (double)partialTicks);
    }

    // this method isnt called yet sadly, make sure its called before fixing this code
    public void renderRadio(double x, double y, double z, TileEntityBase par1TileEntityBonsai, double partialTicks) {

        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
        int dir = par1TileEntityBonsai.getBlockMetadata();
        GL11.glRotatef(180, 1F, 0F, 0F);
        GL11.glRotatef(dir * (45F), 0F, 1F, 0F);

        this.model.render();

        GL11.glPopMatrix();
    }
}
