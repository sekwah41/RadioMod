package com.sekwah.radiomod.client.gui;

/**
 * Created by on 04/08/2016.
 *
 * @author GoblinBob
 */
public class LoadingDummy {
	private String displayText;
	private float loadingTime;
	
	public LoadingDummy(String displayTextIn, float loadingTimeIn) {
		this.displayText = displayTextIn;
		this.loadingTime = loadingTimeIn;
	}
	
	public String getTextToDisplay() {
		return this.displayText;
	}
	
	public float getLoadingTime() {
		return this.loadingTime;
	}
	
	public void decreaseLoadingTime() {
		this.loadingTime--;
	}
	
	public boolean isLoaded() {
		return this.loadingTime <= 0;
	}
}
