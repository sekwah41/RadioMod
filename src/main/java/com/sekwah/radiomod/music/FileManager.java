package com.sekwah.radiomod.music;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.music.song.SongPrivate;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.DecoderException;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.SampleBuffer;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.Player;
import net.minecraft.client.Minecraft;

/**
 * Created by on 05/08/2016.
 *
 * @author GoblinBob
 */
public class FileManager {
	public static File privateSongsDir;
	
	public static void loadPrivateSongs() {
		File[] files = privateSongsDir.listFiles();
		
		SongPrivate.privateSongCollection.clear();
		
		if(files != null){
			for(File file : files){
				if(file.getAbsolutePath().endsWith(".mp3")){
					SongPrivate.privateSongCollection.add(loadSongFromFile(file));
				}
			}
		}
	}
	
	public static SongPrivate loadSongFromFile(File file) {
		String author = null;
		String title = file.getName().replace(".mp3", "");
		
		if(author == null && title.contains(" - ")) {
			String[] parts = title.split("-");
			author = parts[0].trim();
			title = parts[1].trim();
		}
		
		return new SongPrivate(author, title);
	}
	
	public static void preInit() {
		privateSongsDir = new File(RadioMod.instance.modFolder,"privatesongs");
		privateSongsDir.mkdir();
		
		loadPrivateSongs();
		System.out.println("PRIVATE_SONGS_LIST:"+SongPrivate.privateSongCollection.toString());
		for(int i = 0; i < SongPrivate.privateSongCollection.size(); i++) {
			System.out.println(SongPrivate.privateSongCollection.get(i).getFullDisplayTitle());
		}
	}
}