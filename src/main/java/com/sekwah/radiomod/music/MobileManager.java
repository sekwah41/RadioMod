package com.sekwah.radiomod.music;

public class MobileManager {
	public static final int MOBILESTATE_OFF = -1;
	public static final int MOBILESTATE_BOOTINGUP = 0;
	public static final int MOBILESTATE_ON = 1;
	public static final int MOBILESTATE_PLAYING = 2;
	
	public static int mobileState = MOBILESTATE_OFF;
	public static MusicSource localMusicSource = new MusicSource();
	
	public static MusicSource getLocalMusicSource() 
	{
		return localMusicSource;
	}
	
	public static boolean isPlaying() {
		return getLocalMusicSource().getIsPlaying();
	}
	
	public static void setMobileState(int state) {
		mobileState = state;
	}
	
	public static int getMobileState() {
		return mobileState;
	}
}
