package com.sekwah.radiomod.client.gui;

import java.io.IOException;

import com.sekwah.radiomod.blocks.RadioBlock;
import org.lwjgl.opengl.GL11;

import com.sekwah.radiomod.client.sound.RadioSounds;
import com.sekwah.radiomod.util.Draw;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
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
		new LoadingDummy("Setting up the color scheme...", 4),
		new LoadingDummy("Prepearing snazzy interface items...", 3),
		new LoadingDummy("User validation...", 2),
		new LoadingDummy("Connecting to the great music database...", 5)
	};
	
	public GuiComputer(int computerStateIn) {
		this.computerState = computerStateIn;

		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(RadioSounds.radio_startup_click, 1.0F));

		//RadioMod.instance.musicManager.playAssetsSound("IRMOST-GlitchHop");
		//RadioMod.instance.musicManager.playStreamUrl("http://stream.dancewave.online:8080/dance.mp3");
		//RadioMod.instance.musicManager.playStreamUrl("https://api.soundcloud.com/tracks/215534268/stream?client_id=23c5983facf3240a2f14515f05f34873");
	}
	
	public void initGui() {
		super.initGui();
		this.buttonList.clear();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		switch(this.computerState) {
			case RadioBlock.RUNSTATE_BOOTINGUP:
				this.currentStartupTime+=partialTicks;
				if(this.getStartupLogoProgress() >= 1){
					startupLogoFadeout+=partialTicks;
				}
				
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				this.mc.renderEngine.bindTexture(startupLogo);
				Draw.drawTexture(this.width/2-4*this.getStartupLogoScale(), this.height/2-5.5*this.getStartupLogoScale() + (1-this.getStartupLogoProgress())*11*this.getStartupLogoScale(), 0, 0.6875*(1-this.getStartupLogoProgress()), 0.5, 0.6875*(this.getStartupLogoProgress()), 8*this.getStartupLogoScale(), 11*this.getStartupLogoScale()*this.getStartupLogoProgress());
				
				if(areDummiesLoading()){
					this.drawCenteredString(this.fontRendererObj, this.loadingDummies[this.loadingProgress].getTextToDisplay(), this.width/2, (int)(this.height/2+5.5*this.getStartupLogoScale())+20, 0xffffff);
				}
				
				if(this.getStartupLogoProgress() >= 1){
					float alpha = (20-this.startupLogoFadeout)/20.0f;
					if(alpha < 0) alpha = 0;
					Draw.drawRect(0, 0, this.width, this.height, 1, 1, 1, alpha);
				}
			break;
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
			case RadioBlock.RUNSTATE_BOOTINGUP:
				if(areDummiesLoading()){
					LoadingDummy currentDummy = this.loadingDummies[this.loadingProgress];
					if(!currentDummy.isLoaded()){
						currentDummy.decreaseLoadingTime();
					}else{
						this.loadingProgress++;
						if(this.areDummiesLoading())
							Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(RadioSounds.radio_startup_click, 1.0F+(1+this.loadingProgress)*0.05f));
						else
							Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(RadioSounds.radio_startup_finish, 1.0F));
					}
				}else{
					if(this.startupLogoFadeout > 50) {
						this.computerState = RadioBlock.RUNSTATE_ON;
					}
				}
			break;
			case RadioBlock.RUNSTATE_ON:
			break;
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	public float getStartupLogoScale() {
		return (float)((this.width*0.015f)*(1+(1-Math.pow((1-this.startupLogoFadeout/50)*4, 2)/16.0f)*0.3f));
	}
	
	public float getStartupLogoProgress() {
		if(this.loadingProgress == 0){
			return 1/11.0f;
		}else if(this.loadingProgress == 1){
			return 3/11.0f;
		}else if(this.loadingProgress == 2){
			return 6/11.0f;
		}else if(this.loadingProgress == 3){
			return 7/11.0f;
		}else if(this.loadingProgress == 4){
			return 9/11.0f;
		}
		return 1;
	}
	
	public boolean areDummiesLoading() {
		return this.loadingProgress < this.loadingDummies.length;
	}
}
