package com.sekwah.radiomod.client.model.tile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
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

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
   {
        RootNode.rotateAngleX = 0F;
        RootNode.rotateAngleY = 0F;
        RootNode.rotateAngleZ = 0F;
        RootNode.renderWithRotation(par7);

        SpeakerBox.rotateAngleX = 0F;
        SpeakerBox.rotateAngleY = 0F;
        SpeakerBox.rotateAngleZ = 0F;
        SpeakerBox.renderWithRotation(par7);

    }

}
