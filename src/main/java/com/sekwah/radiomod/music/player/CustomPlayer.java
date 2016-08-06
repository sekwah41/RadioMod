package com.sekwah.radiomod.music.player;

import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.SampleBuffer;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.InputStream;

/**
 * Created by on 05/08/2016.
 *
 * @author sekwah41
 */
public class CustomPlayer extends AdvancedPlayer {

    public CustomPlayer(InputStream stream) throws JavaLayerException {
        super(stream);
    }

    public CustomPlayer(InputStream stream, AudioDevice device) throws JavaLayerException
	{
		super(stream, device);
	}

	public AudioDevice getAudio(){
		return audio;
	}

	public int getFrame(){
		return audio.getPosition();
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
					out.write(output.getBuffer(), 0, output.getBufferLength());
				}
			}

			bitstream.closeFrame();
		}
		catch (RuntimeException ex)
		{
			throw new JavaLayerException("Exception decoding audio frame", ex);
		}
		return true;
	}

    public AudioDevice getAudioDevice(){
         return this.audio;
    }

	public boolean getClosed() {
		return closed;
	}
}
