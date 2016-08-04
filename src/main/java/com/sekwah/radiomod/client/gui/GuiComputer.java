package com.sekwah.radiomod.client.gui;

import java.io.IOException;

import com.sekwah.radiomod.RadioMod;

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
	private LoadingDummy[] loadingDummies = new LoadingDummy[]{
		new LoadingDummy("Loading assets...", 4),
		new LoadingDummy("Prepearing snazzy interface items...", 3),
		new LoadingDummy("User validation...", 2),
		new LoadingDummy("Connection to the great music database...", 5)
	};
	
	public GuiComputer(int computerStateIn) {
		this.computerState = computerStateIn;
	}
	
	public void initGui() {
		super.initGui();
		this.buttonList.clear();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
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
			case 0:
				if(this.loadingProgress >= this.loadingDummies.length) break;
				LoadingDummy currentDummy = this.loadingDummies[this.loadingProgress];
				if(!currentDummy.isLoaded()){
					currentDummy.decreaseLoadingTime();
				}else{
					this.loadingProgress++;
				}
			break;
			case 1:
			break;
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return super.doesGuiPauseGame();
	}
}
