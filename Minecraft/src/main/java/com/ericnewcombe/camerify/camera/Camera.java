package com.ericnewcombe.camerify.camera;

import java.util.ArrayList;

import com.ericnewcombe.camerify.chat.ChatHandler;

/*
 * Camera object which store points of which the user has input to be placed onto the path for the camera to follow
 * Camera will also calculate the point it will be at on the path based on the time elapsed
 */

public class Camera {
	
	private static Camera instance = getInstance();
	
	private static ArrayList<CameraPoint> path;
	
	private Camera() {
		path = new ArrayList<CameraPoint>();
		
	}
	
	public static Camera getInstance() {
		
		if ( instance == null ) {
			instance = new Camera();
		}
		
		return instance;
	}
	
	
	public static boolean clearPath() {
		path.clear();
		ChatHandler.sendMessage("Camera cleared.");
		return true; // TODO add logic to determine if the path can be cleared or not
	}
	
	public static boolean addPoint( CameraPoint c ) {
		path.add(c);
		ChatHandler.sendMessage("Point Registered. " + path.size() + " in queue.");
		return true; // TODO add logic to determine if a point is valid or not
	}
	
	public static boolean addPointAtPosition( CameraPoint c, int pos ) {
		
		// Check to see if the intended position is in the scope of the number of points in the path already
		if ( pos > path.size() - 1 || pos < 0 ) {
			ChatHandler.sendMessage("Position out of scope of placed points.");
			return false;
		}
		
		// add the point to the position
		path.add(pos, c);
		
		ChatHandler.sendMessage("Point Registered. " + (pos + 1) + " in queue");
		
		return true; // TODO add logic to return false if out of bounds
	}
	
	public static boolean setPointAtPosition( CameraPoint c, int pos ) {
		
		if ( pos > path.size() - 1 || pos < 0 ) { return false; }
		path.set(pos, c);
		return true;
		
	}
	
	public static void printPath() {
		
		for ( int i = 0, l = path.size(); i < l; i++ ) {
			System.out.println(path.get(i).toString());
		}
		
	}
	
	
	
	public static String[] getPathString() {
		
		String[] points = new String[path.size()];
		
		for( int i = 0, l = path.size(); i < l; i++ ) {
			points[i] = path.get(i).toString();
		}
		
		return points;
		
	}
	
	public static int getSize() { return path.size(); }
	public static CameraPoint getPoint( int i ) { return path.get(i); }
	public static String getPointString (int i ) { return path.get(i).toString(); }
	public static ArrayList<CameraPoint> getPath() { return path; }
	
	public static boolean removePoint( int i ) { 
		if ( i > path.size() - 1 || i < 0 ) { return false; }
		path.remove(i);
		return true;
	}
}
