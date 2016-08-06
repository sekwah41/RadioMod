package com.sekwah.radiomod.client.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import com.sekwah.radiomod.util.Draw;
import com.sekwah.radiomod.util.StringUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiListSongsEntry implements GuiListExtended.IGuiListEntry
{
    private static final Logger LOGGER = LogManager.getLogger();
    private final Minecraft client;
    private final GuiComputer guiComputer;
    private final GuiListSongs containingListSel;
    private final String songAuthor;
    private final String songTitle;
    private float hoverAnimation = 0;
    private final int index;
    
    public GuiListSongsEntry(GuiListSongs listPacksSelIn, String authorIn, String titleIn)
    {
        this.containingListSel = listPacksSelIn;
        this.guiComputer = listPacksSelIn.getComputerGui();
        this.client = Minecraft.getMinecraft();
        this.index = this.containingListSel.getSize();
        
        this.songAuthor = authorIn;
        this.songTitle = titleIn;
    }

    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected)
    {
    	if(y < this.guiComputer.getScreenY()-3 ||
    	   y > this.guiComputer.getScreenY()+this.guiComputer.getScreenHeight())
    	return;
    	
    	Draw.drawXGradient(x, y+slotHeight-1, listWidth, 1, 1, 1, 1, 1, 1, 1, 1, 0);
    	if(isSelected){
    		hoverAnimation += (1-hoverAnimation)*0.5f*this.containingListSel.partialTicks;
    	}else{
    		hoverAnimation += (0-hoverAnimation)*0.5f*this.containingListSel.partialTicks;
    	}
    	
    	Draw.drawXGradient(x, y, 40, slotHeight-1, 1, 1, 1, 0.2f*this.hoverAnimation, 1, 1, 1, 0);
    		//Minecraft.getMinecraft().renderEngine.bindTexture(ClientProxy.texture_NULL);
    		//GL11.glColor4f(0,0,0,0.5f);
    		
    		/*if(slotIndex != BendsPack.currentPack) Draw.rectangle(x,y,200, 64);
    		else								   Draw.rectangle_xgradient(x,y,200, 64, new Color(0.0f,0.0f,0.0f,0.5f), new Color(0.1f,1.0f,0.1f,0.5f));
    		GL11.glColor4f(1,1,1,1.0f);
    		this.client.fontRendererObj.drawString(displayName,(int)(this.moBendsMenuGui.width+this.moBendsMenuGui.presetWindowState*-250+10)+5,y+5, 0xffffff);
    		
    		GL11.glPushMatrix();
    			GL11.glTranslatef((int)(this.moBendsMenuGui.width+this.moBendsMenuGui.presetWindowState*-250+10)+5,y+5+10,0);
    			GL11.glScalef(0.5f,0.5f,0.5f);
    			this.client.fontRendererObj.drawString("By " + author, 0, 0, 0x777777);
    			
    			for(int s = 0;s < text.size();s++){
    				this.client.fontRendererObj.drawString(text.get(s),0,20+s*10, 0xffffff);
	    		}
    		GL11.glPopMatrix();*/
    	String songName = this.songTitle;
    	songName = StringUtils.compact(songName, this.client.fontRendererObj, listWidth-40);
    	this.client.fontRendererObj.drawString(TextFormatting.BOLD+songName, x+2, y+3, 0xffffff);
    	String authorText = this.songAuthor;
    	authorText = StringUtils.compact(authorText, this.client.fontRendererObj, listWidth-40);
    	this.client.fontRendererObj.drawString(authorText, x+2, y+16, 0xdddddd);
    	
        GlStateManager.color(1.0F, 1.0F, 1.0F, this.hoverAnimation);
        this.client.renderEngine.bindTexture(this.guiComputer.computerBg);
		Draw.drawTexture(x+listWidth-30+this.hoverAnimation*10, y+5, 0, 1-16F/256, 16F/256, 16F/256, 16, 16);
    }

    /**
     * Called when the mouse is clicked within this entry. Returning true means that something within this entry was
     * clicked and the list should not be dragged.
     *  
     * @param mouseX Scaled X coordinate of the mouse on the entire screen
     * @param mouseY Scaled Y coordinate of the mouse on the entire screen
     * @param mouseEvent The button on the mouse that was pressed
     * @param relativeX Relative X coordinate of the mouse within this entry.
     * @param relativeY Relative Y coordinate of the mouse within this entry.
     */
    public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY)
    {
        this.containingListSel.selectSong(slotIndex);
        
        return true;
    }

    /**
     * Fired when the mouse button is released. Arguments: index, x, y, mouseEvent, relativeX, relativeY
     */
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY)
    {
    }

    public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_)
    {
    }

	public int getIndex() {
		return index;
	}
}