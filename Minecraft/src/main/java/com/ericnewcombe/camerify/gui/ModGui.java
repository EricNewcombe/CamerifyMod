package com.ericnewcombe.camerify.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Mouse;

import com.ericnewcombe.camerify.Main;
import com.ericnewcombe.camerify.camera.gui.MenuButton;
import com.ericnewcombe.camerify.camera.gui.MenuElement;
import com.ericnewcombe.camerify.camera.gui.Screen;
import com.ericnewcombe.camerify.camera.gui.ScrollList;
import com.ericnewcombe.camerify.camera.CameraPoint;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class ModGui extends GuiScreen {
	
	private final int POINT_SPACING = 22;
	
	//Main Menus
	private final int EXIT_MENU = 0,
					  OPTIONS_SCREEN = 1,
				      EDIT_POINT_SCREEN = 2;
	
	private final int BLANK_SUBSCREEN = 0,
					  CAM_POINTS_SUBSCREEN = 1,
					  CAM_CONTROLS_SUBSCREEN = 2,
					  THIRD_PERSON_SUBSCREEN = 3,
					  GLOBAL_OPTIONS_SUBSCREEN = 4;
	
	private final int MENU_BUTTON_BACKGROUND = 0xdf000000,
					  MENU_BUTTON_HOVER = 0xaf000000,
					  MENU_BUTTON_SELECTED = 0x6f001000,
					  DELETE_BUTTON_BACKGROUND = 0xbbbb0000,
					  DELETE_BUTTON_HOVER = 0x77bb0000,
					  ADD_BUTTON_BACKGROUND = 0xbb00bb00,
					  ADD_BUTTON_HOVER = 0x7700bb00;
	
	private Minecraft mc;
	private Map<Integer, Screen> screens, subScreens;
	private Screen activeScreen;
	
	private int xOffset, yOffset;
	private ScrollList pointList;
	private int pointSelected;
	private int mouseX, mouseY;
	private int screenCenterX, screenCenterY;
	private int screenIndex, subScreenIndex;
	
	public ModGui ( EntityPlayer p ) {
		super();
		this.mc = Minecraft.getMinecraft();
		this.screens = new HashMap<Integer, Screen>();
		this.pointList = new ScrollList(5, POINT_SPACING);
		this.screenIndex = OPTIONS_SCREEN;
		this.subScreenIndex = 0;
		this.pointSelected = 1;
		initializeElements();
	}
	
	@Override
	public void drawScreen( int x, int y, float f ) {

		this.mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
		this.mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
		this.xOffset = (this.width - 330) / 2;
		
		MenuElement.setXOffset(xOffset);
		
		MenuElement hoverTextBox = activeScreen.getHoverTextBox();
		String hoverText = getHoverText(mouseX, mouseY);
		if ( hoverTextBox != null ) {
			hoverTextBox.setText( hoverText );
			hoverTextBox.setVisibility( hoverText == "" ? false : true );
		}
		else {
			System.out.println("asdf");
		}
		
		
		ArrayList<MenuElement> visibleElements = activeScreen.getVisibleElements();
		
		MenuElement currentElement;
		
		for ( int i = 0; i < visibleElements.size(); i++ ) {
			
			currentElement = visibleElements.get(i);
			
			drawElement(currentElement);
			drawText(currentElement);
			
		}
		
		super.drawScreen(x, y, f);
		
	}
	
	private String getHoverText( int x, int y) {
		MenuElement hoveredElement = activeScreen.getElementContainingPoint(x, y);
		
		if ( hoveredElement == null ) { return ""; }
		
		hoveredElement.setColor(hoveredElement.getHoverColor());
		
		return hoveredElement.getHoverText();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	protected void keyTyped(char par1, int par2)
	{
		 if (par2 == 1 || par2 == this.mc.gameSettings.keyBindInventory.getKeyCode())
		 {
			 this.mc.thePlayer.closeScreen();
		 }
	}
	
	public void initGui() {}
	public void drawElement(MenuElement e) { drawRect(e.getLeft(), e.getTop(), e.getRight(), e.getBottom(), e.getColor()); }
	public void drawText(MenuElement e) { drawCenteredString(fontRendererObj, e.getText(), e.getCenterX(), e.getCenterY() - 4, 0xffffff);}
	
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		
		if(mouseButton == 0) {
			checkElementClicked(mouseX, mouseY, mouseButton);
		}
		if (mouseButton == 1 && screenIndex == EDIT_POINT_SCREEN ) {
			Mouse.setCursorPosition(screenCenterX, screenCenterY);
			Mouse.setGrabbed(true);
		}

		Mouse.getEventButton();
		
	}
	
	 protected void mouseReleased(int mouseX, int mouseY, int state) {
        Mouse.setGrabbed(false);
    }
	 
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		
		if( clickedMouseButton == 1 && screenIndex == EDIT_POINT_SCREEN ) {
			
			screens.get(EDIT_POINT_SCREEN).getElement(1).setVisibility(false);
			
			EntityPlayer p = Minecraft.getMinecraft().thePlayer; // Get reference to the player
			int previousMouseX = Mouse.getEventX(), // Get position of mouse
				previousMouseY = Mouse.getEventY();
			
			screenCenterX = this.mc.displayWidth / 2; // Update center of screen
			screenCenterY = this.mc.displayHeight / 2;
			
			
			Mouse.setCursorPosition(screenCenterX, screenCenterY); // Set mouse cursor in the middle of the screen
			
			int changeX = previousMouseX - screenCenterX,
				changeY = previousMouseY - screenCenterY;
			
			// Change view by the amount changed
			if ( p.rotationPitch - changeY / 5 <= 90 && p.rotationPitch - changeY >= -90){
				p.rotationPitch -= changeY / 5;
			}
			else {
				if ( p.rotationPitch > 90 ) {
					p.rotationPitch = 90;
				}
				if ( p.rotationPitch < -90 ) {
					p.rotationPitch = -90;
				}
			}
			
			p.rotationYaw += changeX / 5;
		}
		
	}
	
	private void checkElementClicked( int mouseX, int mouseY, int mouseButton ) {
		
		int buttonClicked = -1;
		
		MenuElement clickedElement = activeScreen.getElementContainingPoint(mouseX, mouseY);
		
		if ( clickedElement instanceof MenuButton ) {
			buttonClicked = clickedElement.getId();
			handleClick(buttonClicked);
		}
		
		
		
	}
	
	private void handleClick( int buttonClicked ) {
		EntityPlayer p = Minecraft.getMinecraft().thePlayer;
		
		if(buttonClicked < 100) { // If the button is part of the main menu
			switch(buttonClicked) {
				case -1: 
					return;
				case 0:
					mc.thePlayer.closeScreen();
					break;
				default:
					selectSubScreen(buttonClicked);
					break;
			}
		}
		else {
			switch(buttonClicked) {
				case 100: // Scroll Up
					scrollUp();
					break;
				case 101: // Scroll Down
					scrollDown();
					break;
				case 102: // Delete Point
					deleteSelectedPoint();
					break;
				case 103: // Edit Point
					editSelectedPoint(p);
					break;
				case 104: // Add point at position
					addPoint(p);
					break;
				case 105: // Clear list
					clearPath();
					break;
				case 154: // Save point
					Main.camPath.setPointAtPosition(new CameraPoint(p.posX, p.posY, p.posZ, p.rotationPitch, p.rotationYaw), pointSelected + pointList.getAmountScrolled());
					break;
				case 155: // Return to options screen
					setScreenAndSubscreen(OPTIONS_SCREEN, CAM_POINTS_SUBSCREEN);
					break;
				default:
					this.pointSelected = buttonClicked - 1000;
					this.pointList.selectElement(pointSelected, MENU_BUTTON_BACKGROUND, MENU_BUTTON_SELECTED);
					addScrollControls();
					break;
			}
		}
	}
	
	// TODO Refactor and make easier to read, as well as comment
	
	private void selectSubScreen( int index ) {
		this.subScreenIndex = index;
		activeScreen = screens.get(screenIndex).getSubScreen(index);
		for( int i = 0; i < this.screens.get(screenIndex).getNumberOfElements(); i++ ) {
			this.screens.get(screenIndex).getElement(i).setDefaultColor(MENU_BUTTON_BACKGROUND);
		}
		this.screens.get(screenIndex).getElement(index).setDefaultColor(MENU_BUTTON_SELECTED);
	}
	
	private void scrollUp() {
		pointList.scrollUp();
		if ( this.pointSelected >= this.pointList.getAmountScrolled() + this.pointList.getNumVisible() ) {
			// Check to see if the selected element is out of context
			this.pointSelected -= 1;
			this.pointList.selectElement(pointSelected, MENU_BUTTON_BACKGROUND, MENU_BUTTON_SELECTED);
		}
		activeScreen.setElements(pointList.getVisibleElements());
		addScrollControls();
	}
	
	private void scrollDown() {
		if ( this.pointSelected == this.pointList.getAmountScrolled() ) {
			// Check to see if the selected element is out of context
			this.pointSelected += 1;
			this.pointList.selectElement(pointSelected, MENU_BUTTON_BACKGROUND, MENU_BUTTON_SELECTED);
		}
		pointList.scrollDown();
		activeScreen.setElements(pointList.getVisibleElements());
		addScrollControls();
	}
	
	private void deleteSelectedPoint() {
		if( Main.camPath.getSize() > 0 ){
			Main.camPath.removePoint(this.pointSelected);
		}
		updatePoints();
		if ( Main.camPath.getSize() > 0 ) {
			if ( this.pointSelected >= Main.camPath.getSize() - this.pointList.getAmountScrolled() || this.pointSelected > Main.camPath.getSize() - 1 ) {
				// If the point is greater than the number of elements or would be offscreen
				pointList.scrollUp();
				this.pointSelected -= 1;
			}
		}
		this.pointList.selectElement(pointSelected, MENU_BUTTON_BACKGROUND, MENU_BUTTON_SELECTED);
		activeScreen.setElements(pointList.getVisibleElements());
		addScrollControls();
	}
	
	private void editSelectedPoint( EntityPlayer p ) {
		// Go to point
		CameraPoint edit = Main.camPath.getPoint(pointSelected);
		p.setPositionAndRotation(edit.x, edit.y, edit.z, edit.camYaw, edit.camPitch);
		// Open edit point screen
		activeScreen = screens.get(EDIT_POINT_SCREEN).getSubScreen(BLANK_SUBSCREEN);
		this.screenIndex = EDIT_POINT_SCREEN;
		this.subScreenIndex = BLANK_SUBSCREEN;
	}
	
	private void addPoint( EntityPlayer p ) {
		pointSelected += 1;
		if ( Main.camPath.getSize() == 0 ) {
			this.pointSelected = 0;
		}
		Main.camPath.addPointAtPosition(new CameraPoint(p.posX, p.posY, p.posZ, p.rotationPitch, p.rotationYaw), pointSelected);
		updatePoints();
		activeScreen.setElements(pointList.getVisibleElements());
		this.pointList.selectElement(pointSelected, MENU_BUTTON_BACKGROUND, MENU_BUTTON_SELECTED);
		addScrollControls();
	}
	
	private void clearPath() {
		Main.camPath.clearPath();
		updatePoints();
		this.pointSelected = 0;
		screens.get(OPTIONS_SCREEN).getSubScreen(CAM_CONTROLS_SUBSCREEN);
		activeScreen.setElements(pointList.getVisibleElements());
		addScrollControls();
	}
	
	// TODO Come up with a better name 
	
	private void setScreenAndSubscreen( int screen, int sub ) {
		this.subScreenIndex = screen;
		this.screenIndex = sub;
		activeScreen = screens.get(screenIndex).getSubScreen(subScreenIndex);
	}
	// TODO have it import these from a file
	
	private void initializeElements() {
		//Initialize all Screens
		screens.put(OPTIONS_SCREEN, new Screen(pointList.getVisibleElements()));
		screens.put(EDIT_POINT_SCREEN, new Screen());
		
		Screen options = screens.get(OPTIONS_SCREEN);
		Screen editPoints = screens.get(EDIT_POINT_SCREEN);
		
		//Add elements to subscreens
		options.addElement(new MenuElement(5, 200, 330, 30, 0xaf000000, "Display Box"));
		options.addElement(new MenuButton(CAM_POINTS_SUBSCREEN, 10, 50, 110, 25, MENU_BUTTON_BACKGROUND, MENU_BUTTON_HOVER, "Cam Points", "View and edit all points currently set"));
		options.addElement(new MenuButton(CAM_CONTROLS_SUBSCREEN, 10, 80, 110, 25, MENU_BUTTON_BACKGROUND, MENU_BUTTON_HOVER, "Cam Controls", "Set hotkeys and edit camera controls"));
		options.addElement(new MenuButton(THIRD_PERSON_SUBSCREEN, 10, 110, 110, 25, MENU_BUTTON_BACKGROUND, MENU_BUTTON_HOVER, "Third Person", "Coming soon!"));
		options.addElement(new MenuButton(GLOBAL_OPTIONS_SUBSCREEN, 10, 140, 110, 25, MENU_BUTTON_BACKGROUND, MENU_BUTTON_HOVER, "Global Options", "Change global mod options"));
		options.addElement(new MenuButton(EXIT_MENU, 120, 175, 90, 20, MENU_BUTTON_BACKGROUND, MENU_BUTTON_HOVER, "Done", "Exit Menu"));
		options.addElement(new MenuElement(111, 10, 120 , 20, 0xff000000, "Camerify Mod"));
		options.addElement(new MenuElement(130, 50, 200 , 115, 0xaf000000));
		
		// Edit Points Screen
		editPoints.addElement(new MenuElement(5, 200, 330, 30, 0xaf000000, "Display Box"));
		editPoints.addElement(new MenuElement(46, 10, 240 , 20, MENU_BUTTON_HOVER, "Right click and drag for quick adjustment"));
		editPoints.addElement(new MenuButton(150, 0, 20, 20, 20, MENU_BUTTON_HOVER, "\u2191")); //UP
		editPoints.addElement(new MenuButton(151, 20, 40, 20, 20, MENU_BUTTON_HOVER, "\u2192")); //RIGHT
		editPoints.addElement(new MenuButton(152, 0, 60, 20, 20, MENU_BUTTON_HOVER, "\u2193")); //DOWN
		editPoints.addElement(new MenuButton(153, -20, 40, 20, 20, MENU_BUTTON_HOVER, "\u2190")); //LEFT
		editPoints.addElement(new MenuButton(154, 100, 175, 60, 20, MENU_BUTTON_HOVER, MENU_BUTTON_HOVER, "Update", "Update point to current position"));
		editPoints.addElement(new MenuButton(155, 170, 175, 60, 20, MENU_BUTTON_HOVER, MENU_BUTTON_HOVER, "Done", "Return to previous screen"));
		
		getPoints();
		
		options.addSubScreen( new Screen() ); // Blank subscreen
		options.addSubScreen( new Screen( pointList.getVisibleElements() )); // Cam points screen
		options.addSubScreen( new Screen() ); // Cam Controls
		options.addSubScreen( new Screen() ); // 3rd person
		options.addSubScreen( new Screen() ); // Global options
		
		editPoints.addSubScreen( new Screen() ); // Blank screen
		
		if(Main.camPath.getSize() > 0) {
			this.pointSelected = 0;
			this.pointList.selectElement(pointSelected, MENU_BUTTON_BACKGROUND, MENU_BUTTON_SELECTED);
		}
		
		screens.get(OPTIONS_SCREEN).getElement(0).setVisibility(false);
		screens.get(OPTIONS_SCREEN).getElement(1).setDefaultColor(MENU_BUTTON_SELECTED);
		addScrollControls();
		
		activeScreen = screens.get(OPTIONS_SCREEN).getSubScreen(1);
	}
	
	private void addScrollControls() {
		Screen camPoints = screens.get(OPTIONS_SCREEN).getSubScreen(CAM_POINTS_SUBSCREEN);
		
		MenuElement addPointButton = new MenuButton(104, 330, 78, 20, 20, ADD_BUTTON_BACKGROUND, ADD_BUTTON_HOVER, "+", "Add point at position");
		MenuElement deletePointButton = new MenuButton(102, 330, 118, 20, 20, DELETE_BUTTON_BACKGROUND, DELETE_BUTTON_HOVER, "x", "Delete selected point");
		MenuElement editPointButton = new MenuButton(103, 330, 98, 20, 20, MENU_BUTTON_BACKGROUND, MENU_BUTTON_HOVER, "\u270E", "Edit selected point");
		MenuElement scrollUpButton = new MenuButton(100, 330, 58, 20, 20, 0xff000000, "\u2191");
		MenuElement scrollDownButton = new MenuButton(101, 330, 138, 20, 20, 0xff000000, "\u2193");
		MenuElement clearButton = new MenuButton(105, 290, 165, 40, 20, MENU_BUTTON_BACKGROUND, MENU_BUTTON_HOVER, "Clear", "Clear all points");
		
		
		camPoints.addElement(addPointButton);
		if( this.pointSelected >= 0 && Main.camPath.getSize() > 0 ) { 
			// Show delete and edit buttons if a point is selected
			camPoints.addElement(deletePointButton);
			camPoints.addElement(editPointButton);
		}
		if( Main.camPath.getSize() > this.pointList.getNumVisible() ) {
			// Add scroll controls if the number of cam points exceeds the number that there is room for on the lis
			if (this.pointList.getAmountScrolled() > 0 ) {
				camPoints.addElement(scrollUpButton);
			}
			if ( this.pointList.getAmountScrolled() < Main.camPath.getSize() - this.pointList.getNumVisible()) {
				camPoints.addElement(scrollDownButton);
			}			
		}
		if ( Main.camPath.getSize() > 0 ) {
			camPoints.addElement(clearButton);
		}
	}
	
	private void getPoints() { 
		// Get all the points in the camera path and create buttons for them
		pointList.clearList();
		MenuElement visiblePoint;
		for( int i = 0, l = Main.camPath.getSize(); i < l; i++ ) {
			visiblePoint = new MenuButton(1000 + i , 135, (i * 22) + 53, 190, 20, 0xff000000, Main.camPath.getPointString(i));
			pointList.addElement(visiblePoint);
		}
	}
	
	private void updatePoints() {
		// Update the point positions based on the ones which are visible
		pointList.clearList();
		for( int i = 0, l = Main.camPath.getSize(); i < l; i++ ) {
			pointList.addElement(new MenuButton(1000 + i, 135, (i * 22) - pointList.getAmountScrolled() * 22 + 53, 190, 20, 0xff000000, Main.camPath.getPointString(i)));
		}
	}
	
	
}
