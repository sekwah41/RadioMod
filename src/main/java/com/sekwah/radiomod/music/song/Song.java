package com.sekwah.radiomod.music.song;

import net.minecraft.util.ResourceLocation;

public class Song {
	private String title;
	private String author;
	private ResourceLocation coverArt;
	
	public Song(String authorIn, String titleIn) {
		this.author = authorIn;
		this.title = titleIn;
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
	
	public String getTitle() {
		return this.title;
	}
}
