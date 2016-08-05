package com.sekwah.radiomod.client.sound;

import com.sekwah.radiomod.RadioMod;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RadioSounds {
	public static SoundEvent radio_startup_click;
	public static SoundEvent radio_startup_finish;
	
	public static void registerSounds() {
		radio_startup_click = registerSound("radio.startup.click");
		radio_startup_finish = registerSound("radio.startup.finish");
	}
	
	private static SoundEvent registerSound(String soundName) {
		ResourceLocation soundID = new ResourceLocation(RadioMod.modid, soundName);
		return GameRegistry.register(new SoundEvent(soundID).setRegistryName(soundID));
	}
}
