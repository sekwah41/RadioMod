package com.sekwah.radiomod.client.model.tile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelSpeaker extends ModelBase
{
    ModelRenderer RootNode;
    ModelRenderer SpeakerBox;

    public ModelSpeaker()
    {
        this( 0.0f );
    }

    public ModelSpeaker( float par1 )
    {
        RootNode = new ModelRenderer( this, 16, 16 );
        RootNode.setTextureSize( 64, 64 );
        RootNode.addBox( 0F, 0F, 0F, 0, 0, 0);
        RootNode.setRotationPoint( 0F, 5F, 0F );
        SpeakerBox = new ModelRenderer( this, 0, 0 );
        SpeakerBox.setTextureSize( 64, 64 );
        SpeakerBox.addBox( -7.5F, -12F, -7.5F, 15, 24, 15);
        SpeakerBox.setRotationPoint( 0F, 12F, 0F );
    }

   public void render(double x, double y, double z, float rotation)
    {
        GlStateManager.pushMatrix();

        GlStateManager.translate((float) x, (float) y, (float) z);

        //GlStateManager.translate((float) x + 0.5F, (float) y + 0.245f, (float) z + 0.61F);
        GlStateManager.rotate(180, 1F, 0F, 0F);
        GlStateManager.translate(0.5F, -0.245f, -0.5F);

        GlStateManager.rotate(rotation, 0F, 1F, 0F);
        GlStateManager.translate(0f,-1.24f,0f);
        GlStateManager.scale(1,1,1);


        float par1 = 0.0625F;
        RootNode.rotateAngleX = 0F;
        RootNode.rotateAngleY = 0F;
        RootNode.rotateAngleZ = 0F;
        RootNode.renderWithRotation(par1);

        SpeakerBox.rotateAngleX = 0F;
        SpeakerBox.rotateAngleY = 0F;
        SpeakerBox.rotateAngleZ = 0F;
        SpeakerBox.renderWithRotation(par1);

        GlStateManager.popMatrix();
    }

}
