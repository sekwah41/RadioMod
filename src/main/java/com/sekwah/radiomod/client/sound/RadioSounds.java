package com.sekwah.radiomod.client.sound;

import com.sekwah.radiomod.RadioMod;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RadioSounds {
	public static SoundEvent radio_startup_click;
	public static SoundEvent radio_startup_finish;
	public static SoundEvent radio_powerbutton_click;
	public static SoundEvent radio_powerbutton_release;
	public static SoundEvent radio_powerbutton_off;
	
	public static void registerSounds() {
		radio_startup_click = registerSound("radio.startup.click");
		radio_startup_finish = registerSound("radio.startup.finish");
		radio_powerbutton_click = registerSound("radio.powerbutton.click");
		radio_powerbutton_release = registerSound("radio.powerbutton.release");
		radio_powerbutton_off = registerSound("radio.powerbutton.off");
	}
	
	private static SoundEvent registerSound(String soundName) {
		ResourceLocation soundID = new ResourceLocation(RadioMod.modid, soundName);
		return GameRegistry.register(new SoundEvent(soundID).setRegistryName(soundID));
	}
}
