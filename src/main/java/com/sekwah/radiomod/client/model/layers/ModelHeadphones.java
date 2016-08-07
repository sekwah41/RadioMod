package com.sekwah.radiomod.client.model.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHeadphones extends ModelBase
{
    ModelRenderer RootNode;
    ModelRenderer Headphone2;
    ModelRenderer Headphone2A;
    ModelRenderer Headphone2B;
    ModelRenderer Headphone2C;
    ModelRenderer Headphone2D;
    ModelRenderer Headphone2E;
    ModelRenderer HeadbandTop;
    ModelRenderer Headband1;
    ModelRenderer Headband2;
    ModelRenderer HeadbandCushion;
    ModelRenderer HeadbandCushionA;
    ModelRenderer Headphone1;
    ModelRenderer Headphone1A;
    ModelRenderer Headphone1B;
    ModelRenderer Headphone1C;
    ModelRenderer Headphone1D;
    ModelRenderer Headphone1E;

    public ModelHeadphones()
    {
        this( 0.0f );
    }

    public ModelHeadphones( float par1 )
    {
        RootNode = new ModelRenderer( this, 16, 16 );
        RootNode.setTextureSize( 64, 64 );
        RootNode.addBox( 0F, 0F, 0F, 0, 0, 0);
        RootNode.setRotationPoint( 0F, 21F, 0F );
        Headphone2 = new ModelRenderer( this, 0, 0 );
        Headphone2.setTextureSize( 64, 64 );
        Headphone2.addBox( -1.5F, -3F, -3F, 3, 6, 6);
        Headphone2.setRotationPoint( -9F, 20F, 0F );
        Headphone2A = new ModelRenderer( this, 8, 16 );
        Headphone2A.setTextureSize( 64, 64 );
        Headphone2A.addBox( -1.5F, -0.5F, -2.5F, 3, 1, 5);
        Headphone2A.setRotationPoint( -8.5F, 23.5F, -4.371139E-08F );
        Headphone2B = new ModelRenderer( this, 8, 16 );
        Headphone2B.setTextureSize( 64, 64 );
        Headphone2B.addBox( -1.5F, -0.5F, -2.5F, 3, 1, 5);
        Headphone2B.setRotationPoint( -8.5F, 16.5F, -4.371139E-08F );
        Headphone2C = new ModelRenderer( this, 8, 16 );
        Headphone2C.setTextureSize( 64, 64 );
        Headphone2C.addBox( -1.5F, -0.5F, -2.5F, 3, 1, 5);
        Headphone2C.setRotationPoint( -8.5F, 20F, -3.5F );
        Headphone2D = new ModelRenderer( this, 8, 16 );
        Headphone2D.setTextureSize( 64, 64 );
        Headphone2D.addBox( -1.5F, -0.5F, -2.5F, 3, 1, 5);
        Headphone2D.setRotationPoint( -8.499999F, 20F, 3.5F );
        Headphone2E = new ModelRenderer( this, 18, 0 );
        Headphone2E.setTextureSize( 64, 64 );
        Headphone2E.addBox( -0.5F, -2F, -2F, 1, 4, 4);
        Headphone2E.setRotationPoint( -10.5F, 20F, 1.311342E-07F );
        HeadbandTop = new ModelRenderer( this, 0, 12 );
        HeadbandTop.setTextureSize( 64, 64 );
        HeadbandTop.addBox( -8F, -0.5F, -1.5F, 16, 1, 3);
        HeadbandTop.setRotationPoint( 0F, 9.5F, 0F );
        Headband1 = new ModelRenderer( this, 0, 12 );
        Headband1.setTextureSize( 64, 64 );
        Headband1.addBox( -0.5F, -3.5F, -1.5F, 1, 7, 3);
        Headband1.setRotationPoint( 8.5F, 14.5F, 0F );
        Headband2 = new ModelRenderer( this, 0, 12 );
        Headband2.setTextureSize( 64, 64 );
        Headband2.addBox( -0.5F, -3.5F, -1.5F, 1, 7, 3);
        Headband2.setRotationPoint( -8.5F, 14.5F, 0F );
        HeadbandCushion = new ModelRenderer( this, 0, 23 );
        HeadbandCushion.setTextureSize( 64, 64 );
        HeadbandCushion.addBox( -9F, -0.5F, -1.5F, 18, 1, 3);
        HeadbandCushion.setRotationPoint( 0F, 10.5F, 0F );
        HeadbandCushionA = new ModelRenderer( this, 1, 27 );
        HeadbandCushionA.setTextureSize( 64, 64 );
        HeadbandCushionA.addBox( -7F, -0.5F, -2F, 14, 1, 4);
        HeadbandCushionA.setRotationPoint( 0F, 10.5F, 0F );
        Headphone1 = new ModelRenderer( this, 0, 0 );
        Headphone1.setTextureSize( 64, 64 );
        Headphone1.addBox( -1.5F, -3F, -3F, 3, 6, 6);
        Headphone1.setRotationPoint( 9F, 20F, 0F );
        Headphone1A = new ModelRenderer( this, 8, 16 );
        Headphone1A.setTextureSize( 64, 64 );
        Headphone1A.addBox( -1.5F, -0.5F, -2.5F, 3, 1, 5);
        Headphone1A.setRotationPoint( 8.5F, 23.5F, 0F );
        Headphone1B = new ModelRenderer( this, 8, 16 );
        Headphone1B.setTextureSize( 64, 64 );
        Headphone1B.addBox( -1.5F, -0.5F, -2.5F, 3, 1, 5);
        Headphone1B.setRotationPoint( 8.5F, 16.5F, 0F );
        Headphone1C = new ModelRenderer( this, 8, 16 );
        Headphone1C.setTextureSize( 64, 64 );
        Headphone1C.addBox( -1.5F, -0.5F, -2.5F, 3, 1, 5);
        Headphone1C.setRotationPoint( 8.5F, 20F, 3.5F );
        Headphone1D = new ModelRenderer( this, 8, 16 );
        Headphone1D.setTextureSize( 64, 64 );
        Headphone1D.addBox( -1.5F, -0.5F, -2.5F, 3, 1, 5);
        Headphone1D.setRotationPoint( 8.5F, 20F, -3.5F );
        Headphone1E = new ModelRenderer( this, 18, 0 );
        Headphone1E.setTextureSize( 64, 64 );
        Headphone1E.addBox( -0.5F, -2F, -2F, 1, 4, 4);
        Headphone1E.setRotationPoint( 10.5F, 20F, 0F );
    }

   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
   {
        RootNode.rotateAngleX = 0F;
        RootNode.rotateAngleY = 0F;
        RootNode.rotateAngleZ = 0F;
        RootNode.renderWithRotation(par7);

        Headphone2.rotateAngleX = 0F;
        Headphone2.rotateAngleY = -3.141593F;
        Headphone2.rotateAngleZ = 0F;
        Headphone2.renderWithRotation(par7);

        Headphone2A.rotateAngleX = 0F;
        Headphone2A.rotateAngleY = -3.141593F;
        Headphone2A.rotateAngleZ = 0F;
        Headphone2A.renderWithRotation(par7);

        Headphone2B.rotateAngleX = 0F;
        Headphone2B.rotateAngleY = -3.141593F;
        Headphone2B.rotateAngleZ = 0F;
        Headphone2B.renderWithRotation(par7);

        Headphone2C.rotateAngleX = 1.570796F;
        Headphone2C.rotateAngleY = -3.141593F;
        Headphone2C.rotateAngleZ = 0F;
        Headphone2C.renderWithRotation(par7);

        Headphone2D.rotateAngleX = 1.570796F;
        Headphone2D.rotateAngleY = -3.141593F;
        Headphone2D.rotateAngleZ = 0F;
        Headphone2D.renderWithRotation(par7);

        Headphone2E.rotateAngleX = -6.283185F;
        Headphone2E.rotateAngleY = 0F;
        Headphone2E.rotateAngleZ = 0F;
        Headphone2E.renderWithRotation(par7);

        HeadbandTop.rotateAngleX = 0F;
        HeadbandTop.rotateAngleY = 0F;
        HeadbandTop.rotateAngleZ = 0F;
        HeadbandTop.renderWithRotation(par7);

        Headband1.rotateAngleX = 0F;
        Headband1.rotateAngleY = 0F;
        Headband1.rotateAngleZ = 0F;
        Headband1.renderWithRotation(par7);

        Headband2.rotateAngleX = 0F;
        Headband2.rotateAngleY = 0F;
        Headband2.rotateAngleZ = 0F;
        Headband2.renderWithRotation(par7);

        HeadbandCushion.rotateAngleX = 0F;
        HeadbandCushion.rotateAngleY = 0F;
        HeadbandCushion.rotateAngleZ = 0F;
        HeadbandCushion.renderWithRotation(par7);

        HeadbandCushionA.rotateAngleX = 0F;
        HeadbandCushionA.rotateAngleY = 0F;
        HeadbandCushionA.rotateAngleZ = 0F;
        HeadbandCushionA.renderWithRotation(par7);

        Headphone1.rotateAngleX = 0F;
        Headphone1.rotateAngleY = 0F;
        Headphone1.rotateAngleZ = 0F;
        Headphone1.renderWithRotation(par7);

        Headphone1A.rotateAngleX = 0F;
        Headphone1A.rotateAngleY = 0F;
        Headphone1A.rotateAngleZ = 0F;
        Headphone1A.renderWithRotation(par7);

        Headphone1B.rotateAngleX = 0F;
        Headphone1B.rotateAngleY = 0F;
        Headphone1B.rotateAngleZ = 0F;
        Headphone1B.renderWithRotation(par7);

        Headphone1C.rotateAngleX = 1.570796F;
        Headphone1C.rotateAngleY = 0F;
        Headphone1C.rotateAngleZ = 0F;
        Headphone1C.renderWithRotation(par7);

        Headphone1D.rotateAngleX = 1.570796F;
        Headphone1D.rotateAngleY = 0F;
        Headphone1D.rotateAngleZ = 0F;
        Headphone1D.renderWithRotation(par7);

        Headphone1E.rotateAngleX = 0F;
        Headphone1E.rotateAngleY = 0F;
        Headphone1E.rotateAngleZ = 0F;
        Headphone1E.renderWithRotation(par7);
    }
}
