package com.sekwah.radiomod.music;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.music.song.SongBuiltIn;
import com.sekwah.radiomod.music.song.TimingData;
import com.sekwah.radiomod.music.song.TrackingData;
import com.sekwah.radiomod.network.packets.client.ClientPlaySongBroadcastPacket;
import com.sekwah.radiomod.network.packets.client.ClientStopSongBroadcastPacket;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by on 07/08/2016.
 *
 * @author sekwah41
 */
public class MusicTracker {

    public Map<String,TrackingData> trackingMap = new HashMap<String,TrackingData>();

    public void updateTracks(){
        for(Map.Entry<String, TrackingData> entry: this.trackingMap.entrySet()){
            String uuid = entry.getKey();
            TrackingData data = entry.getValue();
            if(data.type != TrackingData.STREAM){
                RadioMod.logger.info(data.currentTick++);
                if(data.maxTicks < data.currentTick){
                    this.trackingMap.remove(uuid);
                    // TODO get playlist if there is one.
                }
            }
        }
    }

    public void stopSong(String uuid){
        if(trackingMap.containsKey(uuid)){
            // TODO add code to send stop packet.
            RadioMod.packetNetwork.sendToAll(new ClientStopSongBroadcastPacket(uuid));
            trackingMap.remove(uuid);
        }
    }

    public void playSource(String uuid, TrackingData data){
        TimingData timingData;
        switch (data.type){
            case TrackingData.BUILTIN:
                timingData = this.getTimingDataBuiltIn(SongBuiltIn.builtInSongCollection.get(Integer.parseInt(data.source)).getFileName());
                if(timingData == null){
                    return;
                }
                data.maxTicks = (int) (((timingData.frames * timingData.ms_per_frame) / 1000) * 20);
                break;
            case TrackingData.ONLINE:
                try {
                    timingData = this.getTimingData(new URL(data.source).openConnection().getInputStream());
                    if(timingData == null){
                        return;
                    }
                    data.maxTicks = (int) (((timingData.frames * timingData.ms_per_frame) / 1000) * 20);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                break;
            case TrackingData.STREAM:
                try {
                    timingData = this.getTimingData(new URL(data.source).openConnection().getInputStream());
                    if(timingData == null){
                        return;
                    }
                    data.maxTicks = (int) (((timingData.frames * timingData.ms_per_frame) / 1000) * 20);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                break;
        }

        this.stopSong(uuid);
        // TODO send packet to users
        this.trackingMap.put(uuid, data);

        RadioMod.packetNetwork.sendToAll(new ClientPlaySongBroadcastPacket(uuid,data));
    }

    public TimingData getTimingData(String file){
        try {
            return getTimingData(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            RadioMod.logger.info("Could not read music data.");
            e.printStackTrace();
        }
        return null;
    }

    public TimingData getTimingDataBuiltIn(String file){
        return getTimingData(this.getClass().getResourceAsStream("/assets/radiomod/sounds/songs/" + file + ".mp3"));
    }

    public TimingData getTimingData(InputStream inputStream){
        Bitstream bitstream = new Bitstream(inputStream);
        try {
            Header head = bitstream.readFrame();
            int max_frames = head.max_number_of_frames(inputStream.available());
            inputStream.close();
            return new TimingData(head.ms_per_frame(), max_frames);
        } catch (BitstreamException | IOException e) {
            RadioMod.logger.info("Error getting timing data.");
            e.printStackTrace();
        }
        return null;
    }

    /*public void sendPlayPacket(){
        RadioMod.packetNetwork.sendToServer(new ServerBootupComputerPacket(true, this.tileEntity.getPos()));

    }*/

}
