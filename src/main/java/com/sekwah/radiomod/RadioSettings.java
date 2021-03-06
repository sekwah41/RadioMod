package com.sekwah.radiomod;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by on 07/08/2016.
 *
 * @author sekwah41
 */
public class RadioSettings {

    public double soundRadius;

    public double soundDropoff;

    public void setupConfigVariables(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        Property configSoundRadius = config.get(Configuration.CATEGORY_GENERAL, "soundRadius", 10);
        soundRadius = configSoundRadius.getDouble(10);
        configSoundRadius.setComment("This sets the radius for the full volume");

        Property configDropOffRadius = config.get(Configuration.CATEGORY_GENERAL, "soundDropoff", 54);
        soundDropoff = configDropOffRadius.getDouble(54);
        configSoundRadius.setComment("This sets the radius for the drop off after the full radius.");

        config.save();
    }
}
