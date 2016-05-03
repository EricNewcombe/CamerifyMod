package com.ericnewcombe.camerify.camera.gui;


/**
 * 
 * Clickable element of the menu with an id assigned to it
 * to perform different actions in the main program
 * 
 * @author Eric
 */

public class MenuButton extends MenuElement {

	protected int id;
	
	public MenuButton ( int id, int posX, int posY, int width, int height, int color, int hoverColor, String text, String hoverText ) {
		super(posX, posY, width, height, color, hoverColor, text, hoverText);
		this.id = id;
	}
	public MenuButton ( int id, int posX, int posY, int width, int height, int color, String text) { this(id, posX, posY, width, height, 
																											color, color, text, ""); }
	public MenuButton ( int id, int posX, int posY, int width, int height, int color ) { this(id, posX, posY, width, height, color, ""); }
	
	/**
	 * Determines whether the mouse click is within the element
	 * @param mouseX - Mouse x position
	 * @param mouseY - Mouse y position
	 * @param xOffset - The x offset of the screen
	 * @param yOffset - The y offset of the screen
	 * @return - The result of whether or not the click was contained in the button or not
	 */
	public boolean mouseClickOnButton(int mouseX, int mouseY, int xOffset, int yOffset){
		return mouseX >= posX + xOffset && mouseX <= posX + xOffset + width
				&& mouseY >= posY + yOffset && mouseY <= posY + yOffset + height; 
	}
	
	/**
	 * @return {@link #id}
	 */
	@Override
	public int getId() { return this.id; }
	
}
