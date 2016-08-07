package com.sekwah.radiomod.music.song;

import java.util.ArrayList;
import java.util.List;

public class SongBuiltIn extends Song {
	public static List<SongBuiltIn> builtInSongCollection = new ArrayList<SongBuiltIn>();

	private String fileName;

	public SongBuiltIn(String authorIn, String titleIn, String fileName) {
		super(authorIn, titleIn);
		this.fileName = fileName;
	}

	public String getFileName() {
		return this.fileName;
	}
	
	public static void registerBuiltInSongs() {
		registerSong("Iwo Plaza", "Alive", "Iwo Plaza - Alive");
		registerSong("Iwo Plaza", "Calling", "Iwo Plaza - Calling");
		registerSong("Iwo Plaza", "New Horizon", "Iwo Plaza - New Horizon");
		registerSong("Iwo Plaza", "Drowning", "Iwo Plaza - Drowning");
		registerSong("Iwo Plaza", "Spectrum", "Iwo Plaza - Spectrum");
	}
	
	public static void registerSong(String authorIn, String titleIn, String fileNameIn) {
		builtInSongCollection.add(new SongBuiltIn(authorIn, titleIn, fileNameIn));
	}
}
