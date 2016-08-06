package com.sekwah.radiomod.util;

import net.minecraft.client.gui.FontRenderer;

public class StringUtils {
	public static String compact(String in, FontRenderer fontRenderer, int threshold) {
		String out = in;
		int textWidth = fontRenderer.getStringWidth(in);
		
		if(textWidth >= threshold) {
			int i  = in.length()-1;
			float widthBuffer = textWidth;
			for(; i >= 0; i--) {
				widthBuffer -= fontRenderer.getCharWidth(in.charAt(i));
				if(widthBuffer <= threshold){
					break;
				}
			}
			
			out = in.substring(0, i);
			out += "...";
		}
		
		return out;
	}
}