package com.ericnewcombe.camerify.camera.gui;

import java.util.ArrayList;

/**
 * 
 * Screen which is a structure to contain all of the screen elements which correspond to that screen
 * @author Eric
 *
 */
public class Screen {
	
	/** The parent screen of this screen */
	public Screen parent;
	
	/** collection of subscreens of this screen */
	private ArrayList<Screen> subScreens;
	
	/**  Collection of all of the screenelements to be displayed on this screen */
	private ArrayList<MenuElement> screenElements;
	
	public Screen(){
		this( new ArrayList<MenuElement>() );
	}

	public Screen( ArrayList<MenuElement> elements ) {
		parent = null;
		subScreens = new ArrayList<Screen>();
		screenElements = elements;
	}
	
	/**
	 * Adds a sub screen as a direct child to this screen
	 * @param s - Screen to be made a child screen to this screen
	 */
	public void addSubScreen( Screen s ) {
		s.parent = this;
		subScreens.add(s);
	}
	
	/**
	 * Adds a MenuElement to the {@linkplain #screenElements}
	 * @param newElement - Element to be added to the list of elements to be displayed on this screen
	 */
	public void addElement ( MenuElement newElement ) {
		screenElements.add(newElement);
	}
	
	/**
	 * Sets {@linkplain #screenElements} to the collection of menuelements supplied to the function
	 * @param elements - Arraylist of MenuElements to be displayed on this screen
	 */
	public void setElements ( ArrayList<MenuElement> elements ) {
		screenElements = elements;
	}
	
	/**
	 * Gets the hover text box at the highest parent screen for text to be displayed on
	 * @return - The {@link MenuElement} that is the hover text box to display text in
	 */
	
	public MenuElement getHoverTextBox() {
		if ( parent != null ) { return parent.getHoverTextBox(); }
		if ( screenElements.size() == 0 ) { return null; }
		return screenElements.get(0);
	}
	
	/**
	 * Gets a {@link MenuElement} at a specified index
	 * @param elementIndex - Index at which to grab the element
	 * @return - {@link MenuElement} at the specified index or null if not found
	 */
	public MenuElement getElement( int elementIndex ) {
		if ( elementIndex > screenElements.size() ) { return null; }
		return screenElements.get(elementIndex);
	}
	
	/**
	 * Loops through {@link #screenElements} and determines which {@link MenuElement} contains the x, y point specified
	 * @param x x position of the point to search
	 * @param y y position of the point to search
	 * @return The {@link MenuElement} that has the point contained or null if none
	 */
	
	public MenuElement getElementContainingPoint ( int x, int y ) {
		MenuElement containingElement = null;
		
		if ( parent != null ) { containingElement = parent.getElementContainingPoint(x, y); }
		
		MenuElement currentElement;
		ArrayList<MenuElement> visibleElements = getVisibleElements();
		
		for ( int i = 0; i < visibleElements.size(); i++ ) {
			currentElement = visibleElements.get(i);
			
			if ( currentElement.isPointInElement(x, y, MenuElement.xOffset, MenuElement.yOffset) ) {
				containingElement = currentElement;
			}
			
		}
		
		return containingElement;
	}
	
	/**
	 * Gets all visible {@link MenuElement}s from parent screen then loops through all of the elements on this screen and adding them to a return arraylist if they are flagged as visible
	 * @return {@link ArrayList} of all {@link MenuElement} that are visible
	 */
	public ArrayList<MenuElement> getVisibleElements() {
		
		ArrayList visibleElements = new ArrayList<MenuElement>();
		
		if ( parent != null ) { visibleElements = parent.getVisibleElements(); }
		
		MenuElement currentElement;
		
		for ( int i = 0; i < screenElements.size(); i++ ) {
			
			currentElement = screenElements.get(i);
			if ( currentElement.isVisible() ) {
				visibleElements.add(currentElement);
			}
			
		}
		
		return visibleElements;
	}
	
	/**
	 * Gets the subscreen at the specified index
	 * @param subScreenIndex - Index at which to get the subscreen
	 * @return Null if no element found or {@link Screen} at the index
	 */
	
	public Screen getSubScreen( int subScreenIndex ) {
		if ( subScreenIndex > subScreens.size() ) { return null; }
		return subScreens.get(subScreenIndex);
	}
	
	/**
	 * @return Size of {@link #screenElements}
	 */
	public int getNumberOfElements() {
		return screenElements.size();
	}
	
}
