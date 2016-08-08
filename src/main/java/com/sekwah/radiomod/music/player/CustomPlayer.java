package com.sekwah.radiomod.music.player;

import com.sekwah.radiomod.RadioMod;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.SampleBuffer;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by on 05/08/2016.
 *
 * @author sekwah41
 */
public class CustomPlayer extends AdvancedPlayer {
	private float volume = 0.4f;

	private int currentFrame = 1;

	private Header firstFrameHeader;

	/**
	 * Raw data for the frame
	 */
	private short[] rawData;

	public CustomPlayer(InputStream stream) throws JavaLayerException {
		super(stream);
		this.firstFrameHeader = this.bitstream.readFrame();
		/*try {
			RadioMod.logger.info(firstFrameHeader.max_number_of_frames(stream.available()));
		} catch (IOException e) {
			RadioMod.logger.info("Error getting stream size");
			e.printStackTrace();
		}*/
		this.bitstream.unreadFrame();
	}

	public CustomPlayer(InputStream stream, float volume) throws JavaLayerException
	{
		this(stream);
		this.volume = volume;
	}

	public void setVolume(float volume){
		this.volume = volume;
	}

	public AudioDevice getAudio(){
		return audio;
	}

	public int getFrame(){
		return currentFrame;
	}

	/**
	 * Plays a range of MPEG audio frames
	 * @param start	The first frame to play
	 * @return true if the last frame was played, or false if there are more frames.
	 */
	public void playFrom(final int start) throws JavaLayerException
	{
		boolean ret = true;
		int offset = start;
		while (offset-- > 0 && ret) {
			ret = skipFrame();
			currentFrame++;
		}
		play();
	}

	/**
	 * skips over a single frame
	 * @return false	if there are no more frames to decode, true otherwise.
	 */
	protected boolean skipFrame() throws JavaLayerException
	{
		Header h = bitstream.readFrame();
		if (h == null) return false;
		bitstream.closeFrame();
		return true;
	}

	public Header getFirstFrameHeader(){
		return firstFrameHeader;
	}

	/**
	 * Decodes a single frame.
	 *
	 * @return true if there are no more frames to decode, false otherwise.
	 */
	protected boolean decodeFrame() throws JavaLayerException
	{
		try
		{
			AudioDevice out = audio;
			if (out == null) return false;

			Header h = bitstream.readFrame();
			if (h == null) return false;

			// sample buffer set when decoder constructed
			SampleBuffer output = (SampleBuffer) decoder.decodeFrame(h, bitstream);

			synchronized (this)
			{
				out = audio;
				if(out != null)
				{
					short[] samples = output.getBuffer();

					for (int samp = 0; samp < samples.length; samp++)
					{
						// TODO get volumes from minecraft to effect this.
						short newValue = (short) (samples[samp] * this.volume);
						samples[samp] = newValue;
					}
					this.rawData = samples;
					out.write(samples, 0, output.getBufferLength());
				}
			}

			bitstream.closeFrame();
		}
		catch (RuntimeException ex)
		{
			throw new JavaLayerException("Exception decoding audio frame", ex);
		}
		currentFrame++;
		//RadioMod.logger.info(currentFrame);
		return true;
	}

	public short[] getRawData(){
		return rawData;
	}

	public AudioDevice getAudioDevice(){
		return this.audio;
	}

	public boolean getClosed() {
		return closed;
	}
}
