package com.sekwah.radiomod.generic.guihandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandlerRadio implements IGuiHandler {
	
	public static final int GUIID_COMPUTER = 0;
	public static final int GUIID_MP3PLAYER = 1;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
			case GUIID_COMPUTER:
				return null;
			case GUIID_MP3PLAYER:
				return null;
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
			case GUIID_COMPUTER:
				return null;
			case GUIID_MP3PLAYER:
				return null;
		}
		
		return null;
	}

}
