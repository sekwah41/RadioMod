package com.sekwah.radiomod.client.sound;

import com.sekwah.radiomod.RadioMod;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RadioSounds {
	public static SoundEvent radio_startup;
	
	public static void registerSounds() {
		radio_startup = registerSound("radio.startup");
	}
	
	private static SoundEvent registerSound(String soundName) {
		ResourceLocation soundID = new ResourceLocation(RadioMod.modid, soundName);
		return GameRegistry.register(new SoundEvent(soundID).setRegistryName(soundID));
	}
}
