package com.ericnewcombe.camerify.camera.gui;

import java.util.ArrayList;

public class ScrollList {
	
	protected int amountScrolled, numVisible, scrollSpacing;
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
	
	public void scrollUp() {
		if( this.amountScrolled - 1 >= 0 ) {
			this.amountScrolled -= 1;
			moveElementYPositions(1);
		}
	}
	
	public void scrollDown() {
		if( this.amountScrolled < this.elements.size() - this.numVisible ) {
			this.amountScrolled += 1;
			moveElementYPositions(-1);
		}
	}
	
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
	
	public void addElement(MenuElement e) { this.elements.add(e); }
	public int getNumVisible() { return this.numVisible; }
	public int getAmountScrolled() { return this.amountScrolled; }
	public void clearList() { 
		this.elements.clear();
		this.amountScrolled = 0;
	}
	
	public void moveElementYPositions(int direction) {
		for ( int i = 0; i < this.elements.size(); i++ ) {
			this.elements.get(i).changePosY(direction * this.scrollSpacing);
		}
	}
	
}
