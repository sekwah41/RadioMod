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
	public double[] buffer;
	public int sampleRate = 2048;
	private int minFreq = 0;
	private int maxFreq = 20000;
	private int bands = 20;
	public float[] bandValues;
	public float[] bandSmoothValues;
	
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
		this.buffer = new double[0];
		this.sampleRate = 0;
		this.bandValues = new float[this.bands];
		this.bandSmoothValues = new float[this.bands];
	}
	
	public void draw() {
		for(int i = 0; i < this.bands; i++) {
			bandSmoothValues[i] += (bandValues[i]-bandSmoothValues[i])*0.5f;
			
			float bandWidth = ((float)this.width)/this.bands;
			Draw.drawRect(this.x+i*bandWidth, this.y+this.height-bandSmoothValues[i]*this.height, bandWidth-2, bandSmoothValues[i]*this.height, 1, 1, 1, 1);
		}
	}

	public int getBands() {
		return this.bands;
	}

	public void setLocation(int xIn, int yIn) {
		this.x = xIn;
		this.y = yIn;
	}
	
	public void populate(MusicSource source) {
		this.buffer = source.getFrequencyData(0);
	}
	
	public void setSampleRate(int sampleRateIn) {
		this.sampleRate = sampleRateIn;
	}
	
	public void calculateBands() {
		if(buffer != null && buffer.length > 0){
			for(int i = 0; i < this.bands; i++) {
				double f = Math.pow(((double)i)/this.bands, 3);
				int freq = (int) (0+(buffer.length*0.3*f));
				this.bandValues[i] = (float) Math.abs(buffer[freq]*0.003f);
				this.bandValues[i] *= (0.2f+freq*0.1f);
				this.bandValues[i] = Math.min(Math.max(this.bandValues[i]*0.2f, 0), 1);
			}
		}
	}
}
