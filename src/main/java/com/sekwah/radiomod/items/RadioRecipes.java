package com.sekwah.radiomod.items;

import com.sekwah.radiomod.blocks.RadioBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RadioRecipes {
	public static void registerRecipes() {
		GameRegistry.addRecipe(new ItemStack(RadioBlocks.RADIOBLOCK, 1), "s s", "xxx", "iri", 's', new ItemStack(Items.STICK), 'x', new ItemStack(Blocks.PLANKS), 'i', new ItemStack(Items.IRON_INGOT), 'r', new ItemStack(Items.REDSTONE));
		GameRegistry.addRecipe(new ItemStack(RadioBlocks.SPEAKERBLOCK, 1), "xrx", "iwi", "xxx", 'w', new ItemStack(Blocks.WOOL), 'x', new ItemStack(Blocks.PLANKS), 'i', new ItemStack(Items.IRON_INGOT), 'r', new ItemStack(Items.REDSTONE));
		GameRegistry.addRecipe(new ItemStack(RadioItems.MOBILE, 1), "iGi", "iGi", "iRi", 'G', new ItemStack(Blocks.GLASS_PANE), 'R', new ItemStack(Blocks.REDSTONE_BLOCK), 'i', new ItemStack(Items.IRON_INGOT));
		GameRegistry.addRecipe(new ItemStack(RadioItems.JACK, 1), "e", "r", "i", 'e', new ItemStack(Items.ENDER_PEARL), 'r', new ItemStack(Items.REDSTONE), 'i', new ItemStack(Items.IRON_INGOT));
	}
}
