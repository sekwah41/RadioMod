package com.sekwah.radiomod.client.gui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.blocks.TestRadio;
import com.sekwah.radiomod.client.sound.RadioSounds;
import com.sekwah.radiomod.util.Draw;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

/**
 * Created by on 04/08/2016.
 *
 * @author GoblinBob
 */
public class GuiComputer extends GuiScreen {
	public static ResourceLocation startupLogo;
	
	private int computerState;
	private float startupSequence = 0;
	private int loadingProgress = 0;
	private float currentStartupTime = 0;
	private float startupLogoFadeout = 0;
	private LoadingDummy[] loadingDummies = new LoadingDummy[]{
		new LoadingDummy("Loading assets...", 4),
		new LoadingDummy("Prepearing snazzy interface items...", 3),
		new LoadingDummy("User validation...", 2),
		new LoadingDummy("Connection to the great music database...", 5)
	};
	
	public GuiComputer(int computerStateIn) {
		this.computerState = computerStateIn;
		
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(RadioSounds.radio_startup, 1.0F));

		//RadioMod.instance.musicManager.playAssetsSound("IRMOST-GlitchHop");
		//RadioMod.instance.musicManager.playStreamUrl("http://stream.dancewave.online:8080/dance.mp3");
		RadioMod.instance.musicManager.playStreamUrl("https://cf-media.sndcdn.com/EOqjn9HTjKlM.128.mp3?Policy=eyJTdGF0ZW1lbnQiOlt7IlJlc291cmNlIjoiKjovL2NmLW1lZGlhLnNuZGNkbi5jb20vRU9xam45SFRqS2xNLjEyOC5tcDMiLCJDb25kaXRpb24iOnsiRGF0ZUxlc3NUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE0NzAzNjE3MjN9fX1dfQ__&Signature=rvQk0qciorE~Hl~d~ohYOWMhk9momlEhbNsCru1qIVDs~pxVQorhgsQVIFk1B11o0Al3DNFc6KB88deoe5BspoUpXhDAYEnJ6HY34MU2zcrKja4N46lFNytPv2RdSG53SLLdt5AhgtoEoEluwRliMrP4tMt7av-Q-jJKIOjpj35tdvlP8Mf488~LHl~QmFFeVTnl~RPbHnl2AuWH8fceVD-fZXg96M~oA8vzi-CtPdvjlLwCQmWlbYXOL6U7AfSyg4ENi8rsa4oBsR5eBGZRwIccrQpiXK5KDGRYALeY9XOEcruFItCsBub4q~FxtO2G1u3NFIKoJ2ixbVThu8tZ~g__&Key-Pair-Id=APKAJAGZ7VMH2PFPW6UQ");
	}
	
	public void initGui() {
		super.initGui();
		this.buttonList.clear();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		this.currentStartupTime+=partialTicks;
		if(this.getStartupLogoProgress() >= 1){
			startupLogoFadeout+=partialTicks;
		}
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		this.mc.renderEngine.bindTexture(startupLogo);
		Draw.drawTexture((int)(this.width/2-4*this.getStartupLogoScale()), (int)(this.height/2-5.5*this.getStartupLogoScale() + (1-this.getStartupLogoProgress())*11*this.getStartupLogoScale()), 0, 0.6875*(1-this.getStartupLogoProgress()), 0.5, 0.6875*(this.getStartupLogoProgress()), 8*this.getStartupLogoScale(), 11*this.getStartupLogoScale()*this.getStartupLogoProgress());
		
		if(areDummiesLoading()){
			this.drawCenteredString(this.fontRendererObj, this.loadingDummies[this.loadingProgress].getTextToDisplay(), this.width/2, (int)(this.height/2+5.5*this.getStartupLogoScale())+20, 0xffffff);
		}
		
		if(this.getStartupLogoProgress() >= 1){
			
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		
		switch(this.computerState) {
			case TestRadio.RUNSTATE_BOOTINGUP:
				if(!areDummiesLoading()) break;
				LoadingDummy currentDummy = this.loadingDummies[this.loadingProgress];
				if(!currentDummy.isLoaded()){
					currentDummy.decreaseLoadingTime();
				}else{
					this.loadingProgress++;
				}
			break;
			case TestRadio.RUNSTATE_ON:
			break;
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	public float getStartupLogoScale() {
		return 15;
	}
	
	public float getStartupLogoProgress() {
		if(this.currentStartupTime < 7){
			return 0.1f;
		}else if(this.currentStartupTime < 20){
			return 0.24f;
		}else if(this.currentStartupTime < 27){
			return 0.5f;
		}else if(this.currentStartupTime < 37){
			return 0.7f;
		}else if(this.currentStartupTime < 47){
			return 0.84f;
		}
		return 1;
	}
	
	public boolean areDummiesLoading() {
		return this.loadingProgress < this.loadingDummies.length;
	}
}
