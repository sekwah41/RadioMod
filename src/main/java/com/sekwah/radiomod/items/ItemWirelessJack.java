package com.sekwah.radiomod.items;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.generic.guihandler.GuiHandlerRadio;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

/**
 * Created by on 06/08/2016.
 *
 * @author sekwah41
 */
public class ItemWirelessJack extends Item {
	
	public ItemWirelessJack() {
		this.setCreativeTab(CreativeTabRadio.creativeTabRadio);
	}
	
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {

        return new ActionResult(EnumActionResult.PASS, itemStackIn);
    }

}
