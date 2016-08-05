package com.sekwah.radiomod.generic.guihandler.interfaces;

/**
 * Created by on 05/08/2016.
 *
 * @author sekwah41
 */
public interface IGuiDataLoaded {

    /**
     * Whenever data is requested once the response is returned invoke this method.
     *
     * @return return if the data has been loaded.
     */
    boolean dataLoaded();

}