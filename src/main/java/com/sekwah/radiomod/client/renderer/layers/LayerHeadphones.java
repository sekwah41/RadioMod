package com.sekwah.radiomod.client.renderer.layers;

import com.mojang.authlib.GameProfile;
import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.client.model.layers.ModelHeadphones;
import com.sekwah.radiomod.music.MobileManager;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerHeadphones implements LayerRenderer<EntityLivingBase>
{
	public static ResourceLocation texture = new ResourceLocation(RadioMod.modid, "textures/layers/headphones.png");
	
    private final ModelHeadphones modelHeadphones;
    private final ModelRenderer modelHead;

    public LayerHeadphones(ModelRenderer modelHeadIn)
    {
    	this.modelHead = modelHeadIn;
        this.modelHeadphones = new ModelHeadphones();
    }

    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
    	if(entitylivingbaseIn.getDataManager().get(MobileManager.PARAMETER_LOCALSTATE) != MobileManager.MOBILESTATE_PLAYING) return;
    	
    	GlStateManager.pushMatrix();
	    	if (entitylivingbaseIn.isSneaking())
	        {
	            GlStateManager.translate(0.0F, 0.2F, 0.0F);
	        }
    		
	    	this.modelHead.postRender(0.0625F);
	    	Minecraft.getMinecraft().renderEngine.bindTexture(texture);
	    	//GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	    	
	    	GlStateManager.scale(0.5f, 0.5f, 0.5f);
	    	GlStateManager.translate(0.0f, -27.0f*scale, 0.0f);
	    	
	    	this.modelHeadphones.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    	GlStateManager.popMatrix();
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}