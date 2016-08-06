package com.sekwah.radiomod.music.song;

import net.minecraft.util.ResourceLocation;

public class Song {
	private String title;
	private String author;
	private String fileName;
	private ResourceLocation coverArt;
	
	public Song(String authorIn, String titleIn, String fileName) {
		this.author = authorIn;
		this.title = titleIn;
		this.fileName = fileName;
	}
	
	public boolean doesHaveAuthor() {
		return this.author != null;
	}
	
	public String getFullDisplayTitle() {
		return this.doesHaveAuthor() ? this.author+" - "+this.title : this.title; 
	}
	
	public String getAuthor() {
		return this.author;
	}

	public String getFileName() {
		return this.fileName;
	}
	
	public String getTitle() {
		return this.title;
	}
}
