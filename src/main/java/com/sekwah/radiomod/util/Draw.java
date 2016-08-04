package com.sekwah.radiomod.util;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

/**
 * Created by on 04/08/2016.
 *
 * @author GoblinBob
 */
public class Draw {
	public static void drawTexture(int xCoord, int yCoord, double minU, double minV, double maxU, double maxV, double width, double height){
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(xCoord, yCoord + height, 0D).tex(minU, minV + maxV).endVertex();
        vertexbuffer.pos(xCoord + width, yCoord + height, 0D).tex(minU + maxU, minV + maxV).endVertex();
        vertexbuffer.pos(xCoord + width, yCoord + 0.0D, 0D).tex(minU + maxU, minV).endVertex();
        vertexbuffer.pos(xCoord, yCoord + 0.0D, 0D).tex(minU, minV).endVertex();
        tessellator.draw();
    }
}
