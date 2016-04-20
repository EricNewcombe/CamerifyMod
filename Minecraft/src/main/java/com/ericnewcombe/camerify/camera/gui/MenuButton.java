package com.ericnewcombe.camerify.camera.gui;

public class MenuButton extends MenuElement {

	/*
	 * 
	 * Clickable element of the menu with an id assigned to it
	 * to perform different actions in the main program
	 * 
	 */
	
	protected int id;
	
	public MenuButton ( int id, int posX, int posY, int width, int height, int color, int hoverColor, String text, String hoverText ) {
		super(posX, posY, width, height, color, hoverColor, text, hoverText);
		this.id = id;
	}
	public MenuButton ( int id, int posX, int posY, int width, int height, int color, String text) { this(id, posX, posY, width, height, 
																											color, color, text, ""); }
	public MenuButton ( int id, int posX, int posY, int width, int height, int color ) { this(id, posX, posY, width, height, color, ""); }
	
	
	public boolean mouseClickOnButton(int mouseX, int mouseY, int xOffset, int yOffset){
		return mouseX >= posX + xOffset && mouseX <= posX + xOffset + width
				&& mouseY >= posY + yOffset && mouseY <= posY + yOffset + height; 
	}
	
	public int getId() { return this.id; }
	
}
