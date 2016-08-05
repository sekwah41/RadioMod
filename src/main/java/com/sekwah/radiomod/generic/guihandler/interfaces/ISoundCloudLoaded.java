package com.sekwah.radiomod.generic.guihandler.interfaces;

import com.sekwah.radiomod.onlineservices.soundcloud.SoundCloud;

/**
 * Created by on 05/08/2016.
 *
 * @author sekwah41
 */
public interface ISoundCloudLoaded {

    /**
     * Whenever data is requested once the response is returned invoke this method.
     *
     * TODO add return type so that the data can be loaded to the gui.
     *
     */
    void dataLoaded(boolean success, SoundCloud.LoadedType type);


}
