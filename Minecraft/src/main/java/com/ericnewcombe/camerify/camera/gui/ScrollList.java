package com.ericnewcombe.camerify.camera.gui;

import java.util.ArrayList;

/**
 * 
 * A structure in which to store elements that will be displayed in a list based on the amount that the list has been scrolled up or down
 * @author Eric
 *
 */

public class ScrollList {
	
	/** The amount that the list has been scrolled */
	protected int amountScrolled;
	
	/** The number of elements to be displayed on the creating of the list */
	protected int numVisible;
	
	/** The number of pixels to put in between elements */
	protected int scrollSpacing;
	
	/** Holds all the elements which will be displayed in the list */
	protected ArrayList<MenuElement> elements;
	
	public ScrollList( ArrayList<MenuElement> elements, int numVisible, int scrollSpacing ) {
		this.amountScrolled = 0;
		this.elements = elements;
		this.numVisible = numVisible;
		this.scrollSpacing = scrollSpacing;
	}
	
	public ScrollList(int numVisible, int scrollSpacing) {
		this(new ArrayList<MenuElement>(), numVisible, scrollSpacing);
	}
	
	
	/**
	 * Finds all visible elements in {@link #elements} with the current scroll amount
	 * @return - ArrayList of menuelements which are visible at the current scroll amount
	 */
	public ArrayList<MenuElement> getVisibleElements() {
		//Returns an arraylist of the currently visible elements of the list
		ArrayList<MenuElement> visibleElements = new ArrayList<MenuElement>();
		
		for( int i = 0; i < numVisible; i++ ) {
			if(i + amountScrolled < elements.size() ) {
				visibleElements.add(elements.get(i + amountScrolled));
			}
		}
		
		return visibleElements;
		
	}
	
	/**
	 * Scrolls up the list
	 */
	public void scrollUp() {
		if( this.amountScrolled - 1 >= 0 ) {
			this.amountScrolled -= 1;
			moveElementYPositions(1);
		}
	}
	
	/**
	 * Scrolls down the list
	 */
	public void scrollDown() {
		if( this.amountScrolled < this.elements.size() - this.numVisible ) {
			this.amountScrolled += 1;
			moveElementYPositions(-1);
		}
	}
	
	/**
	 * Loops through {@link #elements} setting their default colour back to the one supplied and setting the default colour of the 
	 * selected element as the selected colour supplied
	 * @param index - Index of the element to be selected
	 * @param defaultColor - The default background colour of the element
	 * @param selectedColor - The colour that the selected element will be
	 */
	public void selectElement(int index, int defaultColor, int selectedColor) {
		if ( index < 0 || index > this.elements.size() - 1 ) { return; }
		for ( int i = 0; i < this.elements.size(); i++ ) {
			if ( i == index ) {
				this.elements.get(i).setDefaultColor(selectedColor);
			}
			else {
				this.elements.get(i).setDefaultColor(defaultColor);
			}
		}
	}
	
	/**
	 * Adds an element to the list
	 * @param e - Element to be added to the end of the list
	 */
	public void addElement(MenuElement e) { this.elements.add(e); }
	
	/**
	 * @return {@link #numVisible}
	 */
	
	public int getNumVisible() { return this.numVisible; }
	
	/**
	 * @return {@link #amountScrolled}
	 */
	public int getAmountScrolled() { return this.amountScrolled; }
	
	/**
	 * Clears {@linkplain #elements} and resets {@link #amountScrolled}
	 */
	public void clearList() { 
		this.elements.clear();
		this.amountScrolled = 0;
	}
	
	/**
	 * Scrolls the list up or down
	 * @param direction - The direction to be scrolled (1 is up, -1 is down)
	 */
	public void moveElementYPositions(int direction) {
		for ( int i = 0; i < this.elements.size(); i++ ) {
			this.elements.get(i).changePosY(direction * this.scrollSpacing);
		}
	}
	
}
