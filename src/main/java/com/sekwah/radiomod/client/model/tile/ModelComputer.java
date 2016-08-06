package com.sekwah.radiomod.client.model.tile;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelComputer extends ModelBase
{
    ModelRenderer RootNode;
    ModelRenderer mouse;
    ModelRenderer mouse4;
    ModelRenderer Keyboard;
    ModelRenderer CPU;
    ModelRenderer Monitor1Bottom;
    ModelRenderer Monitor1Top;
    ModelRenderer Monitor1Right;
    ModelRenderer Monitor1Left;
    ModelRenderer Monitor1FrontTop;
    ModelRenderer Monitor1FrontBottom;
    ModelRenderer Monitor1FrontLeft;
    ModelRenderer Monitor1FrontRight;
    ModelRenderer Monitor1Back;
    ModelRenderer Monitor1Screen;
    ModelRenderer Monitor1Bottom2;
    ModelRenderer Monitor1Bottom3;
    ModelRenderer Monitor1Bottom3Button1;
    ModelRenderer Monitor1Bottom3Button2;
    ModelRenderer Monitor1Bottom3Button3;
    ModelRenderer Monitor1Bottom3Button4;
    ModelRenderer Monitor2;
    ModelRenderer Monitor2B;
    ModelRenderer Mp3Dock;
    ModelRenderer Mp3Player;

    public ModelComputer()
    {
        this( 0.0f );
    }

    public ModelComputer( float par1 )
    {
        RootNode = new ModelRenderer( this, 16, 16 );
        RootNode.setTextureSize( 256, 256 );
        RootNode.addBox( 0F, 0F, 0F, 0, 0, 0);
        RootNode.setRotationPoint( -5F, 9.5F, 9F );
        mouse = new ModelRenderer( this, 2, 24 );
        mouse.setTextureSize( 256, 256 );
        mouse.addBox( -3.5F, -1F, -5.5F, 7, 2, 11);
        mouse.setRotationPoint( 30F, 23F, -8.5F );
        mouse4 = new ModelRenderer( this, 84, 18 );
        mouse4.setTextureSize( 256, 256 );
        mouse4.addBox( -0.5F, -0.5F, -1F, 1, 1, 2);
        mouse4.setRotationPoint( 30F, 22F, -6.5F );
        Keyboard = new ModelRenderer( this, 0, 0 );
        Keyboard.setTextureSize( 256, 256 );
        Keyboard.addBox( -27.5F, -1F, -10F, 55, 2, 20);
        Keyboard.setRotationPoint( -7F, 23F, -8F );
        CPU = new ModelRenderer( this, 6, 26 );
        CPU.setTextureSize( 256, 256 );
        CPU.addBox( -29.5F, -7.5F, -19.5F, 59, 15, 39);
        CPU.setRotationPoint( -7F, 16.5F, 25F );
        Monitor1Bottom = new ModelRenderer( this, 37, 82 );
        Monitor1Bottom.setTextureSize( 256, 256 );
        Monitor1Bottom.addBox( -23.5F, -0.5F, -10F, 47, 1, 20);
        Monitor1Bottom.setRotationPoint( -7F, 0.5F, 16F );
        Monitor1Top = new ModelRenderer( this, 37, 80 );
        Monitor1Top.setTextureSize( 256, 256 );
        Monitor1Top.addBox( -23.5F, -1F, -10F, 47, 2, 20);
        Monitor1Top.setRotationPoint( -7F, -37F, 16F );
        Monitor1Right = new ModelRenderer( this, 183, 2 );
        Monitor1Right.setTextureSize( 256, 256 );
        Monitor1Right.addBox( -1F, -18F, -10F, 2, 36, 20);
        Monitor1Right.setRotationPoint( 15.5F, -18F, 16F );
        Monitor1Left = new ModelRenderer( this, 183, 2 );
        Monitor1Left.setTextureSize( 256, 256 );
        Monitor1Left.addBox( -1F, -18F, -10F, 2, 36, 20);
        Monitor1Left.setRotationPoint( -29.5F, -18F, 16F );
        Monitor1FrontTop = new ModelRenderer( this, 59, 107 );
        Monitor1FrontTop.setTextureSize( 256, 256 );
        Monitor1FrontTop.addBox( -21.5F, -2F, -1F, 43, 4, 2);
        Monitor1FrontTop.setRotationPoint( -7F, -34.7F, 8F );
        Monitor1FrontBottom = new ModelRenderer( this, 59, 107 );
        Monitor1FrontBottom.setTextureSize( 256, 256 );
        Monitor1FrontBottom.addBox( -21.5F, -2F, -1F, 43, 4, 2);
        Monitor1FrontBottom.setRotationPoint( -7F, -1.299999F, 7.999997F );
        Monitor1FrontLeft = new ModelRenderer( this, 59, 107 );
        Monitor1FrontLeft.setTextureSize( 256, 256 );
        Monitor1FrontLeft.addBox( -18.5F, -2F, -1F, 37, 4, 2);
        Monitor1FrontLeft.setRotationPoint( -27.23331F, -17.93024F, 7.997986F );
        Monitor1FrontRight = new ModelRenderer( this, 59, 107 );
        Monitor1FrontRight.setTextureSize( 256, 256 );
        Monitor1FrontRight.addBox( -18.5F, -2F, -1F, 37, 4, 2);
        Monitor1FrontRight.setRotationPoint( 13.2F, -17.3F, 7.999998F );
        Monitor1Back = new ModelRenderer( this, 97, 117 );
        Monitor1Back.setTextureSize( 256, 256 );
        Monitor1Back.addBox( -21.5F, -18F, -1F, 43, 36, 2);
        Monitor1Back.setRotationPoint( -7F, -18F, 25F );
        Monitor1Screen = new ModelRenderer( this, 10, 120 );
        Monitor1Screen.setTextureSize( 256, 256 );
        Monitor1Screen.addBox( -18.5F, -15F, -1F, 37, 30, 2);
        Monitor1Screen.setRotationPoint( -7F, -18F, 9F );
        Monitor1Bottom2 = new ModelRenderer( this, 4, 216 );
        Monitor1Bottom2.setTextureSize( 256, 256 );
        Monitor1Bottom2.addBox( -23.5F, -2F, -10F, 47, 4, 20);
        Monitor1Bottom2.setRotationPoint( -7F, 3F, 16F );
        Monitor1Bottom3 = new ModelRenderer( this, 126, 206 );
        Monitor1Bottom3.setTextureSize( 256, 256 );
        Monitor1Bottom3.addBox( -21F, -2F, -9F, 42, 4, 18);
        Monitor1Bottom3.setRotationPoint( -7F, 7F, 17F );
        Monitor1Bottom3Button1 = new ModelRenderer( this, 14, 89 );
        Monitor1Bottom3Button1.setTextureSize( 256, 256 );
        Monitor1Bottom3Button1.addBox( -0.5F, -1.5F, -1F, 1, 3, 2);
        Monitor1Bottom3Button1.setRotationPoint( -7F, 5.5F, 8F );
        Monitor1Bottom3Button2 = new ModelRenderer( this, 14, 89 );
        Monitor1Bottom3Button2.setTextureSize( 256, 256 );
        Monitor1Bottom3Button2.addBox( -0.5F, -1.5F, -1F, 1, 3, 2);
        Monitor1Bottom3Button2.setRotationPoint( -2F, 5.5F, 8F );
        Monitor1Bottom3Button3 = new ModelRenderer( this, 14, 89 );
        Monitor1Bottom3Button3.setTextureSize( 256, 256 );
        Monitor1Bottom3Button3.addBox( -0.5F, -1.5F, -1F, 1, 3, 2);
        Monitor1Bottom3Button3.setRotationPoint( 3F, 5.5F, 8F );
        Monitor1Bottom3Button4 = new ModelRenderer( this, 14, 94 );
        Monitor1Bottom3Button4.setTextureSize( 256, 256 );
        Monitor1Bottom3Button4.addBox( -1F, -1F, -1F, 2, 2, 2);
        Monitor1Bottom3Button4.setRotationPoint( 9F, 2.5F, 6.25F );
        Monitor2 = new ModelRenderer( this, 7, 158 );
        Monitor2.setTextureSize( 256, 256 );
        Monitor2.addBox( -18F, -18F, -10F, 36, 36, 20);
        Monitor2.setRotationPoint( -7F, -15F, 35F );
        Monitor2B = new ModelRenderer( this, 98, 118 );
        Monitor2B.setTextureSize( 256, 256 );
        Monitor2B.addBox( -14F, -10.5F, -8F, 28, 21, 16);
        Monitor2B.setRotationPoint( -7F, -1.5F, 34F );
        Mp3Dock = new ModelRenderer( this, 0, 43 );
        Mp3Dock.setTextureSize( 256, 256 );
        Mp3Dock.addBox( -6F, -1F, -5F, 12, 2, 10);
        Mp3Dock.setRotationPoint( 31F, 23F, 11F );
        Mp3Player = new ModelRenderer( this, 4, 103 );
        Mp3Player.setTextureSize( 256, 256 );
        Mp3Player.addBox( -3.5F, -1F, -5.5F, 7, 2, 11);
        Mp3Player.setRotationPoint( 31F, 16.5F, 11.5F );
    }

    public void render(double x, double y, double z, float rotation)
    {
        GlStateManager.pushMatrix();

        GlStateManager.translate((float) x, (float) y, (float) z);

        //GlStateManager.translate((float) x + 0.5F, (float) y + 0.245f, (float) z + 0.61F);
        GlStateManager.rotate(180, 1F, 0F, 0F);
        GlStateManager.translate(0.5F, -0.245f, -0.5F);

        GlStateManager.rotate(rotation, 0F, 1F, 0F);
        GlStateManager.translate(0f,0f,-0.145f);
        GlStateManager.scale(0.16f,0.16f,0.16f);

        float par1 = 0.0625F;
        RootNode.rotateAngleX = 0F;
        RootNode.rotateAngleY = 0F;
        RootNode.rotateAngleZ = 0F;
        RootNode.renderWithRotation(par1);

        mouse.rotateAngleX = 0F;
        mouse.rotateAngleY = 0F;
        mouse.rotateAngleZ = 0F;
        mouse.renderWithRotation(par1);

        mouse4.rotateAngleX = 0F;
        mouse4.rotateAngleY = 0F;
        mouse4.rotateAngleZ = 0F;
        mouse4.renderWithRotation(par1);

        Keyboard.rotateAngleX = 0F;
        Keyboard.rotateAngleY = 0F;
        Keyboard.rotateAngleZ = 0F;
        Keyboard.renderWithRotation(par1);

        CPU.rotateAngleX = 0F;
        CPU.rotateAngleY = 0F;
        CPU.rotateAngleZ = 0F;
        CPU.renderWithRotation(par1);

        Monitor1Bottom.rotateAngleX = 0F;
        Monitor1Bottom.rotateAngleY = 0F;
        Monitor1Bottom.rotateAngleZ = 0F;
        Monitor1Bottom.renderWithRotation(par1);

        Monitor1Top.rotateAngleX = 0F;
        Monitor1Top.rotateAngleY = 0F;
        Monitor1Top.rotateAngleZ = 0F;
        Monitor1Top.renderWithRotation(par1);

        Monitor1Right.rotateAngleX = 0F;
        Monitor1Right.rotateAngleY = 0F;
        Monitor1Right.rotateAngleZ = 0F;
        Monitor1Right.renderWithRotation(par1);

        Monitor1Left.rotateAngleX = 0F;
        Monitor1Left.rotateAngleY = 0F;
        Monitor1Left.rotateAngleZ = 0F;
        Monitor1Left.renderWithRotation(par1);

        Monitor1FrontTop.rotateAngleX = 0.4363323F;
        Monitor1FrontTop.rotateAngleY = 0F;
        Monitor1FrontTop.rotateAngleZ = 0F;
        Monitor1FrontTop.renderWithRotation(par1);

        Monitor1FrontBottom.rotateAngleX = -0.4363323F;
        Monitor1FrontBottom.rotateAngleY = 0F;
        Monitor1FrontBottom.rotateAngleZ = 0F;
        Monitor1FrontBottom.renderWithRotation(par1);

        Monitor1FrontLeft.rotateAngleX = -2.05739E-08F;
        Monitor1FrontLeft.rotateAngleY = -0.4363323F;
        Monitor1FrontLeft.rotateAngleZ = -1.570796F;
        Monitor1FrontLeft.renderWithRotation(par1);

        Monitor1FrontRight.rotateAngleX = -1.028695E-07F;
        Monitor1FrontRight.rotateAngleY = 0.4363325F;
        Monitor1FrontRight.rotateAngleZ = -1.570796F;
        Monitor1FrontRight.renderWithRotation(par1);

        Monitor1Back.rotateAngleX = 0F;
        Monitor1Back.rotateAngleY = 0F;
        Monitor1Back.rotateAngleZ = 0F;
        Monitor1Back.renderWithRotation(par1);

        Monitor1Screen.rotateAngleX = 0F;
        Monitor1Screen.rotateAngleY = 0F;
        Monitor1Screen.rotateAngleZ = 0F;
        Monitor1Screen.renderWithRotation(par1);

        Monitor1Bottom2.rotateAngleX = 0F;
        Monitor1Bottom2.rotateAngleY = 0F;
        Monitor1Bottom2.rotateAngleZ = 0F;
        Monitor1Bottom2.renderWithRotation(par1);

        Monitor1Bottom3.rotateAngleX = 0F;
        Monitor1Bottom3.rotateAngleY = 0F;
        Monitor1Bottom3.rotateAngleZ = 0F;
        Monitor1Bottom3.renderWithRotation(par1);

        Monitor1Bottom3Button1.rotateAngleX = 0F;
        Monitor1Bottom3Button1.rotateAngleY = 0F;
        Monitor1Bottom3Button1.rotateAngleZ = 0F;
        Monitor1Bottom3Button1.renderWithRotation(par1);

        Monitor1Bottom3Button2.rotateAngleX = 0F;
        Monitor1Bottom3Button2.rotateAngleY = 0F;
        Monitor1Bottom3Button2.rotateAngleZ = 0F;
        Monitor1Bottom3Button2.renderWithRotation(par1);

        Monitor1Bottom3Button3.rotateAngleX = 0F;
        Monitor1Bottom3Button3.rotateAngleY = 0F;
        Monitor1Bottom3Button3.rotateAngleZ = 0F;
        Monitor1Bottom3Button3.renderWithRotation(par1);

        Monitor1Bottom3Button4.rotateAngleX = 0F;
        Monitor1Bottom3Button4.rotateAngleY = 0F;
        Monitor1Bottom3Button4.rotateAngleZ = 0F;
        Monitor1Bottom3Button4.renderWithRotation(par1);

        Monitor2.rotateAngleX = 0F;
        Monitor2.rotateAngleY = 0F;
        Monitor2.rotateAngleZ = 0F;
        Monitor2.renderWithRotation(par1);

        Monitor2B.rotateAngleX = 0F;
        Monitor2B.rotateAngleY = 0F;
        Monitor2B.rotateAngleZ = 0F;
        Monitor2B.renderWithRotation(par1);

        Mp3Dock.rotateAngleX = 0F;
        Mp3Dock.rotateAngleY = 0F;
        Mp3Dock.rotateAngleZ = 0F;
        Mp3Dock.renderWithRotation(par1);

        Mp3Player.rotateAngleX = 1.570796F;
        Mp3Player.rotateAngleY = 0F;
        Mp3Player.rotateAngleZ = 0F;
        Mp3Player.renderWithRotation(par1);

        GL11.glPopMatrix();

    }

}
