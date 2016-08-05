package com.sekwah.radiomod.util;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

/**
 * Created by on 04/08/2016.
 *
 * @author GoblinBob
 */
public class Draw {
	public static void drawTexture(double xCoord, double yCoord, double minU, double minV, double maxU, double maxV, double width, double height){
		GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(xCoord, yCoord + height, 0D).tex(minU, minV + maxV).endVertex();
        vertexbuffer.pos(xCoord + width, yCoord + height, 0D).tex(minU + maxU, minV + maxV).endVertex();
        vertexbuffer.pos(xCoord + width, yCoord + 0.0D, 0D).tex(minU + maxU, minV).endVertex();
        vertexbuffer.pos(xCoord, yCoord + 0.0D, 0D).tex(minU, minV).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }
	
	public static void drawRect(double xCoord, double yCoord, double width, double height, float r, float g, float b, float a) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vertexbuffer.pos(xCoord, yCoord + height, 0D).color(r, g, b, a).endVertex();
        vertexbuffer.pos(xCoord + width, yCoord + height, 0D).color(r, g, b, a).endVertex();
        vertexbuffer.pos(xCoord + width, yCoord + 0.0D, 0D).color(r, g, b, a).endVertex();
        vertexbuffer.pos(xCoord, yCoord + 0.0D, 0D).color(r, g, b, a).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
	}
	
	public static void drawXGradient(double xCoord, double yCoord, double width, double height, float r, float g, float b, float a, float r1, float g1, float b1, float a1) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vertexbuffer.pos(xCoord, yCoord + height, 0D).color(r, g, b, a).endVertex();
        vertexbuffer.pos(xCoord + width, yCoord + height, 0D).color(r1, g1, b1, a1).endVertex();
        vertexbuffer.pos(xCoord + width, yCoord + 0.0D, 0D).color(r1, g1, b1, a1).endVertex();
        vertexbuffer.pos(xCoord, yCoord + 0.0D, 0D).color(r, g, b, a).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
	}
	
	public static void drawYGradient(double xCoord, double yCoord, double width, double height, float r, float g, float b, float a, float r1, float g1, float b1, float a1) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vertexbuffer.pos(xCoord, yCoord + height, 0D).color(r1, g1, b1, a1).endVertex();
        vertexbuffer.pos(xCoord + width, yCoord + height, 0D).color(r1, g1, b1, a1).endVertex();
        vertexbuffer.pos(xCoord + width, yCoord + 0.0D, 0D).color(r, g, b, a).endVertex();
        vertexbuffer.pos(xCoord, yCoord + 0.0D, 0D).color(r, g, b, a).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
	}
}
