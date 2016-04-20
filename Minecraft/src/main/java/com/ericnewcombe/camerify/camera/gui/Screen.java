package com.ericnewcombe.camerify.camera.gui;

import java.util.ArrayList;

public class Screen {
	
	public Screen parent;
	private ArrayList<Screen> subScreens;
	private ArrayList<MenuElement> screenElements;
	
	public Screen(){
		this( new ArrayList<MenuElement>() );
	}

	public Screen( ArrayList<MenuElement> elements ) {
		parent = null;
		subScreens = new ArrayList<Screen>();
		screenElements = elements;
	}
	
	public void addSubScreen( Screen s ) {
		s.parent = this;
		subScreens.add(s);
	}
	
	public void addElement ( MenuElement newElement ) {
		screenElements.add(newElement);
	}
	
	public void setElements ( ArrayList<MenuElement> elements ) {
		screenElements = elements;
	}
	
	public MenuElement getHoverTextBox() {
		if ( parent != null ) { return parent.getHoverTextBox(); }
		if ( screenElements.size() == 0 ) { return null; }
		return screenElements.get(0);
	}
	
	public MenuElement getElement( int elementIndex ) {
		if ( elementIndex > screenElements.size() ) { return null; }
		return screenElements.get(elementIndex);
	}
	
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
	
	public Screen getSubScreen( int subScreenIndex ) {
		if ( subScreenIndex > subScreens.size() ) { return null; }
		return subScreens.get(subScreenIndex);
	}
	
	public int getNumberOfElements() {
		return screenElements.size();
	}
	
}
