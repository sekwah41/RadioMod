package com.sekwah.radiomod.music;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.music.song.Song;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

public class MobileManager {
	
	public static final DataParameter<Integer> PARAMETER_LOCALSTATE = EntityDataManager.<Integer>createKey(EntityPlayer.class, DataSerializers.VARINT);
	
	public static final int MOBILESTATE_OFF = -1;
	public static final int MOBILESTATE_BOOTINGUP = 0;
	public static final int MOBILESTATE_ON = 1;
	public static final int MOBILESTATE_PLAYING = 2;
	
	public static MusicSource localMusicSource = new MusicSource();
	public static int framePaused = 0;
	
	public static MusicSource getLocalMusicSource() 
	{
		return localMusicSource;
	}
	
	public static boolean isPlaying() {
		return getLocalMusicSource().getIsPlaying();
	}
	
	public static void setMobileState(int state) {
		RadioMod.proxy.setPlayerLocalRunstate(state);
	}
	
	public static int getMobileState() {
		return RadioMod.proxy.getPlayerLocalRunstate();
	}
	
	public static int getFramePaused() {
		return framePaused;
	}

	public static void setFramePaused(int framePausedIn) {
		framePaused = framePausedIn;
	}
	
	public static Song getSongPlaying() {
		return localMusicSource.getCurrentSong();
	}
}
