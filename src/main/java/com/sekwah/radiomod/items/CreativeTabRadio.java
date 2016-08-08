package com.sekwah.radiomod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class CreativeTabRadio extends CreativeTabs
{
	public static CreativeTabRadio creativeTabRadio = new CreativeTabRadio("radiomod");
	
	CreativeTabRadio(String par2Str)
    {
        super(par2Str);
    }

	@Override
	public Item getTabIconItem()
    {
        return RadioItems.MOBILE;
    }
	
	@SideOnly(Side.CLIENT)
    public String getTabLabel()
    {
        return "radiomod";
    }
}