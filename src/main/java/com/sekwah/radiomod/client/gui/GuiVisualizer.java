package com.sekwah.radiomod.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.*;

import com.sekwah.radiomod.music.MusicSource;
import com.sekwah.radiomod.util.Draw;
import com.sekwah.radiomod.util.FastFourierTransform;

import scala.actors.threadpool.Arrays;

/**
 * Created by on 04/08/2016.
 *
 * @author GoblinBob
 */
public class GuiVisualizer {
	public short[] buffer;
	public int sampleRate = 2048;
	private int minFreq = 0;
	private int maxFreq = 20000;
	private int bands = 30;
	public float[] bandValues;
	
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
		this.buffer = new short[0];
		this.sampleRate = 0;
		this.bandValues = new float[this.bands];
	}
	
	public void draw() {
		for(int i = 0; i < this.bands; i++) {
			float bandWidth = ((float)this.width)/this.bands;
			Draw.drawRect(this.x+i*bandWidth, this.y+this.height-bandValues[i]*this.height, bandWidth-2, bandValues[i]*this.height, 1, 1, 1, 1);
		}
	}

	public int getBands() {
		return this.bands;
	}

	public void setLocation(int xIn, int yIn) {
		this.x = xIn;
		this.y = yIn;
	}
	
	public void populate(short[] bufferIn) {
		this.buffer = bufferIn;
	}
	
	public void setSampleRate(int sampleRateIn) {
		this.sampleRate = sampleRateIn;
	}
	
	public void calculateBands() {
		if(this.sampleRate == 0) return;
		
		double[] output = FastFourierTransform.transform(Arrays.copyOf(this.buffer, this.sampleRate), true);
		
		if(output != null){
			for(int i = 0; i < this.bands; i++) {
				int freq = (int) (0+(512*((double)i)/this.bands));
				this.bandValues[i] = (float) Math.min(Math.max(Math.abs(output[freq]*0.003f), 0), 1);
			}
		}
	}
}
