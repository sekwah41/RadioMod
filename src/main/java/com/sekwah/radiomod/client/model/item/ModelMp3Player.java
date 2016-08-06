package com.sekwah.radiomod.client.model.item;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMp3Player extends ModelBase
{
    ModelRenderer RootNode;
    ModelRenderer Mp3Player;

    public ModelMp3Player()
    {
        this( 0.0f );
    }

    public ModelMp3Player( float par1 )
    {
        RootNode = new ModelRenderer( this, 16, 16 );
        RootNode.setTextureSize( 64, 32 );
        RootNode.addBox( 0F, 0F, 0F, 0, 0, 0);
        RootNode.setRotationPoint( 0F, 24F, 0F );
        Mp3Player = new ModelRenderer( this, 0, 0 );
        Mp3Player.setTextureSize( 64, 32 );
        Mp3Player.addBox( -3.5F, -1F, -5.5F, 7, 2, 11);
        Mp3Player.setRotationPoint( 0F, 18.5F, 0F );
    }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
   {
        RootNode.rotateAngleX = 0F;
        RootNode.rotateAngleY = 0F;
        RootNode.rotateAngleZ = 0F;
        RootNode.renderWithRotation(par7);

        Mp3Player.rotateAngleX = 1.570796F;
        Mp3Player.rotateAngleY = 0F;
        Mp3Player.rotateAngleZ = 0F;
        Mp3Player.renderWithRotation(par7);

    }

}
