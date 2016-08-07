package com.sekwah.radiomod.music.song;

import net.minecraft.util.ResourceLocation;

public class Song {
	private int id;
	
	private String title;
	private String author;
	private ResourceLocation coverArt;
	
	public Song(int idIn, String authorIn, String titleIn) {
		this.id = idIn;
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

	public int getID() {
		return this.id;
	}
}
