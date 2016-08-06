package com.sekwah.radiomod.client.gui;

import java.io.IOException;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.blocks.RadioBlock;
import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;
import com.sekwah.radiomod.client.sound.RadioSounds;
import com.sekwah.radiomod.music.FileManager;
import com.sekwah.radiomod.music.song.Song;
import com.sekwah.radiomod.music.song.SongPrivate;
import com.sekwah.radiomod.network.packets.server.ServerBootupComputerPacket;
import com.sekwah.radiomod.network.packets.server.ServerShutdownComputerPacket;
import com.sekwah.radiomod.util.Draw;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * Created by on 04/08/2016.
 *
 * @author GoblinBob
 */
public class GuiComputer extends GuiScreen {
	protected int bgWidth = 256;
    protected int bgHeight = 187;
    protected float[] bgColor = new float[]{
    	0.5f,
    	0.3f,
    	0.0f
    };
	public static ResourceLocation computerBg;
	public static ResourceLocation startupLogo;
	public GuiVisualizer guiVisualizer;
	public GuiListSongs guiSongList;

	private PositionedSoundRecord startupFinishSoundRecord;

	private int computerState;
	private int playedSong;
	private float startupSequence;
	private int loadingProgress;
	private float currentStartupTime;
	private float startupLogoFadeout;
	private float desktopFadein;
	private float shutdownSequence;
	private LoadingDummy[] loadingDummies;

	public boolean powerButtonClicked = false;
	public boolean greenLightWhiteFlash = true;
	public float songTitleScroll = 0;

	/*
	 * Specifies the current used screen, for example: Song List (0), My playlists (1), Bookmarked playlists (2)
	 */
	public int currentScreen = 0;

	public TileEntityRadio tileEntity;

	public GuiComputer(TileEntityRadio tileEntity) {
		if(tileEntity != null){
			this.tileEntity = tileEntity;
			this.computerState = this.tileEntity.getRunState();
		}
		FileManager.loadPrivateSongs();

		if(this.computerState == RadioBlock.RUNSTATE_BOOTINGUP) {
			this.setupLoadingDummies();
		}

		//RadioMod.instance.musicManager.playAssetsSound("IRMOST-GlitchHop");
		//RadioMod.instance.musicManager.playStreamUrl("http://stream.dancewave.online:8080/dance.mp3");
		//RadioMod.instance.musicManager.playStreamUrl("https://api.soundcloud.com/tracks/215534268/stream?client_id=23c5983facf3240a2f14515f05f34873");
	}

	public void initGui() {
		super.initGui();
		this.buttonList.clear();

		this.guiVisualizer = new GuiVisualizer((int)this.getScreenCenterX()-45, (int)this.getScreenCenterY()-25, (int)90, (int)50);
		this.guiSongList = new GuiListSongs(this, this.mc, (int) this.getScreenWidth(), (int) this.height, (int) this.getScreenY()+18, (int) (this.getScreenY()+this.getScreenHeight()));
	}

	public void setupLoadingDummies() {
		this.loadingDummies = new LoadingDummy[]{
			new LoadingDummy("Loading assets...", 4),
			new LoadingDummy("Setting up the color scheme...", 4),
			new LoadingDummy("Prepearing snazzy interface item...", 3),
			new LoadingDummy("User validation...", 2),
			new LoadingDummy("Connecting to the great music database...", 5)
		};
	}

	public void bootupComputer() {
		this.computerState = RadioBlock.RUNSTATE_BOOTINGUP;
		RadioMod.packetNetwork.sendToServer(new ServerBootupComputerPacket(true, this.tileEntity.getPos()));

		this.setupLoadingDummies();

		this.greenLightWhiteFlash = true;
		this.startupSequence = 0;
		this.loadingProgress = 0;
		this.currentStartupTime = 0;
		this.startupLogoFadeout = 0;

		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(RadioSounds.radio_startup_click, 1.0F));
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(RadioSounds.radio_powerbutton_release, 1.0F));
	}

	public void finishBootup() {
		this.computerState = RadioBlock.RUNSTATE_ON;
		this.desktopFadein = 20;
	}

	public void shutdownComputer() {
		this.computerState = RadioBlock.RUNSTATE_OFF;

		this.tileEntity.musicSource.stopMusic();

		RadioMod.packetNetwork.sendToServer(new ServerShutdownComputerPacket(true, this.tileEntity.getPos()));

		if(this.startupFinishSoundRecord != null){
			Minecraft.getMinecraft().getSoundHandler().stopSound(this.startupFinishSoundRecord);
		}
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(RadioSounds.radio_powerbutton_off, 1.0F));
		this.shutdownSequence = 10;
	}

	public void selectSong(GuiListSongsEntry selectedSongEntry) {
		this.playSong(selectedSongEntry.getIndex());
	}

	private void playSong(int index) {
		this.computerState = RadioBlock.RUNSTATE_PLAYING;
		this.playedSong = index;
		this.tileEntity.musicSource.playSongCollection(index);
	}

	private void stopSong() {
		this.tileEntity.musicSource.stopMusic();
		this.computerState = RadioBlock.RUNSTATE_ON;
	}
	
	private void previousSong() {
		this.stopSong();
		int nextSong = this.playedSong-1;
		if(nextSong < 0) nextSong = SongPrivate.privateSongCollection.size()-1;
		this.playSong(nextSong);
	}
	
	private void nextSong() {
		this.stopSong();
		int nextSong = this.playedSong+1;
		if(nextSong >= SongPrivate.privateSongCollection.size()) nextSong = 0;
		this.playSong(nextSong);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

		this.drawDefaultBackground();
		Draw.drawRect(this.getScreenX(), this.getScreenY(), this.getScreenWidth(), this.getScreenHeight(), 0, 0, 0, 1);

		switch(this.computerState) {
			case RadioBlock.RUNSTATE_OFF:
				if(this.shutdownSequence > 0) this.shutdownSequence-=partialTicks;
				float vSeq = (10-this.shutdownSequence)/5.0f;
				float hSeq = (5-this.shutdownSequence)/5.0f;
				if(vSeq < 0) vSeq = 0;
				if(hSeq < 0) hSeq = 0;
				if(vSeq > 1) vSeq = 1;
				if(hSeq > 1) hSeq = 1;
				Draw.drawRect(this.getScreenX()+this.getScreenWidth()/2*hSeq, this.getScreenY()+(this.getScreenHeight()-2)/2*vSeq, this.getScreenWidth()*(1-hSeq), (this.getScreenHeight()-2)*(1-vSeq)+2, 1, 1, 1, 1);
			break;
			case RadioBlock.RUNSTATE_BOOTINGUP:
				this.currentStartupTime+=partialTicks;
				if(this.getStartupLogoProgress() >= 1){
					startupLogoFadeout+=partialTicks;
				}

				this.mc.renderEngine.bindTexture(startupLogo);
				Draw.drawTexture(this.getScreenCenterX()-4*this.getStartupLogoScale(), this.getScreenCenterY()-5.5*this.getStartupLogoScale() + (1-this.getStartupLogoProgress())*11*this.getStartupLogoScale() - 10, 0, 0.6875*(1-this.getStartupLogoProgress()), 0.5, 0.6875*(this.getStartupLogoProgress()), 8*this.getStartupLogoScale(), 11*this.getStartupLogoScale()*this.getStartupLogoProgress());

				if(areDummiesLoading()){
					this.drawCenteredString(this.fontRendererObj, this.loadingDummies[this.loadingProgress].getTextToDisplay(), this.width/2, (int)(this.height/2+5.5*this.getStartupLogoScale())+5, 0xffffff);
				}

				if(this.getStartupLogoProgress() >= 1){
					float alpha = (20-this.startupLogoFadeout)/20.0f;
					if(alpha < 0) alpha = 0;
					Draw.drawRect(this.getScreenX(), this.getScreenY(), this.getScreenWidth(), this.getScreenHeight(), 1, 1, 1, alpha);

					this.mc.renderEngine.bindTexture(computerBg);
					Draw.drawTexture(this.getScreenCenterX()-62, this.getScreenCenterY()+26, 1-124F/256, 1-30F/256, 124F/256, 30F/256, 124, 30);

					alpha = (this.startupLogoFadeout-40)/20.0f;
					if(alpha < 0) alpha = 0;
					if(alpha > 1) alpha = 1;
					Draw.drawRect(this.getScreenX(), this.getScreenY(), this.getScreenWidth(), this.getScreenHeight(), 0, 0, 0, alpha);
				}
			break;
			case RadioBlock.RUNSTATE_ON:
				Draw.drawRect(this.getScreenX(), this.getScreenY(), this.getScreenWidth(), this.getScreenHeight(), this.bgColor[0], this.bgColor[1], this.bgColor[2], 1);
				Draw.drawYGradient(this.getScreenX(), this.getScreenY()+this.getScreenHeight()-80, this.getScreenWidth(), 80, this.bgColor[0], this.bgColor[1], this.bgColor[2], 1, this.bgColor[0]*0.7f, this.bgColor[1]*0.7f, this.bgColor[2]*0.7f, 1);

				if(this.currentScreen == 0) {
					this.guiSongList.drawScreen(mouseX, mouseY, partialTicks);
				}

				Draw.drawRect(this.getScreenX(), this.getScreenY(), this.getScreenWidth(), 18, this.bgColor[0]*1.5f, this.bgColor[1]*1.5f, this.bgColor[2]*1.5f, 1);
				String screenTitle = this.currentScreen == 0 ? "Songs" : this.currentScreen == 1 ? "My Playlists" : "Bookmarked";
				this.drawCenteredString(this.fontRendererObj, screenTitle, (int)this.getScreenCenterX(), (int)(this.getScreenY()+5), 0xffffff);
				Draw.drawXGradient(this.getScreenX(), this.getScreenY()+16, this.getScreenWidth()/2, 2, 1, 1, 1, 0.1f, 1, 1, 1, 1);
				Draw.drawXGradient(this.getScreenX()+this.getScreenWidth()/2, this.getScreenY()+16, this.getScreenWidth()/2, 2, 1, 1, 1, 1, 1, 1, 1, 0.1f);
			break;
			case RadioBlock.RUNSTATE_PLAYING:
				Draw.drawRect(this.getScreenX(), this.getScreenY(), this.getScreenWidth(), this.getScreenHeight(), this.bgColor[0], this.bgColor[1], this.bgColor[2], 1);
				Draw.drawYGradient(this.getScreenX(), this.getScreenY()+this.getScreenHeight()-80, this.getScreenWidth(), 80, this.bgColor[0], this.bgColor[1], this.bgColor[2], 1, this.bgColor[0]*0.7f, this.bgColor[1]*0.7f, this.bgColor[2]*0.7f, 1);

				for(int i = 0; i < this.guiVisualizer.getBands(); i++) {
					float ticks = Minecraft.getMinecraft().thePlayer.ticksExisted+partialTicks;
					this.guiVisualizer.buffer[i] = Math.min(Math.abs((float) Math.sin(ticks*0.1f + i*0.2)), 1);
				}
				this.guiVisualizer.draw();

				Draw.drawRect(this.getScreenX(), this.getScreenY(), this.getScreenWidth(), 18, this.bgColor[0]*1.5f, this.bgColor[1]*1.5f, this.bgColor[2]*1.5f, 1);
				if(getCurrentPlayedSong() != null) {
					String songTitle = this.getCurrentPlayedSong().getFullDisplayTitle();
					int titleLength = songTitle.length();
					int titleWidth = this.fontRendererObj.getStringWidth(songTitle);

					if(titleWidth > 165) {
						float offset = 0;
						songTitle += "          ";
						titleLength = songTitle.length();
						titleWidth = this.fontRendererObj.getStringWidth(songTitle);
						songTitle += songTitle;
						for(int i = 0; i < titleLength; i++) {
							if(offset - this.songTitleScroll < 0) {
								offset += this.fontRendererObj.getCharWidth(songTitle.charAt(0));
								songTitle = songTitle.substring(1, songTitle.length());
							}else{
								break;
							}
						}
						float newLength = this.fontRendererObj.getStringWidth(songTitle);

						for(int i = songTitle.length()-1; i >= 0; i--) {
							if(newLength > 165) {
								newLength -= this.fontRendererObj.getCharWidth(songTitle.charAt(i));
								songTitle = songTitle.substring(0, songTitle.length()-1);
							}else{
								break;
							}
						}

						this.drawString(this.fontRendererObj, songTitle, (int)this.getScreenCenterX()-85 + (int)offset - (int)this.songTitleScroll, (int)(this.getScreenY()+5), 0xffffff);

						Draw.drawXGradient(this.getScreenCenterX()-85-1, this.getScreenY(), 40, 16, this.bgColor[0]*1.5f, this.bgColor[1]*1.5f, this.bgColor[2]*1.5f, 1, this.bgColor[0]*1.5f, this.bgColor[1]*1.5f, this.bgColor[2]*1.5f, 0);
						Draw.drawXGradient(this.getScreenCenterX()+85-40+1, this.getScreenY(), 40, 16, this.bgColor[0]*1.5f, this.bgColor[1]*1.5f, this.bgColor[2]*1.5f, 0, this.bgColor[0]*1.5f, this.bgColor[1]*1.5f, this.bgColor[2]*1.5f, 1);
						Draw.drawRect(this.getScreenCenterX()+85, this.getScreenY(), 10, 10, this.bgColor[0]*1.5f, this.bgColor[1]*1.5f, this.bgColor[2]*1.5f, 1);

						this.songTitleScroll+=0.5f;
						while(this.songTitleScroll >= titleWidth) {
							this.songTitleScroll -= titleWidth;
						}
					}else{
						this.drawCenteredString(this.fontRendererObj, songTitle, (int)this.getScreenCenterX(), (int)(this.getScreenY()+5), 0xffffff);
					}

					this.mc.renderEngine.bindTexture(computerBg);
					Draw.drawTexture(this.getScreenCenterX()-20-8, this.getScreenCenterY()+40, 3*16F/256, 1-16F/256, -16F/256, 16F/256, 16, 16);
					Draw.drawTexture(this.getScreenCenterX()-8, this.getScreenCenterY()+40, 0, 1-16F/256, 16F/256, 16F/256, 16, 16);
					Draw.drawTexture(this.getScreenCenterX()+20-8, this.getScreenCenterY()+40, 2*16F/256, 1-16F/256, 16F/256, 16F/256, 16, 16);
				}
				Draw.drawXGradient(this.getScreenX(), this.getScreenY()+16, this.getScreenWidth()/2, 2, 1, 1, 1, 0.1f, 1, 1, 1, 1);
				Draw.drawXGradient(this.getScreenX()+this.getScreenWidth()/2, this.getScreenY()+16, this.getScreenWidth()/2, 2, 1, 1, 1, 1, 1, 1, 1, 0.1f);

				this.mc.renderEngine.bindTexture(this.computerBg);
				if(!(mouseX >= this.getScreenX()+1 && mouseX <= this.getScreenX()+17 && mouseY >= this.getScreenY() && mouseY <= this.getScreenY()+16)) GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
				Draw.drawTexture(this.getScreenX()+2, this.getScreenY(), 4*16F/256, 1-16F/256, -16F/256, 16F/256, 16, 16);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			break;
		}
		if(this.desktopFadein > 0) this.desktopFadein-=partialTicks;
		float alpha = (this.desktopFadein)/20.0f;
		if(alpha < 0) alpha = 0;
		if(alpha > 1) alpha = 1;
		Draw.drawRect(this.getScreenX(), this.getScreenY(), this.getScreenWidth(), this.getScreenHeight(), 0, 0, 0, alpha);

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

		switch(this.computerState) {
			case RadioBlock.RUNSTATE_ON:
				if(this.currentScreen == 0) {
					this.guiSongList.mouseClicked(mouseX, mouseY, mouseButton);
				}
			break;
			case RadioBlock.RUNSTATE_PLAYING:
				if(mouseX >= this.getScreenX()+1 && mouseX <= this.getScreenX()+17 && mouseY >= this.getScreenY() && mouseY <= this.getScreenY()+16) {
					this.stopSong();
				}
				
				if(mouseX >= this.getScreenCenterX()-20-8 && mouseX <= this.getScreenCenterX()-20-8+16 &&
				   mouseY >= this.getScreenCenterY()+40 && mouseY <= this.getScreenCenterY()+40+16){
					previousSong();
				}
				if(mouseX >= this.getScreenCenterX()+20-8 && mouseX <= this.getScreenCenterX()+20-8+16 &&
				   mouseY >= this.getScreenCenterY()+40 && mouseY <= this.getScreenCenterY()+40+16){
					nextSong();
				}
				//Draw.drawTexture(this.getScreenCenterX()-20-8, this.getScreenCenterY()+40, 3*16F/256, 1-16F/256, -16F/256, 16F/256, 16, 16);
				//Draw.drawTexture(this.getScreenCenterX()-8, this.getScreenCenterY()+40, 0, 1-16F/256, 16F/256, 16F/256, 16, 16);
				//Draw.drawTexture(this.getScreenCenterX()+20-8, this.getScreenCenterY()+40, 2*16F/256, 1-16F/256, 16F/256, 16F/256, 16, 16);
			break;
		}
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);

		if(this.powerButtonClicked) {
			if(this.computerState == RadioBlock.RUNSTATE_OFF){
				this.bootupComputer();
			}else{
				this.shutdownComputer();
			}
		}

		this.powerButtonClicked = false;

		switch(this.computerState) {
			case RadioBlock.RUNSTATE_ON:
				if(this.currentScreen == 0) {
					this.guiSongList.mouseReleased(mouseX, mouseY, state);
				}
			break;
		}
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
	}

	public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        this.guiSongList.handleMouseInput();
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
						else{
							this.startupFinishSoundRecord = PositionedSoundRecord.getMasterRecord(RadioSounds.radio_startup_finish, 1.0F);
							Minecraft.getMinecraft().getSoundHandler().playSound(this.startupFinishSoundRecord);
						}
					}
				}else{
					if(this.startupLogoFadeout > 60) {
						this.finishBootup();
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
		return (float)((5)*(1+(1-Math.pow((1-this.startupLogoFadeout/60)*4, 2)/16.0f)*0.3f));
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
		return this.bgWidth-41;
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
		return SongPrivate.privateSongCollection.size() > 0 ? SongPrivate.privateSongCollection.get(this.playedSong) : null;
	}
}
