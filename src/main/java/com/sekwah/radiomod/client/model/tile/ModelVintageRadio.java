package com.sekwah.radiomod.client.model.tile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelVintageRadio extends ModelBase
{
    ModelRenderer RootNode;
    ModelRenderer SpeakerBox;
    ModelRenderer Thingy2;
    ModelRenderer SpeakerBoxA;
    ModelRenderer SpeakerBoxB;
    ModelRenderer SpeakerBoxC;
    ModelRenderer SpeakerBoxD;
    ModelRenderer Button1;
    ModelRenderer Button2;
    ModelRenderer Button3;
    ModelRenderer knob;
    ModelRenderer Thingy;
    ModelRenderer Handle;
    ModelRenderer Metal1;
    ModelRenderer Metal1B;
    ModelRenderer Metal2;
    ModelRenderer Metal2B;
    ModelRenderer Metal3;
    ModelRenderer Metal3B;
    ModelRenderer Metal4;
    ModelRenderer Metal4B;
    ModelRenderer AntennaeBase;
    ModelRenderer Antennae;
    ModelRenderer AntennaeTip;

    public ModelVintageRadio()
    {
        this( 0.0f );
    }

    public ModelVintageRadio( float par1 )
    {
        RootNode = new ModelRenderer( this, 16, 16 );
        RootNode.setTextureSize( 128, 128 );
        RootNode.addBox( 0F, 0F, 0F, 0, 0, 0);
        RootNode.setRotationPoint( 0F, 24F, 0F );
        SpeakerBox = new ModelRenderer( this, 0, 0 );
        SpeakerBox.setTextureSize( 128, 128 );
        SpeakerBox.addBox( -20.5F, -10F, -6.5F, 41, 20, 13);
        SpeakerBox.setRotationPoint( 0F, 12F, 0F );
        Thingy2 = new ModelRenderer( this, 99, 2 );
        Thingy2.setTextureSize( 128, 128 );
        Thingy2.addBox( -1F, -1.5F, -1.5F, 2, 3, 3);
        Thingy2.setRotationPoint( -21.5F, 5F, 0F );
        SpeakerBoxA = new ModelRenderer( this, 4, 47 );
        SpeakerBoxA.setTextureSize( 128, 128 );
        SpeakerBoxA.addBox( -18.5F, -0.5F, -6.5F, 37, 1, 13);
        SpeakerBoxA.setRotationPoint( 0F, 0.5F, 0F );
        SpeakerBoxB = new ModelRenderer( this, 2, 33 );
        SpeakerBoxB.setTextureSize( 128, 128 );
        SpeakerBoxB.addBox( -19.5F, -0.5F, -6.5F, 39, 1, 13);
        SpeakerBoxB.setRotationPoint( 0F, 1.5F, 0F );
        SpeakerBoxC = new ModelRenderer( this, 2, 33 );
        SpeakerBoxC.setTextureSize( 128, 128 );
        SpeakerBoxC.addBox( -19.5F, -0.5F, -6.5F, 39, 1, 13);
        SpeakerBoxC.setRotationPoint( 0F, 22.5F, 0F );
        SpeakerBoxD = new ModelRenderer( this, 4, 47 );
        SpeakerBoxD.setTextureSize( 128, 128 );
        SpeakerBoxD.addBox( -18.5F, -0.5F, -6.5F, 37, 1, 13);
        SpeakerBoxD.setRotationPoint( 0F, 23.5F, 0F );
        Button1 = new ModelRenderer( this, 30, 79 );
        Button1.setTextureSize( 128, 128 );
        Button1.addBox( -1.5F, -0.5F, -0.5F, 3, 1, 1);
        Button1.setRotationPoint( -5F, 0F, 0F );
        Button2 = new ModelRenderer( this, 30, 79 );
        Button2.setTextureSize( 128, 128 );
        Button2.addBox( -1.5F, -0.5F, -0.5F, 3, 1, 1);
        Button2.setRotationPoint( 5F, 0F, 0F );
        Button3 = new ModelRenderer( this, 30, 79 );
        Button3.setTextureSize( 128, 128 );
        Button3.addBox( -1.5F, -0.5F, -0.5F, 3, 1, 1);
        Button3.setRotationPoint( 0F, 0F, 0F );
        knob = new ModelRenderer( this, 2, 2 );
        knob.setTextureSize( 128, 128 );
        knob.addBox( -1F, -1F, -0.5F, 2, 2, 1);
        knob.setRotationPoint( 10F, 11.5F, -7F );
        Thingy = new ModelRenderer( this, 99, 2 );
        Thingy.setTextureSize( 128, 128 );
        Thingy.addBox( -1F, -1.5F, -1.5F, 2, 3, 3);
        Thingy.setRotationPoint( 21.5F, 5F, 0F );
        Handle = new ModelRenderer( this, 18, 66 );
        Handle.setTextureSize( 128, 128 );
        Handle.addBox( -15.5F, -1F, -1.5F, 31, 2, 3);
        Handle.setRotationPoint( 0F, -6F, 0F );
        Metal1 = new ModelRenderer( this, 20, 73 );
        Metal1.setTextureSize( 128, 128 );
        Metal1.addBox( -4F, -0.5F, -0.5F, 8, 1, 1);
        Metal1.setRotationPoint( 18F, -6F, -0.75F );
        Metal1B = new ModelRenderer( this, 20, 73 );
        Metal1B.setTextureSize( 128, 128 );
        Metal1B.addBox( -4F, -0.5F, -0.5F, 8, 1, 1);
        Metal1B.setRotationPoint( 18F, -6F, 0.75F );
        Metal2 = new ModelRenderer( this, 23, 76 );
        Metal2.setTextureSize( 128, 128 );
        Metal2.addBox( -0.5F, -4.5F, -0.5F, 1, 9, 1);
        Metal2.setRotationPoint( 21.5F, -1F, -0.75F );
        Metal2B = new ModelRenderer( this, 23, 76 );
        Metal2B.setTextureSize( 128, 128 );
        Metal2B.addBox( -0.5F, -4.5F, -0.5F, 1, 9, 1);
        Metal2B.setRotationPoint( 21.5F, -1F, 0.75F );
        Metal3 = new ModelRenderer( this, 20, 73 );
        Metal3.setTextureSize( 128, 128 );
        Metal3.addBox( -4F, -0.5F, -0.5F, 8, 1, 1);
        Metal3.setRotationPoint( -18F, -6F, -0.75F );
        Metal3B = new ModelRenderer( this, 20, 73 );
        Metal3B.setTextureSize( 128, 128 );
        Metal3B.addBox( -4F, -0.5F, -0.5F, 8, 1, 1);
        Metal3B.setRotationPoint( -18F, -6F, 0.75F );
        Metal4 = new ModelRenderer( this, 23, 76 );
        Metal4.setTextureSize( 128, 128 );
        Metal4.addBox( -0.5F, -4.5F, -0.5F, 1, 9, 1);
        Metal4.setRotationPoint( -21.5F, -1F, -0.75F );
        Metal4B = new ModelRenderer( this, 23, 76 );
        Metal4B.setTextureSize( 128, 128 );
        Metal4B.addBox( -0.5F, -4.5F, -0.5F, 1, 9, 1);
        Metal4B.setRotationPoint( -21.5F, -1F, 0.75F );
        AntennaeBase = new ModelRenderer( this, 29, 77 );
        AntennaeBase.setTextureSize( 128, 128 );
        AntennaeBase.addBox( -1F, -1F, -1F, 2, 2, 2);
        AntennaeBase.setRotationPoint( 17F, 19F, 7.5F );
        Antennae = new ModelRenderer( this, 23, 76 );
        Antennae.setTextureSize( 128, 128 );
        Antennae.addBox( -0.5F, -48F, -0.5F, 1, 48, 1);
        Antennae.setRotationPoint( 17F, 19F, 7.5F );
        AntennaeTip = new ModelRenderer( this, 29, 78 );
        AntennaeTip.setTextureSize( 128, 128 );
        AntennaeTip.addBox( -1F, -0.5F, -1F, 2, 1, 2);
        AntennaeTip.setRotationPoint( -13.77691F, -17.83452F, 7.5F );
    }

   public void render(double x, double y, double z, float rotation)
    {
        GlStateManager.pushMatrix();

        GlStateManager.translate((float) x, (float) y, (float) z);

        //GlStateManager.translate((float) x + 0.5F, (float) y + 0.245f, (float) z + 0.61F);
        GlStateManager.rotate(180, 1F, 0F, 0F);
        GlStateManager.translate(0.5F, -0.245f, -0.5F);

        GlStateManager.rotate(rotation, 0F, 1F, 0F);
        //GlStateManager.translate(0f,0f,-0.145f);
        GlStateManager.scale(0.33f,0.33f,0.33f);


        float par7 = 0.0625F;

        RootNode.rotateAngleX = 0F;
        RootNode.rotateAngleY = 0F;
        RootNode.rotateAngleZ = 0F;
        RootNode.renderWithRotation(par7);

        SpeakerBox.rotateAngleX = 0F;
        SpeakerBox.rotateAngleY = 0F;
        SpeakerBox.rotateAngleZ = 0F;
        SpeakerBox.renderWithRotation(par7);

        Thingy2.rotateAngleX = 0F;
        Thingy2.rotateAngleY = 0F;
        Thingy2.rotateAngleZ = 0F;
        Thingy2.renderWithRotation(par7);

        SpeakerBoxA.rotateAngleX = 0F;
        SpeakerBoxA.rotateAngleY = 0F;
        SpeakerBoxA.rotateAngleZ = 0F;
        SpeakerBoxA.renderWithRotation(par7);

        SpeakerBoxB.rotateAngleX = 0F;
        SpeakerBoxB.rotateAngleY = 0F;
        SpeakerBoxB.rotateAngleZ = 0F;
        SpeakerBoxB.renderWithRotation(par7);

        SpeakerBoxC.rotateAngleX = 0F;
        SpeakerBoxC.rotateAngleY = 0F;
        SpeakerBoxC.rotateAngleZ = 0F;
        SpeakerBoxC.renderWithRotation(par7);

        SpeakerBoxD.rotateAngleX = 0F;
        SpeakerBoxD.rotateAngleY = 0F;
        SpeakerBoxD.rotateAngleZ = 0F;
        SpeakerBoxD.renderWithRotation(par7);

        Button1.rotateAngleX = 0F;
        Button1.rotateAngleY = 0F;
        Button1.rotateAngleZ = 0F;
        Button1.renderWithRotation(par7);

        Button2.rotateAngleX = 0F;
        Button2.rotateAngleY = 0F;
        Button2.rotateAngleZ = 0F;
        Button2.renderWithRotation(par7);

        Button3.rotateAngleX = 0F;
        Button3.rotateAngleY = 0F;
        Button3.rotateAngleZ = 0F;
        Button3.renderWithRotation(par7);

        knob.rotateAngleX = 0F;
        knob.rotateAngleY = 0F;
        knob.rotateAngleZ = 0F;
        knob.renderWithRotation(par7);

        Thingy.rotateAngleX = 0F;
        Thingy.rotateAngleY = 0F;
        Thingy.rotateAngleZ = 0F;
        Thingy.renderWithRotation(par7);

        Handle.rotateAngleX = 0F;
        Handle.rotateAngleY = 0F;
        Handle.rotateAngleZ = 0F;
        Handle.renderWithRotation(par7);

        Metal1.rotateAngleX = 0F;
        Metal1.rotateAngleY = 0F;
        Metal1.rotateAngleZ = 0F;
        Metal1.renderWithRotation(par7);

        Metal1B.rotateAngleX = 0F;
        Metal1B.rotateAngleY = 0F;
        Metal1B.rotateAngleZ = 0F;
        Metal1B.renderWithRotation(par7);

        Metal2.rotateAngleX = 0F;
        Metal2.rotateAngleY = 0F;
        Metal2.rotateAngleZ = 0F;
        Metal2.renderWithRotation(par7);

        Metal2B.rotateAngleX = 0F;
        Metal2B.rotateAngleY = 0F;
        Metal2B.rotateAngleZ = 0F;
        Metal2B.renderWithRotation(par7);

        Metal3.rotateAngleX = 0F;
        Metal3.rotateAngleY = 0F;
        Metal3.rotateAngleZ = 0F;
        Metal3.renderWithRotation(par7);

        Metal3B.rotateAngleX = 0F;
        Metal3B.rotateAngleY = 0F;
        Metal3B.rotateAngleZ = 0F;
        Metal3B.renderWithRotation(par7);

        Metal4.rotateAngleX = 0F;
        Metal4.rotateAngleY = 0F;
        Metal4.rotateAngleZ = 0F;
        Metal4.renderWithRotation(par7);

        Metal4B.rotateAngleX = 0F;
        Metal4B.rotateAngleY = 0F;
        Metal4B.rotateAngleZ = 0F;
        Metal4B.renderWithRotation(par7);

        AntennaeBase.rotateAngleX = 0F;
        AntennaeBase.rotateAngleY = 0F;
        AntennaeBase.rotateAngleZ = 0F;
        AntennaeBase.renderWithRotation(par7);

        Antennae.rotateAngleX = 0F;
        Antennae.rotateAngleY = 0F;
        Antennae.rotateAngleZ = -0.6960422F;
        Antennae.renderWithRotation(par7);

        AntennaeTip.rotateAngleX = 0F;
        AntennaeTip.rotateAngleY = 0F;
        AntennaeTip.rotateAngleZ = -0.696042F;
        AntennaeTip.renderWithRotation(par7);

        GlStateManager.popMatrix();
    }

}
