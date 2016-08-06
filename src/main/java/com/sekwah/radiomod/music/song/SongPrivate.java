package com.sekwah.radiomod.music.song;

import java.util.ArrayList;
import java.util.List;

public class SongPrivate extends Song {
	public static List<SongPrivate> privateSongCollection = new ArrayList<SongPrivate>();

	private String fileName;

	public SongPrivate(String authorIn, String titleIn, String fileName) {
		super(authorIn, titleIn);
		this.fileName = fileName;
	}

	public String getFileName() {
		return this.fileName;
	}
}
