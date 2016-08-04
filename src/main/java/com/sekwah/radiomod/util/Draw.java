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
        vertexbuffer.pos((double)(xCoord + 0.0F), (double)(yCoord + (float)height), (double)0).tex(minU, minV + maxV).endVertex();
        vertexbuffer.pos((double)(xCoord + width), (double)(yCoord + (float)height), (double)0).tex(minU + maxU, minV + maxV).endVertex();
        vertexbuffer.pos((double)(xCoord + width), (double)(yCoord + 0.0F), (double)0).tex(minU + maxU, minV).endVertex();
        vertexbuffer.pos((double)(xCoord + 0.0F), (double)(yCoord + 0.0F), (double)0).tex(minU, minV).endVertex();
        tessellator.draw();
    }
}
