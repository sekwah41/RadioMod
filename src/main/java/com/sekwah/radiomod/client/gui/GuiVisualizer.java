package com.sekwah.radiomod.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.sekwah.radiomod.music.MusicSource;
import com.sekwah.radiomod.util.Draw;

/**
 * Created by on 04/08/2016.
 *
 * @author GoblinBob
 */
public class GuiVisualizer {
	
	public float[] buffer;
	private int minFreq = 0;
	private int maxFreq = 20000;
	private int bands = 10;
	
	private int x;
	private int y;
	private int width;
	private int height;

	// http://uk.mathworks.com/help/signal/ref/spectrogram.html?requestedDomain=uk.mathworks.com
	// Read over this stuff and some other spectrogram stuff, that may be the solution to visualising.
	
	public GuiVisualizer(int xIn, int yIn, int widthIn, int heightIn) {
		this.x = xIn;
		this.y = yIn;
		this.width = widthIn;
		this.height = heightIn;
		this.buffer = new float[this.bands];
	}
	
	public void draw() {
		for(int i = 0; i < this.bands; i++) {
			float bandWidth = ((float)this.width)/this.bands;
			Draw.drawRect(this.x+i*bandWidth, this.y+this.height-buffer[i]*this.height, bandWidth-2, buffer[i]*this.height, 1, 1, 1, 1);
		}
	}

	public int getBands() {
		return this.bands;
	}

	public void setLocation(int xIn, int yIn) {
		this.x = xIn;
		this.y = yIn;
	}
}
