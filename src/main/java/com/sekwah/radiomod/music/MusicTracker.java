package com.sekwah.radiomod.music;

import com.sekwah.radiomod.music.song.TrackingData;

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
            data.currentTicks++;
            if(data.maxTicks < data.currentTicks){
                this.trackingMap.remove(uuid);
                // TODO get playlist if there is one.
            }
        }
    }

    public void stopSong(String uuid){
        if(trackingMap.containsKey(uuid)){
            // TODO add code to send stop packet.
            trackingMap.remove(uuid);
        }
    }

    public void playSource(String uuid, TrackingData data){
        this.stopSong(uuid);
        // TODO send packet to users
        this.trackingMap.put(uuid, data);
    }

    /*public void sendPlayPacket(){
        RadioMod.packetNetwork.sendToServer(new ServerBootupComputerPacket(true, this.tileEntity.getPos()));

    }*/

}
