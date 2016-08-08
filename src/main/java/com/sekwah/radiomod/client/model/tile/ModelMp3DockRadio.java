package com.sekwah.radiomod.client.model.tile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelMp3DockRadio extends ModelBase
{
    ModelRenderer RootNode;
    ModelRenderer Mp3Player;
    ModelRenderer SpeakerBox;
    ModelRenderer Knob1;
    ModelRenderer Knob2;
    ModelRenderer Knob3;
    ModelRenderer Knob4;
    ModelRenderer Knob5;
    ModelRenderer button1;
    ModelRenderer button2;
    ModelRenderer button3;
    ModelRenderer Foot1;
    ModelRenderer Foot2;
    ModelRenderer Foot3;
    ModelRenderer Foot4;

    public ModelMp3DockRadio()
    {
        this( 0.0f );
    }

    public ModelMp3DockRadio( float par1 )
    {
        RootNode = new ModelRenderer( this, 16, 16 );
        RootNode.setTextureSize( 256, 64 );
        RootNode.addBox( 0F, 0F, 0F, 0, 0, 0);
        RootNode.setRotationPoint( 0F, 6.5F, 0F );
        Mp3Player = new ModelRenderer( this, 0, 0 );
        Mp3Player.setTextureSize( 256, 64 );
        Mp3Player.addBox( -3.5F, -1F, -5.5F, 7, 2, 11);
        Mp3Player.setRotationPoint( 0F, 0.5F, -3.5F );
        SpeakerBox = new ModelRenderer( this, 80, 8 );
        SpeakerBox.setTextureSize( 256, 64 );
        SpeakerBox.addBox( -22F, -7.5F, -11F, 44, 15, 22);
        SpeakerBox.setRotationPoint( 0F, 13.5F, 0F );
        Knob1 = new ModelRenderer( this, 82, 15 );
        Knob1.setTextureSize( 256, 64 );
        Knob1.addBox( -1.5F, -2F, -1F, 3, 3, 2);
        Knob1.setRotationPoint( 0F, 13.5F, -12F );
        Knob2 = new ModelRenderer( this, 82, 20 );
        Knob2.setTextureSize( 256, 64 );
        Knob2.addBox( -1F, -0.5F, -1F, 2, 1, 2);
        Knob2.setRotationPoint( 0F, 11.5F, -11.9F );
        Knob3 = new ModelRenderer( this, 82, 20 );
        Knob3.setTextureSize( 256, 64 );
        Knob3.addBox( -1F, -0.5F, -1F, 2, 1, 2);
        Knob3.setRotationPoint( 0F, 14.5F, -11.9F );
        Knob4 = new ModelRenderer( this, 82, 20 );
        Knob4.setTextureSize( 256, 64 );
        Knob4.addBox( -1F, -0.5F, -1F, 2, 1, 2);
        Knob4.setRotationPoint( 1.5F, 13F, -11.9F );
        Knob5 = new ModelRenderer( this, 82, 20 );
        Knob5.setTextureSize( 256, 64 );
        Knob5.addBox( -1F, -0.5F, -1F, 2, 1, 2);
        Knob5.setRotationPoint( -1.5F, 13F, -11.9F );
        button1 = new ModelRenderer( this, 90, 26 );
        button1.setTextureSize( 256, 64 );
        button1.addBox( -1F, -0.5F, -0.5F, 2, 1, 1);
        button1.setRotationPoint( -2.25F, 9.5F, -11F );
        button2 = new ModelRenderer( this, 90, 26 );
        button2.setTextureSize( 256, 64 );
        button2.addBox( -1F, -0.5F, -0.5F, 2, 1, 1);
        button2.setRotationPoint( -0.05000019F, 9.5F, -11F );
        button3 = new ModelRenderer( this, 90, 26 );
        button3.setTextureSize( 256, 64 );
        button3.addBox( -1F, -0.5F, -0.5F, 2, 1, 1);
        button3.setRotationPoint( 2.15F, 9.5F, -11F );
        Foot1 = new ModelRenderer( this, 64, 20 );
        Foot1.setTextureSize( 256, 64 );
        Foot1.addBox( -1F, -1F, -1F, 2, 4, 2);
        Foot1.setRotationPoint( -20F, 21F, -9F );
        Foot2 = new ModelRenderer( this, 64, 20 );
        Foot2.setTextureSize( 256, 64 );
        Foot2.addBox( -1F, -1F, -1F, 2, 4, 2);
        Foot2.setRotationPoint( 20F, 21F, -9F );
        Foot3 = new ModelRenderer( this, 64, 20 );
        Foot3.setTextureSize( 256, 64 );
        Foot3.addBox( -1F, -1F, -1F, 2, 4, 2);
        Foot3.setRotationPoint( -20F, 21F, 9F );
        Foot4 = new ModelRenderer( this, 64, 20 );
        Foot4.setTextureSize( 256, 64 );
        Foot4.addBox( -1F, -1F, -1F, 2, 4, 2);
        Foot4.setRotationPoint( 20F, 21F, 9F );
    }

    public void render(double x, double y, double z, float rotation)
    {
        GlStateManager.pushMatrix();

        GlStateManager.translate((float) x, (float) y, (float) z);

        //GlStateManager.translate((float) x + 0.5F, (float) y + 0.245f, (float) z + 0.61F);
        GlStateManager.rotate(180, 1F, 0F, 0F);
        GlStateManager.translate(0.5F, -0.245f*2.0f, -0.5F);

        GlStateManager.rotate(rotation, 0F, 1F, 0F);
        //GlStateManager.translate(0f,0f,-0.145f);
        GlStateManager.scale(0.32f,0.32f,0.32f);


        float par1 = 0.0625F;
        RootNode.rotateAngleX = 0F;
        RootNode.rotateAngleY = 0F;
        RootNode.rotateAngleZ = 0F;
        RootNode.renderWithRotation(par1);

        Mp3Player.rotateAngleX = 1.570796F;
        Mp3Player.rotateAngleY = 0F;
        Mp3Player.rotateAngleZ = 0F;
        Mp3Player.renderWithRotation(par1);

        SpeakerBox.rotateAngleX = 0F;
        SpeakerBox.rotateAngleY = 0F;
        SpeakerBox.rotateAngleZ = 0F;
        SpeakerBox.renderWithRotation(par1);

        Knob1.rotateAngleX = 0F;
        Knob1.rotateAngleY = 0F;
        Knob1.rotateAngleZ = 0F;
        Knob1.renderWithRotation(par1);

        Knob2.rotateAngleX = 0F;
        Knob2.rotateAngleY = 0F;
        Knob2.rotateAngleZ = 0F;
        Knob2.renderWithRotation(par1);

        Knob3.rotateAngleX = 0F;
        Knob3.rotateAngleY = 0F;
        Knob3.rotateAngleZ = 0F;
        Knob3.renderWithRotation(par1);

        Knob4.rotateAngleX = 0F;
        Knob4.rotateAngleY = 0F;
        Knob4.rotateAngleZ = 1.570796F;
        Knob4.renderWithRotation(par1);

        Knob5.rotateAngleX = 0F;
        Knob5.rotateAngleY = 0F;
        Knob5.rotateAngleZ = 1.570796F;
        Knob5.renderWithRotation(par1);

        button1.rotateAngleX = 0F;
        button1.rotateAngleY = 0F;
        button1.rotateAngleZ = 0F;
        button1.renderWithRotation(par1);

        button2.rotateAngleX = 0F;
        button2.rotateAngleY = 0F;
        button2.rotateAngleZ = 0F;
        button2.renderWithRotation(par1);

        button3.rotateAngleX = 0F;
        button3.rotateAngleY = 0F;
        button3.rotateAngleZ = 0F;
        button3.renderWithRotation(par1);

        Foot1.rotateAngleX = -0.1745329F;
        Foot1.rotateAngleY = 1.337902E-09F;
        Foot1.rotateAngleZ = 0.1745329F;
        Foot1.renderWithRotation(par1);

        Foot2.rotateAngleX = -0.1745329F;
        Foot2.rotateAngleY = 3.320594E-10F;
        Foot2.rotateAngleZ = -0.1745329F;
        Foot2.renderWithRotation(par1);

        Foot3.rotateAngleX = 0.1745329F;
        Foot3.rotateAngleY = -9.81679E-10F;
        Foot3.rotateAngleZ = 0.1745329F;
        Foot3.renderWithRotation(par1);

        Foot4.rotateAngleX = 0.1745329F;
        Foot4.rotateAngleY = -1.439599E-09F;
        Foot4.rotateAngleZ = -0.1745329F;
        Foot4.renderWithRotation(par1);

        GlStateManager.popMatrix();

    }

}
