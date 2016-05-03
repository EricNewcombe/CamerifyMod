package com.ericnewcombe.camerify.camera;

import java.util.ArrayList;

import com.ericnewcombe.camerify.chat.ChatHandler;

/**
 * Object used to manage a collection of {@link CameraPoint}s representing a path in which the camera is to follow
 * 
 * @author Eric
 */
public class Camera {
	
	private static Camera instance = getInstance();
	
	private static ArrayList<CameraPoint> path;
	
	// Private initializer for singleton pattern
	private Camera() {
		path = new ArrayList<CameraPoint>();
	}
	
	public static Camera getInstance() {
		
		if ( instance == null ) {
			instance = new Camera();
		}
		
		return instance;
	}
	
	/**
	 * Clears the camera path of points
	 * @return status of the clearing of the path
	 */
	public static boolean clearPath() {
		path.clear();
		ChatHandler.sendMessage("Camera cleared.");
		return true; // TODO add logic to determine if the path can be cleared or not
	}
	
	/**
	 * Adds a {@link CameraPoint} on the path at the end of {@link #path}
	 * @param c {@link CameraPoint} which is to be added
	 * @return Whether the point was added successfully or not
	 */
	public static boolean addPoint( CameraPoint c ) {
		path.add(c);
		ChatHandler.sendMessage("Point Registered. " + path.size() + " in queue.");
		return true; // TODO add logic to determine if a point is valid or not
	}
	
	/**
	 *  Adds a {@link CameraPoint} on the path at a specific position as long as it is within the bounds of {@link #path}'s size
	 * @param c {@link CameraPoint} which is to be added
	 * @param pos Position of where the point should be added
	 * @return Whether the point was added successfully at that position or not
	 */
	public static boolean addPointAtPosition( CameraPoint c, int pos ) {
		
		// Check to see if the intended position is in the scope of the number of points in the path already
		if ( pos > path.size() || pos < 0 ) {
			ChatHandler.sendMessage("Position out of scope of placed points.");
			return false;
		}
		
		// add the point to the position
		path.add(pos, c);
		
		ChatHandler.sendMessage("Point Registered. " + (pos + 1) + " in queue");
		
		return true; // TODO add logic to return false if out of bounds
	}
	
	/**
	 * Sets the {@link CameraPoint} at the position to the {@link CameraPoint} provided so long as it is within the bounds of the {@link #path}'s size
	 * @param c New camera point to replace the old one
	 * @param pos the position at which the new camerapoint should overwrite
	 * @return Whether the point was successfully overridden or not
	 */
	
	public static boolean setPointAtPosition( CameraPoint c, int pos ) {
		
		if ( pos > path.size() - 1 || pos < 0 ) { return false; }
		path.set(pos, c);
		return true;
		
	}
	
	/**
	 * Prints out all {@link CameraPoint}s to the console
	 */
	
	public static void printPath() {
		
		for ( int i = 0, l = path.size(); i < l; i++ ) {
			System.out.println(path.get(i).toString());
		}
		
	}
	
	/**
	 * Creates a string representation of all {@link CameraPoint}s and stores it in an array
	 * @return An array of the string representations of each point
	 */
	
	public static String[] getPathString() {
		
		String[] points = new String[path.size()];
		
		for( int i = 0, l = path.size(); i < l; i++ ) {
			points[i] = path.get(i).toString();
		}
		
		return points;
		
	}
	
	/**
	 * Removes a {@link CameraPoint} at a given index
	 * @param i index of the point to be removed
	 * @return The {@link CameraPoint} which was removed
	 */
	public static CameraPoint removePoint( int i ) { 
		if ( i > path.size() - 1 || i < 0 ) { return null; }
		return path.remove(i);
	}
	
	public static int getSize() { return path.size(); }
	public static CameraPoint getPoint( int i ) { return path.get(i); }
	public static String getPointString (int i ) { return path.get(i).toString(); }
	public static ArrayList<CameraPoint> getPath() { return path; }
	
	
}
