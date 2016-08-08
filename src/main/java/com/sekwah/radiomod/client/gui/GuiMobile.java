package com.sekwah.radiomod.client.gui;

import java.io.IOException;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.sekwah.radiomod.RadioMod;
import com.sekwah.radiomod.blocks.BlockRadio;
import com.sekwah.radiomod.blocks.tileentities.TileEntityRadio;
import com.sekwah.radiomod.client.sound.RadioSounds;
import com.sekwah.radiomod.music.FileManager;
import com.sekwah.radiomod.music.MobileManager;
import com.sekwah.radiomod.music.MusicSource;
import com.sekwah.radiomod.music.song.Song;
import com.sekwah.radiomod.music.song.SongBuiltIn;
import com.sekwah.radiomod.music.song.SongPrivate;
import com.sekwah.radiomod.music.song.SongSoundCloud;
import com.sekwah.radiomod.music.song.TrackingData;
import com.sekwah.radiomod.network.packets.server.ServerPlaySongPacket;
import com.sekwah.radiomod.network.packets.server.ServerStopSongPacket;
import com.sekwah.radiomod.util.Draw;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * Created by on 04/08/2016.
 *
 * @author GoblinBob
 */
public class GuiMobile extends GuiScreen {
	protected int bgWidth = 184;
	protected int bgHeight = 256;
	protected float[] bgColor = new float[]{
			0.5f,
			0.3f,
			0.0f
	};
	public static ResourceLocation mobileBg;
	public GuiVisualizer guiVisualizer;
	public GuiVisualizer guiVisualizerFullSize;
	public GuiListMobileSongs guiSongList;
	private GuiTextField guiTextField;

	private int playedSong;
	private float startupSequence;
	private float bringUpSequence;
	private int loadingProgress;
	private float currentStartupTime;
	private float startupLogoFadeout;
	private float desktopFadein;
	private float shutdownSequence;
	private LoadingDummy[] loadingDummies;
	
	public float songTitleScroll = 0;
	public boolean[] deviceButtons = new boolean[3];
	public boolean confirmButtonDown = false;

	/*
	 * Specifies the current used screen, for example: Song List (0), My playlists (1), Bookmarked playlists (2)
	 */
	public int currentTab = 0;
	public Tab[] tabs = new Tab[]{
			new Tab("Chef's Specials"),
			new Tab("Private Collection"),
			new Tab("Playlists"),
			new Tab("Bookmarked"),
			new Tab("Radio Stations"),
			new Tab("SoundCloud"),
	};
	public static final int TAB_BUILTIN = 0;
	public static final int TAB_PRIVATE = 1;
	public static final int TAB_PLAYLISTS = 2;
	public static final int TAB_BOOKMARKED = 3;
	public static final int TAB_RADIO = 4;
	public static final int TAB_SOUNDCLOUD = 5;

	public TileEntityRadio tileEntity;
	
	public GuiMobile(TileEntityRadio tileEntity) {
		this.tileEntity = tileEntity;
		Song currentSong = null;
		if(this.tileEntity != null && this.tileEntity.getMusicSource() != null && this.tileEntity.getMusicSource().getCurrentSong() != null) {
			currentSong = this.tileEntity.getMusicSource().getCurrentSong();
		}else{
			currentSong = MobileManager.getSongPlaying();
		}
		
		if(currentSong != null){
			if(currentSong instanceof SongBuiltIn) {
				currentTab = 0;
			}else if(currentSong instanceof SongPrivate) {
				currentTab = 1;
			}else if(currentSong instanceof SongSoundCloud) {
				currentTab = 5;
			}
			this.playedSong = currentSong.getID();
		}
		
		FileManager.loadPrivateSongs();
	}
	
	public void initGui() {
		super.initGui();
		this.buttonList.clear();
		
		this.guiVisualizer = new GuiVisualizer((int)this.getScreenCenterX()-60, (int)this.getScreenCenterY()-25, (int)120, (int)50);
		this.guiVisualizerFullSize = new GuiVisualizer((int)this.getScreenX(), (int) ((int)this.getScreenY()+this.getScreenHeight()-100), (int)this.getScreenWidth(), (int)100);
		this.guiSongList = new GuiListMobileSongs(this, this.mc, (int) this.getScreenWidth(), (int) this.getScreenHeight()-18, (int) ((int) this.getScreenY()+18-this.getYOffset()), (int) (this.getScreenY()+this.getScreenHeight()-this.getYOffset()));
		this.guiTextField = new GuiTextField(10, this.fontRendererObj, (int) (this.getScreenX()), (int) (this.getScreenY()+18), (int) (this.getScreenWidth()-20), 19);
        this.guiTextField.setText("Song URL");
        this.guiTextField.setMaxStringLength(100000000);
        Keyboard.enableRepeatEvents(true);
		this.openTab(0);
		
		if(this.getRunState() == MobileManager.MOBILESTATE_BOOTINGUP) {
			this.setupLoadingDummies();
		}
	}
	
	public void setupLoadingDummies() {
		this.loadingDummies = new LoadingDummy[]{
			new LoadingDummy("Loading assets...", 4),
			new LoadingDummy("Setting up the color scheme...", 4),
			new LoadingDummy("Snazzying out the UI...", 3),
			new LoadingDummy("User validation...", 2),
			new LoadingDummy("Gathering funky beats...", 5)
		};
	}
	
	public void openTab(int tab) {
		tab = Math.max(Math.min(tab, this.tabs.length-1), 0);
		
		this.currentTab = tab;
		this.guiSongList.fillOut(getSongCollection());
	}
	
	public void bootupComputer() {
		this.setRunState(MobileManager.MOBILESTATE_BOOTINGUP);
		
		this.initGui();

		this.startupSequence = 0;
		this.loadingProgress = 0;
		this.currentStartupTime = 0;
		this.startupLogoFadeout = 0;
		
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(RadioSounds.radio_startup_click, 1.0F));
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(RadioSounds.radio_powerbutton_release, 1.0F));
	}
	
	public void finishBootup() {
		this.setRunState(MobileManager.MOBILESTATE_ON);
		this.desktopFadein = 20;
	}
	
	public void shutdownComputer() {
		this.setRunState(MobileManager.MOBILESTATE_OFF);
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(RadioSounds.radio_powerbutton_off, 1.0F));
		this.shutdownSequence = 10;
	}
	
	public void selectSong(GuiListMobileSongsEntry guiListMobileSongsEntry) {
		this.playSong(guiListMobileSongsEntry.getIndex());
	}
	
	private void playSong(int index) {
		this.playSong(index, 0);
	}
	
	private void playSong(int index, int frame) {
		this.setRunState(MobileManager.MOBILESTATE_PLAYING);
		this.playedSong = index;
		
		switch(this.currentTab) {
			case 0:
				if(this.isInHand()){
					this.getMusicSource().playBuiltInSongCollection(index, frame, false);
				}else{
					RadioMod.packetNetwork.sendToServer(new ServerPlaySongPacket(this.tileEntity.getUUID(),
							new TrackingData(TrackingData.BUILTIN, String.valueOf(index), frame)));
				}
			break;
			case 1:
				if(this.isInHand()){
					this.getMusicSource().playPrivateSongCollection(index, frame, false);
				}else{
					RadioMod.packetNetwork.sendToServer(new ServerPlaySongPacket(this.tileEntity.getUUID(),
							new TrackingData(TrackingData.PRIVATE, String.valueOf(index), frame)));
				}
			break;
		}
	}

	private void stopSong() {
		this.setFramePaused(0);
		
		if(this.isInHand()){
			this.getMusicSource().stopMusic();
		}else{
			RadioMod.packetNetwork.sendToServer(new ServerStopSongPacket(this.tileEntity.getUUID()));
		}
		
		this.setRunState(MobileManager.MOBILESTATE_ON);
	}
	
	private void previousSong() {
		if(getSongCollection() == null) return;
		
		this.stopSong();
		int nextSong = this.playedSong-1;
		if(nextSong < 0) nextSong = getSongCollection().size()-1;
		this.playSong(nextSong);
	}
	
	private void nextSong() {
		if(getSongCollection() == null) return;
		
		this.stopSong();
		int nextSong = this.playedSong+1;
		if(nextSong >= getSongCollection().size()) nextSong = 0;
		this.playSong(nextSong);
	}

	private void togglePlay(){
		if(this.getMusicSource() != null && this.getMusicSource().getIsPlaying()){
			this.setFramePaused(this.getMusicSource().getCurrentFrame());
			if(this.isInHand()){
				this.getMusicSource().stopMusic();
			}else{
				RadioMod.packetNetwork.sendToServer(new ServerStopSongPacket(this.tileEntity.getUUID()));
			}
		}
		else{
			this.playSong(playedSong, this.getFramePaused());
		}
	}
	
	public void submitSongURL() {
		String url = this.guiTextField.getText();
		
		if(this.isInHand()){
			this.getMusicSource().stopMusic();
			this.getMusicSource().playStreamUrl(url);
		}else{
			RadioMod.packetNetwork.sendToServer(new ServerStopSongPacket(this.tileEntity.getUUID()));
			RadioMod.packetNetwork.sendToServer(new ServerPlaySongPacket(this.tileEntity.getUUID(),
					new TrackingData(TrackingData.STREAM, url, 0)));
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		this.drawDefaultBackground();
		Draw.drawRect(this.getScreenX(), this.getScreenY(), this.getScreenWidth(), this.getScreenHeight(), 0, 0, 0, 1);
		
		if(this.bringUpSequence < 1){
			this.bringUpSequence+=partialTicks*0.1f;
			if(this.bringUpSequence > 1){
				this.initGui();
				this.bringUpSequence = 1;
			}
		}
		
		switch(this.getRunState()) {
			case MobileManager.MOBILESTATE_OFF:
				if(this.bringUpSequence == 1){
					this.bootupComputer();
				}
				
				if(this.shutdownSequence > 0) this.shutdownSequence-=partialTicks;
				float vSeq = (10-this.shutdownSequence)/5.0f;
				float hSeq = (5-this.shutdownSequence)/5.0f;
				if(vSeq < 0) vSeq = 0;
				if(hSeq < 0) hSeq = 0;
				if(vSeq > 1) vSeq = 1;
				if(hSeq > 1) hSeq = 1;
				Draw.drawRect(this.getScreenX()+this.getScreenWidth()/2*hSeq, this.getScreenY()+(this.getScreenHeight()-2)/2*vSeq, this.getScreenWidth()*(1-hSeq), (this.getScreenHeight()-2)*(1-vSeq)+2, 1, 1, 1, 1);
			break;
			case MobileManager.MOBILESTATE_BOOTINGUP:
				this.currentStartupTime+=partialTicks;
				if(this.getStartupLogoProgress() >= 1){
					startupLogoFadeout+=partialTicks;
				}
				
				this.mc.renderEngine.bindTexture(GuiComputer.startupLogo);
				Draw.drawTexture(this.getScreenCenterX()-4*this.getStartupLogoScale(), this.getScreenCenterY()-5.5*this.getStartupLogoScale() + (1-this.getStartupLogoProgress())*11*this.getStartupLogoScale() - 10, 0, 0.6875*(1-this.getStartupLogoProgress()), 0.5, 0.6875*(this.getStartupLogoProgress()), 8*this.getStartupLogoScale(), 11*this.getStartupLogoScale()*this.getStartupLogoProgress());
				
				if(areDummiesLoading()){
					this.drawCenteredString(this.fontRendererObj, this.loadingDummies[this.loadingProgress].getTextToDisplay(), this.width/2, (int)(this.getYOffset()+this.height/2+5.5*this.getStartupLogoScale()), 0xffffff);
				}
				
				if(this.getStartupLogoProgress() >= 1){
					float alpha = (20-this.startupLogoFadeout)/20.0f;
					if(alpha < 0) alpha = 0;
					Draw.drawRect(this.getScreenX(), this.getScreenY(), this.getScreenWidth(), this.getScreenHeight(), 1, 1, 1, alpha);
				
					this.mc.renderEngine.bindTexture(GuiComputer.computerBg);
					Draw.drawTexture(this.getScreenCenterX()-62, this.getScreenCenterY()+26, 1-124F/256, 1-30F/256, 124F/256, 30F/256, 124, 30);
					
					alpha = (this.startupLogoFadeout-40)/20.0f;
					if(alpha < 0) alpha = 0;
					if(alpha > 1) alpha = 1;
					Draw.drawRect(this.getScreenX(), this.getScreenY(), this.getScreenWidth(), this.getScreenHeight(), 0, 0, 0, alpha);
				}
			break;
			case MobileManager.MOBILESTATE_ON:
				Draw.drawRect(this.getScreenX(), this.getScreenY(), this.getScreenWidth(), this.getScreenHeight(), this.bgColor[0], this.bgColor[1], this.bgColor[2], 1);
				Draw.drawYGradient(this.getScreenX(), this.getScreenY()+this.getScreenHeight()-80, this.getScreenWidth(), 80, this.bgColor[0], this.bgColor[1], this.bgColor[2], 1, this.bgColor[0]*0.7f, this.bgColor[1]*0.7f, this.bgColor[2]*0.7f, 1);

				if(this.currentTab == TAB_BUILTIN) {
					this.guiSongList.drawScreen(mouseX, mouseY, partialTicks);
				}else if(this.currentTab == TAB_PRIVATE) {
					this.guiSongList.drawScreen(mouseX, mouseY, partialTicks);
				}else if(this.currentTab == TAB_RADIO) {
					this.guiTextField.yPosition = (int) (this.getScreenY()+18);
					this.guiTextField.drawTextBox();
					GlStateManager.color(1.0f, 1.0f, 1.0f);
					
					//Drawing the confirmation button
					this.mc.renderEngine.bindTexture(GuiComputer.computerBg);
					Draw.drawTexture(this.getScreenX()+this.getScreenWidth()-20, this.getScreenY()+18, (64F+(this.confirmButtonDown?20F:0F))/256, 1-20F/256, 20F/256, 20F/256, 20, 20);
					
					
					Draw.drawRect(this.getScreenX(), this.getScreenY()+38, this.getScreenWidth(), this.getScreenHeight()-38, (1-this.bgColor[0])*0.1f, (1-this.bgColor[1])*0.1f, (1-this.bgColor[2])*0.1f, 1);
					
					if(this.getMusicSource() != null && this.getMusicSource().getPlayer() != null){
						if(this.getMusicSource().getPlayer().getRawData() != null){
							
							int dataLength = this.getMusicSource().getPlayer().getRawData().length;
							this.guiVisualizerFullSize.setSampleRate(dataLength >= 2048 ? 2048 : dataLength >= 1024 ? 1024 : 0);
							this.guiVisualizerFullSize.populate(this.getMusicSource());
						}
						this.guiVisualizerFullSize.setLocation((int)this.getScreenX(), (int) (this.getScreenY()+this.getScreenHeight()-100));
						this.guiVisualizerFullSize.calculateBands();
						float average = 0;
						for(int i = 0; i < this.guiVisualizerFullSize.bandSmoothValues.length; i++) {
							average+=this.guiVisualizerFullSize.bandSmoothValues[i];
						}
						average/=this.guiVisualizerFullSize.bandSmoothValues.length;
						Draw.drawYGradient(this.getScreenX(), this.getScreenY()+this.getScreenHeight()-100*average, this.getScreenWidth(), 100*average, 1-this.bgColor[0], 1-this.bgColor[1], 1-this.bgColor[2], 0, 1-this.bgColor[0], 1-this.bgColor[1], 1-this.bgColor[2], 1.0f*average);
						
						this.guiVisualizerFullSize.draw();
					}
				}else if(this.currentTab == TAB_SOUNDCLOUD) {
					this.guiTextField.yPosition = (int) (this.getScreenY()+18);
					this.guiTextField.drawTextBox();
					
					//Drawing the confirmation button
					this.mc.renderEngine.bindTexture(GuiComputer.computerBg);
					Draw.drawTexture(this.getScreenX()+this.getScreenWidth()-20, this.getScreenY()+18, (64F+(this.confirmButtonDown?20F:0F))/256, 1-20F/256, 20F/256, 20F/256, 20, 20);
				}

				Draw.drawRect(this.getScreenX(), this.getScreenY(), this.getScreenWidth(), 18, this.bgColor[0]*1.5f, this.bgColor[1]*1.5f, this.bgColor[2]*1.5f, 1);
				String screenTitle = this.tabs[this.currentTab].displayLabel;
				this.drawCenteredString(this.fontRendererObj, screenTitle, (int)this.getScreenCenterX(), (int)(this.getScreenY()+5), 0xffffff);
				Draw.drawXGradient(this.getScreenX(), this.getScreenY()+16, this.getScreenWidth()/2, 2, 1, 1, 1, 0.1f, 1, 1, 1, 1);
				Draw.drawXGradient(this.getScreenX()+this.getScreenWidth()/2, this.getScreenY()+16, this.getScreenWidth()/2, 2, 1, 1, 1, 1, 1, 1, 1, 0.1f);
				
				this.mc.renderEngine.bindTexture(GuiComputer.computerBg);
				if(!(mouseX >= this.getScreenX()+1 && mouseX <= this.getScreenX()+17 && mouseY >= this.getScreenY() && mouseY <= this.getScreenY()+16)) GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
				if(this.currentTab > 0) Draw.drawTexture(this.getScreenX()+2, this.getScreenY(), 4*16F/256, 1-16F/256, -16F/256, 16F/256, 16, 16);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				
				if(!(mouseX >= this.getScreenX()+this.getScreenWidth()-16-1 && mouseX <= this.getScreenX()+this.getScreenWidth() && mouseY >= this.getScreenY() && mouseY <= this.getScreenY()+16)) GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
				if(this.currentTab < this.tabs.length-1) Draw.drawTexture(this.getScreenX()+this.getScreenWidth()-16-2, this.getScreenY(), 3*16F/256, 1-16F/256, 16F/256, 16F/256, 16, 16);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			break;
			case MobileManager.MOBILESTATE_PLAYING:
				Draw.drawRect(this.getScreenX(), this.getScreenY(), this.getScreenWidth(), this.getScreenHeight(), this.bgColor[0], this.bgColor[1], this.bgColor[2], 1);
				Draw.drawYGradient(this.getScreenX(), this.getScreenY()+this.getScreenHeight()-80, this.getScreenWidth(), 80, this.bgColor[0], this.bgColor[1], this.bgColor[2], 1, this.bgColor[0]*0.7f, this.bgColor[1]*0.7f, this.bgColor[2]*0.7f, 1);
				
				if(this.getMusicSource() != null && this.getMusicSource().getPlayer() != null){
					if(this.getMusicSource().getPlayer().getRawData() != null){
						int dataLength = this.getMusicSource().getPlayer().getRawData().length;
						this.guiVisualizer.setSampleRate(dataLength >= 2048 ? 2048 : dataLength >= 1024 ? 1024 : 0);
						this.guiVisualizer.populate(this.getMusicSource());
					}
					this.guiVisualizer.setLocation((int)this.getScreenCenterX()-60, (int)this.getScreenCenterY()-25);
					this.guiVisualizer.calculateBands();
					this.guiVisualizer.draw();
				}

				Draw.drawRect(this.getScreenX(), this.getScreenY(), this.getScreenWidth(), 18, this.bgColor[0]*1.5f, this.bgColor[1]*1.5f, this.bgColor[2]*1.5f, 1);
				if(getCurrentPlayedSong() != null) {
					String songTitle = this.getCurrentPlayedSong().getFullDisplayTitle();
					int titleLength = songTitle.length();
					int titleWidth = this.fontRendererObj.getStringWidth(songTitle);

					if(titleWidth > 150) {
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
							if(newLength > 150) {
								newLength -= this.fontRendererObj.getCharWidth(songTitle.charAt(i));
								songTitle = songTitle.substring(0, songTitle.length()-1);
							}else{
								break;
							}
						}

						this.drawString(this.fontRendererObj, songTitle, (int)this.getScreenCenterX()-65 + (int)offset - (int)this.songTitleScroll, (int)(this.getScreenY()+5), 0xffffff);

						Draw.drawXGradient(this.getScreenCenterX()-65-1, this.getScreenY(), 30, 16, this.bgColor[0]*1.5f, this.bgColor[1]*1.5f, this.bgColor[2]*1.5f, 1, this.bgColor[0]*1.5f, this.bgColor[1]*1.5f, this.bgColor[2]*1.5f, 0);
						Draw.drawXGradient(this.getScreenCenterX()+65-30+1, this.getScreenY(), 30, 16, this.bgColor[0]*1.5f, this.bgColor[1]*1.5f, this.bgColor[2]*1.5f, 0, this.bgColor[0]*1.5f, this.bgColor[1]*1.5f, this.bgColor[2]*1.5f, 1);
						Draw.drawRect(this.getScreenCenterX()+65, this.getScreenY(), 20, 16, this.bgColor[0]*1.5f, this.bgColor[1]*1.5f, this.bgColor[2]*1.5f, 1);

						this.songTitleScroll+=0.5f;
						while(this.songTitleScroll >= titleWidth) {
							this.songTitleScroll -= titleWidth;
						}
					}else{
						this.drawCenteredString(this.fontRendererObj, songTitle, (int)this.getScreenCenterX(), (int)(this.getScreenY()+5), 0xffffff);
					}

					this.mc.renderEngine.bindTexture(GuiComputer.computerBg);
					Draw.drawTexture(this.getScreenCenterX()-20-8, this.getScreenCenterY()+40, 3*16F/256, 1-16F/256, -16F/256, 16F/256, 16, 16);
					// TODO draw play and pause.
					if(this.getMusicSource() != null && this.getMusicSource().getIsPlaying()){
						Draw.drawTexture(this.getScreenCenterX()-8, this.getScreenCenterY()+40, 1*16F/256, 1-16F/256, 16F/256, 16F/256, 16, 16);
					}
					else{
						Draw.drawTexture(this.getScreenCenterX()-8, this.getScreenCenterY()+40, 0, 1-16F/256, 16F/256, 16F/256, 16, 16);
					}
					//Draw.drawTexture(this.getScreenCenterX()-8, this.getScreenCenterY()+40, 0, 1-16F/256, 16F/256, 16F/256, 16, 16);
					Draw.drawTexture(this.getScreenCenterX()+20-8, this.getScreenCenterY()+40, 2*16F/256, 1-16F/256, 16F/256, 16F/256, 16, 16);
				}
				Draw.drawXGradient(this.getScreenX(), this.getScreenY()+16, this.getScreenWidth()/2, 2, 1, 1, 1, 0.1f, 1, 1, 1, 1);
				Draw.drawXGradient(this.getScreenX()+this.getScreenWidth()/2, this.getScreenY()+16, this.getScreenWidth()/2, 2, 1, 1, 1, 1, 1, 1, 1, 0.1f);

				this.mc.renderEngine.bindTexture(GuiComputer.computerBg);
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

		this.mc.renderEngine.bindTexture(this.mobileBg);
		Draw.drawTexture(this.width/2-this.bgWidth/2, this.getYOffset()+this.height/2-this.bgHeight/2, 0, 0, ((float)this.bgWidth)/256, 1, this.bgWidth, this.bgHeight);
		
		if(this.deviceButtons[0]) Draw.drawTexture(this.width/2-this.bgWidth/2+19, this.getYOffset()+this.height/2-this.bgHeight/2+193, 1-38F/256, 54F/256, 38F/256, 38F/256, 38, 38);
		if(this.deviceButtons[1]) Draw.drawTexture(this.width/2-this.bgWidth/2+65, this.getYOffset()+this.height/2-this.bgHeight/2+185, 1-54F/256, 0, 54F/256, 54F/256, 54, 54);
		if(this.deviceButtons[2]) Draw.drawTexture(this.width/2-this.bgWidth/2+127, this.getYOffset()+this.height/2-this.bgHeight/2+193, 1-38F/256, 92F/256, 38F/256, 38F/256, 38, 38);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);

		switch(this.getRunState()){
			case MobileManager.MOBILESTATE_ON:
				if(this.currentTab == TAB_RADIO) {
					if (this.guiTextField.isFocused())
			        {
			            this.guiTextField.textboxKeyTyped(typedChar, keyCode);
			        }
				}else if(this.currentTab == TAB_SOUNDCLOUD){
					if (this.guiTextField.isFocused())
			        {
			            this.guiTextField.textboxKeyTyped(typedChar, keyCode);
			        }
				}
			break;
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		if(mouseButton == 0){
			if(mouseX >= this.getScreenX()+10 && mouseX <= this.getScreenX()+10+38 &&
			   mouseY >= this.getScreenY()+173 && mouseY <= this.getScreenY()+173+38) {
				this.deviceButtons[0] = true;
			}
			
			if(mouseX >= this.getScreenX()+56 && mouseX <= this.getScreenX()+56+54 &&
			   mouseY >= this.getScreenY()+165 && mouseY <= this.getScreenY()+165+54) {
				this.deviceButtons[1] = true;
			}
			
			if(mouseX >= this.getScreenX()+118 && mouseX <= this.getScreenX()+118+38 &&
			   mouseY >= this.getScreenY()+173 && mouseY <= this.getScreenY()+173+38) {
				this.deviceButtons[2] = true;
			}

			if(mouseX >= this.getScreenX()+this.getScreenWidth()-20 && mouseX <= this.getScreenX()+this.getScreenWidth() &&
			   mouseY >= this.getScreenY()+18 && mouseY <= this.getScreenY()+18+20) {
				this.confirmButtonDown = true;
				
				this.submitSongURL();
			}
		}
		
		switch(this.getRunState()) {
			case BlockRadio.RUNSTATE_ON:
				if(this.currentTab == TAB_BUILTIN) {
					this.guiSongList.mouseClicked(mouseX, mouseY, mouseButton);
				}else if(this.currentTab == TAB_PRIVATE) {
					this.guiSongList.mouseClicked(mouseX, mouseY, mouseButton);
				}else if(this.currentTab == TAB_RADIO) {
					this.guiTextField.mouseClicked(mouseX, mouseY, mouseButton);
				}else if(this.currentTab == TAB_SOUNDCLOUD) {
					this.guiTextField.mouseClicked(mouseX, mouseY, mouseButton);
				}
				
				if(mouseX >= this.getScreenX()+1 && mouseX <= this.getScreenX()+17 && mouseY >= this.getScreenY() && mouseY <= this.getScreenY()+16) {
					openTab(this.currentTab-1);
				}
				
				if(mouseX >= this.getScreenX()+this.getScreenWidth()-16-1 && mouseX <= this.getScreenX()+this.getScreenWidth() && mouseY >= this.getScreenY() && mouseY <= this.getScreenY()+16) {
					openTab(this.currentTab+1);
				}
			break;
			case BlockRadio.RUNSTATE_PLAYING:
				if(mouseX >= this.getScreenX()+1 && mouseX <= this.getScreenX()+17 && mouseY >= this.getScreenY() && mouseY <= this.getScreenY()+16) {
					this.stopSong();
				}
				
				if((mouseX >= this.getScreenCenterX()-20-8 && mouseX <= this.getScreenCenterX()-20-8+16 &&
				   mouseY >= this.getScreenCenterY()+40 && mouseY <= this.getScreenCenterY()+40+16) || this.deviceButtons[0]){
					previousSong();
				}
				if((mouseX >= this.getScreenCenterX()+20-8 && mouseX <= this.getScreenCenterX()+20-8+16 &&
				   mouseY >= this.getScreenCenterY()+40 && mouseY <= this.getScreenCenterY()+40+16) || this.deviceButtons[2]){
					nextSong();
				}
				if((mouseX >= this.getScreenCenterX()-8 && mouseX <= this.getScreenCenterX()-8+16 &&
				   mouseY >= this.getScreenCenterY()+40 && mouseY <= this.getScreenCenterY()+40+16) || this.deviceButtons[1]){
					togglePlay();
				}
			break;
		}
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
		
		for(int i = 0; i < this.deviceButtons.length; i++) {
			this.deviceButtons[i] = false;
		}
		this.confirmButtonDown = false;

		switch(this.getRunState()) {
			case BlockRadio.RUNSTATE_ON:
				if(this.currentTab == 0) {
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
		
		switch(this.getRunState()) {
			case MobileManager.MOBILESTATE_BOOTINGUP:
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
					if(this.startupLogoFadeout > 60) {
						this.finishBootup();
					}
				}
			break;
			case MobileManager.MOBILESTATE_ON:
				if(this.currentTab == TAB_SOUNDCLOUD) {
					this.guiTextField.updateCursorCounter();
				}else if(this.currentTab == TAB_RADIO) {
					this.guiTextField.updateCursorCounter();
				}
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
		return this.width/2-this.bgWidth/2 + 9;
	}
	
	public float getScreenY() {
		return this.height/2-this.bgHeight/2 + 20 + this.getYOffset();
	}
	
	public float getScreenWidth() {
		return 166;
	}
	
	public float getScreenHeight() {
		return 151;
	}
	
	public float getScreenCenterX() {
		return this.getScreenX() + this.getScreenWidth()/2;
	}
	
	public float getScreenCenterY() {
		return this.getScreenY() + this.getScreenHeight()/2;
	}
	
	public Song getCurrentPlayedSong() {
		switch(this.currentTab) {
			case 0:
				return SongBuiltIn.builtInSongCollection.get(this.playedSong);
			case 1:
				return SongPrivate.privateSongCollection.get(this.playedSong);
		}
		return null;
	}
	
	public List<? extends Song> getSongCollection() {
		switch(this.currentTab) {
			case 0:
				return SongBuiltIn.builtInSongCollection;
			case 1:
				return SongPrivate.privateSongCollection;
		}
		return null;
	}
	
	public float getYOffset() {
		float mag = 10;
		return (float) (Math.pow((1-this.bringUpSequence)*mag, 2)/(mag*mag))*height/2;
	}
	
	public int getRunState() {
		return this.tileEntity != null ? this.tileEntity.getRunState() : MobileManager.getMobileState();
	}
	
	public void setRunState(int runStateIn) {
		if(this.tileEntity != null) {
			this.tileEntity.setRunState(runStateIn);
		}else{
			MobileManager.setMobileState(runStateIn);
		}
	}
	
	public MusicSource getMusicSource() {
		return this.tileEntity != null ? RadioMod.instance.musicManager.radioSources.get(this.tileEntity.getUUID()) : MobileManager.getLocalMusicSource();
	}
	
	private boolean isInHand() {
		return this.tileEntity == null;
	}
	
	public int getFramePaused() {
		return this.tileEntity != null ? this.tileEntity.getFramePaused() : MobileManager.getFramePaused();
	}
	
	public void setFramePaused(int framePaused) {
		if(this.tileEntity != null) {
			this.tileEntity.setFramePaused(framePaused);
		}else{
			MobileManager.setFramePaused(framePaused);
		}
	}
	
	public class Tab {
		public String displayLabel;
		
		public Tab(String displayLabelIn) {
			this.displayLabel = displayLabelIn;
		}
	}
}
