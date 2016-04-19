package com.ericnewcombe.camerify.camera;

import java.util.ArrayList;

import com.ericnewcombe.camerify.chat.ChatHandler;

/*
 * Camera object which store points of which the user has input to be placed onto the path for the camera to follow
 * Camera will also calculate the point it will be at on the path based on the time elapsed
 */

public class Camera {
	
	private static Camera instance = null;
	
	private boolean travelling;
	private static ArrayList<CameraPoint> path;
	
	private Camera() {
		
		this.travelling = false;
		this.path = new ArrayList<CameraPoint>();
		
	}
	
	public static Camera getInstance() {
		
		if ( instance == null ) {
			instance = new Camera();
		}
		
		return instance;
	}
	
	
	public static void clearPath() {
		path.clear();
		ChatHandler.sendMessage("Camera cleared.");
	}
	
	public static void addPoint( CameraPoint c ) {
		path.add(c);
		ChatHandler.sendMessage("Point Registered. " + path.size() + " in queue.");
	}
	
	public static void addPointAtPosition( CameraPoint c, int pos ) {
		
		// Check to see if the intended position is in the scope of the number of points in the path already
		int placedPosition = pos > path.size() - 1 || pos < 0 ? path.size() : pos;
		
		// add the point to the position
		path.add(placedPosition, c);
		
		ChatHandler.sendMessage("Point Registered. " + (placedPosition + 1) + " in queue");
		
	}
	
	public void setPointAtPosition( CameraPoint c, int pos ) {
		
		if ( pos > path.size() - 1 ) { return; }
		path.set(pos, c);
		
	}
	
	public void printPath() {
		
		for ( int i = 0, l = path.size(); i < l; i++ ) {
			System.out.println(path.get(i).toString());
		}
		
	}
	
	public int getSize() {
		return path.size();
	}
	
	public String[] getPathString() {
		
		String[] points = new String[path.size()];
		
		for( int i = 0, l = path.size(); i < l; i++ ) {
			points[i] = path.get(i).toString();
		}
		
		return points;
		
	}
	
}

/*
 * Represents a position in the minecraft world with x,y,z coords and pitch and roll
 */
class CameraPoint {
	
	public double x, y, z;
	public float camPitch, camYaw;
	
	public CameraPoint( double x, double y, double z, float camPitch, float camYaw  ){
		this.x = x;
		this.y = y;
		this.z = z;
		this.camPitch = camPitch;
		this.camYaw = camYaw;
	}
	
	public String toString() { return "x: " + (int)x + " y: " + (int)y + " z: " + (int)z; }
	
}
