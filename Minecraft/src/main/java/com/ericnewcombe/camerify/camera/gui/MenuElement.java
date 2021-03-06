package com.ericnewcombe.camerify.camera.gui;

/**
 * 
 * Base class for elements of the GUI menu to build off of. Holds the basic information in order for it to be displayed on the screen
 * 
 * @author Eric
 * 
 */

public class MenuElement {
	
	/** x position of the screen of the element */
	
	protected int posX;
	
	/**  y position on the screen of the element */
	protected int posY;
	
	/**  Width in pixels of the element */
	protected int width;
	
	/** Height in pixels of the element */
	protected int height;
	
	/** Base colour of the element which will be used to revert it back to its original state */
	protected int defaultColor;
	
	/** The current colour of the element, used for displaying on a {@link Screen} */
	protected int currentColor;
	
	/** The colour that the element turns when it is hovered over */
	protected int hoverColor;
	
	//TODO create better description
	/** The x offset of the screen */
	protected static int xOffset;
	
	/** The y offset of the screen */
	protected static int yOffset;
	
	/** Text to be displayed within the element */
	protected String text;
	
	/** Text to be displayed on a {@link Screen} when the {@link MenuElement} is hovered over */
	protected String hoverText;
	
	/** Whether the data is to be displayed on a {@link Screen} */
	protected boolean visible;
	
	// TODO change how the background colour and hover colours are stored
	
	public MenuElement ( int posX, int posY, int width, int height, int color, int hoverColor, String text, String hoverText ) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.defaultColor = color;
		this.currentColor = this.defaultColor;
		this.text = text;
		this.hoverText = hoverText;
		this.hoverColor = hoverColor;
		this.visible = true;
	}
	
	public MenuElement ( int posX, int posY, int width, int height, int color, String text ) {
		this(posX, posY, width, height, color, color, text, ""); 
	}
	
	public MenuElement ( int posX, int posY, int width, int height, int color ) {
		this(posX, posY, width, height, color, ""); 
	}

	
	
	public int getTop() { 
		return this.posY + yOffset; 
	}
	
	public int getLeft() {
		return this.posX + xOffset; 
	}
	
	public int getBottom() { 
		return this.posY + yOffset + this.height; 
	}
	
	public int getRight() { 
		return this.posX + xOffset + this.width; 
	}
	
	public int getColor() {
		return this.currentColor; 
	}
	
	public int getDefaultColor() { 
		return this.defaultColor; 
	}
	
	public int getId() { 
		return -1; 
	}
	
	public int getCenterX() { 
		return this.posX + MenuElement.xOffset + this.width / 2;  
	}
	
	public int getCenterY() { 
		return this.posY + MenuElement.yOffset + this.height / 2; 
	}
	
	public int getWidth() { 
		return this.width; 
	}
	
	public int getHeight() { 
		return this.height; 
	}
	
	public String getText() { 
		return this.text; 
	}
	
	public String getHoverText() { 
		return this.hoverText; 
	}
	
	public int getHoverColor() { 
		return this.hoverColor; 
	}

	public boolean isVisible() { 
		return this.visible; 
	}
	
	public boolean hasHoverText() {
		return hoverText == "";
	}

	public boolean hasText() {
		return text != "";
	}
	
	
	public void changePosX(int amount) { 
		this.posX += amount; 
	}
	
	public void changePosY(int amount) { 
		this.posY += amount; 
	}
	
	
	public void setColor( int c ) { 
		this.currentColor = c; 
	}
	
	public void setDefaultColor ( int c ) { 
		this.defaultColor = c; 
	}
	

	public void setPosY(int y) { 
		this.posY = y; 
	}
	
	public void setPosX(int x) {
		this.posX = x; 
	}
	
	public void setText(String text) {
		this.text = text; 
	}
	
	public void setVisibility(boolean visible) {
		this.visible = visible; 
	}
	
	public static void setXOffset(int offset) { 
		xOffset = offset; 
	}
	
	public static void setYOffset(int offset) { 
		yOffset = offset; 
	}
	
	
	public boolean isPointInElement( int x, int y, int xOffset, int yOffset ) {
		return x >= posX + xOffset && x <= posX + xOffset + width && 
			   y >= posY + yOffset && y <= posY + yOffset + height; 
	}
	
}
