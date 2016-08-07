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

    public static double soundRadius;

    public static double soundDropoff;

    public static void setupConfigVariables(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        Property configExperimentalFirstPersonMode = config.get(Configuration.CATEGORY_GENERAL, "soundRadius", 0);
        soundRadius = configExperimentalFirstPersonMode.getDouble(0);
        configExperimentalFirstPersonMode.setComment("This sets the first person mode. 0 = Enabled, 1 = Disabled, 2 = Jutsu Toggle(on when casting justsus)");

        config.save();
    }
}
