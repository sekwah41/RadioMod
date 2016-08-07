package com.sekwah.radiomod.generic.guihandler;

import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;
import com.sekwah.radiomod.client.gui.GuiComputer;
import com.sekwah.radiomod.client.gui.GuiMobile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandlerRadio implements IGuiHandler {
	
	public static final int GUIID_COMPUTER = 0;
	public static final int GUIID_MOBILE = 1;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
			case GUIID_COMPUTER:
				return null;
			case GUIID_MOBILE:
				return null;
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntityRadio tileEntity = (TileEntityRadio) world.getTileEntity(new BlockPos(x, y, z));
		
		switch(ID) {
			case GUIID_COMPUTER:
				return new GuiComputer(tileEntity);
			case GUIID_MOBILE:
				return new GuiMobile(tileEntity);
		}
		
		return null;
	}

}
