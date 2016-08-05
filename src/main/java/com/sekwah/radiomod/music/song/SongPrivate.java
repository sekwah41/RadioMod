package com.sekwah.radiomod.music.song;

import java.util.ArrayList;
import java.util.List;

public class SongPrivate extends Song {
	public static List<SongPrivate> privateSongCollection = new ArrayList<SongPrivate>();
	
	public SongPrivate(String authorIn, String titleIn) {
		super(authorIn, titleIn);
	}
}
