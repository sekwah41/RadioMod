package com.sekwah.radiomod.client.gui;

import com.google.common.collect.Lists;
import com.sekwah.radiomod.music.song.Song;
import com.sekwah.radiomod.music.song.SongPrivate;
import com.sekwah.radiomod.util.Draw;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Mouse;

@SideOnly(Side.CLIENT)
public class GuiListMobileSongs extends GuiListExtended
{
    private static final Logger LOGGER = LogManager.getLogger();
    private final GuiMobile guiMobile;
    private final List<GuiListMobileSongsEntry> entries = Lists.<GuiListMobileSongsEntry>newArrayList();
    /** Index to the currently selected pack */
    private int selectedIdx = -1;
    public float partialTicks = 0;
    
    public GuiListMobileSongs(GuiMobile guiMobileIn, Minecraft clientIn, int widthIn, int heightIn, int topIn, int bottomIn)
    {
        super(clientIn, widthIn, heightIn, topIn, bottomIn, 28);
        this.guiMobile = guiMobileIn;
        this.left = (int) guiMobileIn.getScreenX();
        this.right = this.left + widthIn;
    }
    
    public void fillOut(List<? extends Song> songList) {
    	entries.clear();
    	if(songList == null) return;
    	for(int i = 0;i < songList.size();i++){
    		Song song = songList.get(i);
    		entries.add(new GuiListMobileSongsEntry(this, song.getAuthor(), song.getTitle()));
    	}
    }
    
    public GuiListMobileSongsEntry getListEntry(int index)
    {
        return (GuiListMobileSongsEntry)this.entries.get(index);
    }

    protected int getSize()
    {
        return this.entries.size();
    }

    protected int getScrollBarX()
    {
        return this.getX()+(int)this.guiMobile.getScreenWidth()-16;
    }
    
    public int getListWidth()
    {
        return (int) (this.getMobileGui().getScreenWidth()-20);
    }
    
    public void selectSong(int idx)
    {
        this.selectedIdx = idx;
        this.guiMobile.selectSong(this.getSelectedSongEntry());
    }
    
    protected boolean isSelected(int slotIndex)
    {
        return slotIndex == this.selectedIdx;
    }

    public GuiListMobileSongsEntry getSelectedSongEntry()
    {
        return this.selectedIdx >= 0 && this.selectedIdx < this.getSize() ? this.getListEntry(this.selectedIdx) : null;
    }

    public GuiMobile getMobileGui()
    {
        return this.guiMobile;
    }
    
    public void drawScreen(int mouseXIn, int mouseYIn, float partialTicks)
    {
    	this.partialTicks = partialTicks;
    	
        if (this.visible)
        {
            this.mouseX = mouseXIn;
            this.mouseY = mouseYIn;
            this.drawBackground();
            int i = this.getScrollBarX();
            int j = i + 6;
            this.bindAmountScrolled();
            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            Tessellator tessellator = Tessellator.getInstance();
            VertexBuffer vertexbuffer = tessellator.getBuffer();
            // Forge: background rendering moved into separate method.
            int k = this.getX();
            int l = this.getY() + 4 - (int)this.amountScrolled;

            this.drawSelectionBox(k, l, mouseXIn, mouseYIn);
            
            float shadowOpacity = Math.min(this.amountScrolled*0.02f, 1);
            Draw.drawYGradient(this.getMobileGui().getScreenX(), this.getY(), this.width, 20, 0, 0, 0, shadowOpacity*0.3f, 0, 0, 0, 0);
            
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
            GlStateManager.disableAlpha();
            GlStateManager.shadeModel(7425);
            GlStateManager.disableTexture2D();
            int i1 = 4;
            int j1 = this.getMaxScroll();
            
            if (j1 > 0)
            {
                int k1 = this.getY()-this.top + (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();
                k1 = MathHelper.clamp_int(k1, this.getY()-this.top + 32, this.getY()-this.top + this.bottom - this.top - 8);
                int l1 = this.getY()-this.top + (int)this.amountScrolled * (this.bottom - this.top - k1) / j1 + this.top;

                if (l1 < this.getY())
                {
                    l1 = this.getY();
                }

                vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                vertexbuffer.pos((double)i, (double)this.getY()+this.height, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
                vertexbuffer.pos((double)j, (double)this.getY()+this.height, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
                vertexbuffer.pos((double)j, (double)this.getY(), 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
                vertexbuffer.pos((double)i, (double)this.getY(), 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
                tessellator.draw();
                vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                vertexbuffer.pos((double)i, (double)(l1 + k1), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
                vertexbuffer.pos((double)j, (double)(l1 + k1), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
                vertexbuffer.pos((double)j, (double)l1, 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
                vertexbuffer.pos((double)i, (double)l1, 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
                tessellator.draw();
                vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                vertexbuffer.pos((double)i, (double)(l1 + k1 - 1), 0.0D).tex(0.0D, 1.0D).color(192, 192, 192, 255).endVertex();
                vertexbuffer.pos((double)(j - 1), (double)(l1 + k1 - 1), 0.0D).tex(1.0D, 1.0D).color(192, 192, 192, 255).endVertex();
                vertexbuffer.pos((double)(j - 1), (double)l1, 0.0D).tex(1.0D, 0.0D).color(192, 192, 192, 255).endVertex();
                vertexbuffer.pos((double)i, (double)l1, 0.0D).tex(0.0D, 0.0D).color(192, 192, 192, 255).endVertex();
                tessellator.draw();
            }

            this.renderDecorations(mouseXIn, mouseYIn);
            GlStateManager.enableTexture2D();
            GlStateManager.shadeModel(7424);
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
        }
    }
    protected void drawSelectionBox(int p_148120_1_, int p_148120_2_, int mouseXIn, int mouseYIn)
    {
        int i = this.getSize();
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();

        for (int j = 0; j < i; ++j)
        {
            int k = p_148120_2_ + j * this.slotHeight;
            int l = this.slotHeight;

            if (k > this.bottom || k + l < this.top)
            {
                //this.func_178040_a(j, p_148120_1_, k);
            }
            
            this.drawSlot(j, p_148120_1_, k, l, mouseXIn, mouseYIn);
        }
    }
    
    protected void drawContainerBackground(Tessellator tessellator)
    {
    }
    
    protected void overlayBackground(int startY, int endY, int startAlpha, int endAlpha)
    {
    }
    
    public int getX() {
    	return (int) (this.getMobileGui().getScreenX()+10);
    }
    
    public int getY() {
    	return (int) (this.getMobileGui().getScreenY()+18);
    }
    
    public int getSelectedIndex() {
    	return this.selectedIdx;
    }
    
    public int getSlotIndexFromScreenCoords(int p_148124_1_, int p_148124_2_)
    {
        int i = this.getX();
        int j = this.getX() + this.getListWidth();
        int k = p_148124_2_ - this.top - this.headerPadding + (int)this.amountScrolled - 4;
        int l = k / this.slotHeight;
        return p_148124_1_ < this.getScrollBarX() && p_148124_1_ >= i && p_148124_1_ <= j && l >= 0 && k >= 0 && l < this.getSize() ? l : -1;
    }
}