package com.sekwah.radiomod.client.gui;

import java.io.IOException;

import com.sekwah.radiomod.blocks.RadioBlock;
import org.lwjgl.opengl.GL11;

import com.sekwah.radiomod.client.sound.RadioSounds;
import com.sekwah.radiomod.music.song.Song;
import com.sekwah.radiomod.music.song.SongPrivate;
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
	protected int bgWidth = 256;
    protected int bgHeight = 187;
	public static ResourceLocation computerBg;
	public static ResourceLocation startupLogo;
	public GuiVisualizer guiVisualizer;
	
	private int computerState;
	private float startupSequence;
	private int loadingProgress;
	private float currentStartupTime;
	private float startupLogoFadeout;
	private LoadingDummy[] loadingDummies;
	
	public boolean powerButtonClicked = false;
	public boolean greenLightWhiteFlash = true;
	public float songTitleScroll = 0;
	
	public GuiComputer(int computerStateIn) {
		this.computerState = computerStateIn;

		//RadioMod.instance.musicManager.playAssetsSound("IRMOST-GlitchHop");
		//RadioMod.instance.musicManager.playStreamUrl("http://stream.dancewave.online:8080/dance.mp3");
		//RadioMod.instance.musicManager.playStreamUrl("https://api.soundcloud.com/tracks/215534268/stream?client_id=23c5983facf3240a2f14515f05f34873");
	}
	
	public void initGui() {
		super.initGui();
		this.buttonList.clear();
		
		this.guiVisualizer = new GuiVisualizer((int)this.getScreenCenterX()-40, (int)this.getScreenCenterY()-30, (int)80, (int)60);
	}
	
	public void bootupComputer() {
		this.computerState = RadioBlock.RUNSTATE_BOOTINGUP;
		
		this.greenLightWhiteFlash = true;
		this.startupSequence = 0;
		this.loadingProgress = 0;
		this.currentStartupTime = 0;
		this.startupLogoFadeout = 0;
		
		this.loadingDummies = new LoadingDummy[]{
			new LoadingDummy("Loading assets...", 4),
			new LoadingDummy("Setting up the color scheme...", 4),
			new LoadingDummy("Prepearing snazzy interface items...", 3),
			new LoadingDummy("User validation...", 2),
			new LoadingDummy("Connecting to the great music database...", 5)
		};
		
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(RadioSounds.radio_startup_click, 1.0F));
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(RadioSounds.radio_powerbutton_release, 1.0F));
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		this.drawDefaultBackground();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		this.mc.renderEngine.bindTexture(computerBg);
		Draw.drawTexture(this.width/2-this.bgWidth/2, this.height/2-this.bgHeight/2, 0, 0, 1, ((float)this.bgHeight)/256, this.bgWidth, this.bgHeight);
		
		if(this.powerButtonClicked) {
			Draw.drawTexture(this.width/2-this.bgWidth/2+205, this.height/2-this.bgHeight/2+167, 0, 192F/256, 16F/256, 16F/256, 16, 16);
		}
		
		if(this.computerState != RadioBlock.RUNSTATE_OFF) {
			if(this.greenLightWhiteFlash){
				Draw.drawRect(this.width/2-this.bgWidth/2+205, this.height/2-this.bgHeight/2+161, 16, 5, 1, 1, 1, 1);
				this.greenLightWhiteFlash = false;
			}else{
				Draw.drawTexture(this.width/2-this.bgWidth/2+205, this.height/2-this.bgHeight/2+161, 0, 187F/256, 16F/256, 5F/256, 16, 5);
			}
		}
		
		switch(this.computerState) {
			case RadioBlock.RUNSTATE_BOOTINGUP:
				this.currentStartupTime+=partialTicks;
				if(this.getStartupLogoProgress() >= 1){
					startupLogoFadeout+=partialTicks;
				}
				
				this.mc.renderEngine.bindTexture(startupLogo);
				Draw.drawTexture(this.getScreenCenterX()-4*this.getStartupLogoScale(), this.getScreenCenterY()-5.5*this.getStartupLogoScale() + (1-this.getStartupLogoProgress())*11*this.getStartupLogoScale(), 0, 0.6875*(1-this.getStartupLogoProgress()), 0.5, 0.6875*(this.getStartupLogoProgress()), 8*this.getStartupLogoScale(), 11*this.getStartupLogoScale()*this.getStartupLogoProgress());
				
				if(areDummiesLoading()){
					this.drawCenteredString(this.fontRendererObj, this.loadingDummies[this.loadingProgress].getTextToDisplay(), this.width/2, (int)(this.height/2+5.5*this.getStartupLogoScale())+10, 0xffffff);
				}
				
				if(this.getStartupLogoProgress() >= 1){
					float alpha = (20-this.startupLogoFadeout)/20.0f;
					if(alpha < 0) alpha = 0;
					Draw.drawRect(this.getScreenX(), this.getScreenY(), this.getScreenWidth(), this.getScreenHeight(), 1, 1, 1, alpha);
				}
			break;
			case RadioBlock.RUNSTATE_PLAYING:
				for(int i = 0; i < this.guiVisualizer.getBands(); i++) {
					float ticks = Minecraft.getMinecraft().thePlayer.ticksExisted+partialTicks;
					this.guiVisualizer.buffer[i] = Math.min(Math.abs((float) Math.sin(ticks*0.1f + i*0.2)), 1);
				}
				this.guiVisualizer.draw();
				
				if(getCurrentPlayedSong() != null) {
					String songTitle = this.getCurrentPlayedSong().getFullDisplayTitle();
					int titleLength = songTitle.length();
					int titleWidth = this.fontRendererObj.getStringWidth(songTitle+"       ");
					
					if(titleWidth > 180) {
						float offset = 0;
						songTitle += "       "+songTitle+"       ";
						for(int i = 0; i < titleLength; i++) {
							if(offset - this.songTitleScroll < 0) {
								offset += this.fontRendererObj.getCharWidth(songTitle.charAt(0));
								songTitle = songTitle.substring(1, songTitle.length());
							}else{
								break;
							}
						}
						float newLength = this.fontRendererObj.getStringWidth(songTitle);
						System.out.println(newLength);
						for(int i = songTitle.length()-1; i >= 0; i--) {
							if(newLength > 180) {
								newLength -= this.fontRendererObj.getCharWidth(songTitle.charAt(i));
								songTitle = songTitle.substring(0, songTitle.length()-1);
							}else{
								break;
							}
						}
						
						this.drawString(this.fontRendererObj, songTitle, (int)this.getScreenCenterX()-90 + (int)offset - (int)this.songTitleScroll, (int)(this.getScreenCenterY()-50), 0xffffff);
						
						Draw.drawXGradient(this.getScreenCenterX()-90, (int)(this.getScreenCenterY()-50), 40, 10, 0, 0, 0, 1, 0, 0, 0, 0);
						Draw.drawXGradient(this.getScreenCenterX()+90-40, (int)(this.getScreenCenterY()-50), 40, 10, 0, 0, 0, 0, 0, 0, 0, 1);
						Draw.drawRect(this.getScreenCenterX()+90, (int)(this.getScreenCenterY()-50), 10, 10, 0, 0, 0, 1);
						
						this.songTitleScroll+=0.5f;
						while(this.songTitleScroll >= titleWidth) {
							this.songTitleScroll -= titleWidth;
						}
					}else{
						this.drawCenteredString(this.fontRendererObj, songTitle, (int)this.getScreenCenterX(), (int)(this.getScreenCenterY()-50), 0xffffff);
					}
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
		
		if(mouseX >= this.width/2-this.bgWidth/2+205 && mouseX <= this.width/2-this.bgWidth/2+205+16 &&
		   mouseY >= this.height/2-this.bgHeight/2+167 && mouseY <= this.height/2-this.bgHeight/2+167+16) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(RadioSounds.radio_powerbutton_click, 1.0F));
			this.powerButtonClicked = true;
		}
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
		
		if(this.powerButtonClicked) {
			if(this.computerState == RadioBlock.RUNSTATE_OFF){
				this.bootupComputer();
			}else{
				this.computerState = RadioBlock.RUNSTATE_OFF;
				Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(RadioSounds.radio_powerbutton_off, 1.0F));
			}
		}
		
		this.powerButtonClicked = false;
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
						this.computerState = RadioBlock.RUNSTATE_PLAYING;
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
		return (float)((5)*(1+(1-Math.pow((1-this.startupLogoFadeout/50)*4, 2)/16.0f)*0.3f));
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
	
	public float getScreenX() {
		return this.width/2-this.bgWidth/2 + 20;
	}
	
	public float getScreenY() {
		return this.height/2-this.bgHeight/2 + 20;
	}
	
	public float getScreenWidth() {
		return this.bgWidth-40;
	}
	
	public float getScreenHeight() {
		return this.bgHeight-20-41;
	}
	
	public float getScreenCenterX() {
		return this.getScreenX() + this.getScreenWidth()/2;
	}
	
	public float getScreenCenterY() {
		return this.getScreenY() + this.getScreenHeight()/2;
	}
	
	public Song getCurrentPlayedSong() {
		return SongPrivate.privateSongCollection.size() > 0 ? SongPrivate.privateSongCollection.get(0) : null;
	}
}
