package com.ericnewcombe.camerify.camera;

import java.util.ArrayList;

import com.ericnewcombe.camerify.chat.ChatHandler;

import net.minecraft.util.math.Vec3d;

public class CameraPathHandler {
	
	/** Tracks whether the player is in motion through the points */
	private static boolean moving = false;
	
	/**  The current frame that is being displayed in the path */
	private static int currentFrame = 0;
	
	/** The time in milliseconds to travel the path */
	private static int travelTime = 200;
	
	/** List of all {@link CameraPoint}s contained in the path */
	private static ArrayList<CameraPoint> path;
	
	/**
	 * Starts the travelling of the {@link CameraPoint}. If there are points to traverse then it displays a message that it has started to the user, then removes control
	 * and begins to traverse points. If there is no points to traverse, or it it already travelling then it displays an appropriate message
	 * @return Whether the camera successfullly started travelling or not
	 */
	
	public static boolean startTravel() {
		
		updatePoints();
		
		if ( Camera.getSize() == 0 ) {
			ChatHandler.sendMessage("No points to travel.");
			return false;
		}
		if ( !moving ) {
			ChatHandler.sendMessage("Travelling started!");
			moving = true;
			currentFrame = 0;
			return true;
		}
		else {
			ChatHandler.sendMessage("Already travelling.");
			return false;
		}
		
	}
	

	public static boolean endTravel() {
		if ( moving ) {
			ChatHandler.sendMessage("Travelling ended.");
			moving = false;
			return true; // Success
		}
		return false; // Not moving
	}
	
	private static Vec3d calculatePositionInPath ( double pointPositionInPath) {
		
		if ( Camera.getSize() == 1 ) { return generateVecFromPoint (path.get(0)); }
		if ( Camera.getSize() == 2 ) { return calculateLinearPosition( pointPositionInPath ); }
		if ( Camera.getSize() == 3 ) { return calculateQuadraticPosition ( pointPositionInPath ); }
		else { return calculateSplinePosition ( pointPositionInPath ); }
		
	}

	/**
	 * Creates a {@link Vec3d} representation of a {@link CameraPoint}
	 * @param p {@link CameraPoint} to be represented
	 * @return {@link Vec3d} representation of the {@link CameraPoint}
	 */
	public static Vec3d generateVecFromPoint(CameraPoint p) {
		return new Vec3d(p.x, p.y, p.z);
	}
	
	public static Vec3d calculateSplinePosition(double pointPositionInPath) {
		
		Vec3d pos;
		
		pointPositionInPath = Math.ceil( pointPositionInPath * 1000 ) / 1000;
		ArrayList <CameraPoint> path = Camera.getPath();
		
		// For debugging purposes
		ChatHandler.sendMessage(String.valueOf(pointPositionInPath));
		
		double x = 0,
			   y = 0,
			   z = 0;
		
		int startPoint = (int) Math.floor(pointPositionInPath);
		
		
		if ( startPoint >= Camera.getSize() - 4 ) { startPoint = Camera.getSize() - 4; }
		
		// TODO write travelling logic here
		
		
		pos = new Vec3d(x, y, z);
		
		return pos;
	}

	public static Vec3d calculateQuadraticPosition(double pointPositionInPath) {
		Vec3d pos;
		
		pointPositionInPath = Math.ceil( pointPositionInPath * 1000 ) / 1000;
		ArrayList <CameraPoint> path = Camera.getPath();
		
		// For debugging purposes
		ChatHandler.sendMessage(String.valueOf(pointPositionInPath));
		
		double x = 0,
			   y = 0,
			   z = 0;
		
		int startPoint = (int) Math.floor(pointPositionInPath);
		
		
		if ( startPoint >= Camera.getSize() - 4 ) { startPoint = Camera.getSize() - 4; }
		
		// TODO write travelling logic here
		
		
		pos = new Vec3d(x, y, z);
		
		return pos;
	}

	/**
	 * Calculates the position between two points in a linear way
	 * @param pointPositionInPath double between 0 and 1 representing the percentage completed between two points
	 * @return {@link Vec3d} point of where the player should be at the particular point in the path
	 */
	public static Vec3d calculateLinearPosition(double pointPositionInPath) {
		Vec3d pos;
		
		pointPositionInPath = Math.ceil( pointPositionInPath * 1000 ) / 1000;
		ArrayList <CameraPoint> path = Camera.getPath();
		
		// For debugging purposes
		ChatHandler.sendMessage(String.valueOf(pointPositionInPath));
		
		double x = 0,
			   y = 0,
			   z = 0;
		
		int startPoint = (int) Math.floor(pointPositionInPath);
		
		// TODO write travelling logic here
		
		// If it is at the beginning or the end of the path just return that point, else set the values as a combination of the two points
		// based on the amount between the two points has been travelled
		
		x = path.get(0).x * ( 1 - pointPositionInPath ) + path.get(1).x * pointPositionInPath;
		y = path.get(0).y * ( 1 - pointPositionInPath ) + path.get(1).y * pointPositionInPath;
		z = path.get(0).z * ( 1 - pointPositionInPath ) + path.get(1).z * pointPositionInPath;
		
		pos = new Vec3d(x, y, z);
		
		return pos;
	}

	private static float[] calculateHeadDirectionOnPath ( double pointPositioninPath ) {
		// TODO write the pitch, yaw transition logic here
		return new float[2];
	}
	
	/**
	 * Updates the player position and view angles based on the the amount of the path that has been travelled
	 * then updates the values increasing the amount of time passed
	 */
	public static void tick() {
		// TODO write the logic to update camera at every tick
		if ( moving ) {
			if ( Camera.getSize() > 1 ) {
				
			}
		}
	}
	
	/**
	 * sets the time that it will take to traverse the path in milliseconds
	 * @param milliseconds Number of milliseconds that it will take to traverse the path
	 */
	public static void setTravelTime( int milliseconds ) {
		travelTime = milliseconds;
	}
	
	/** updates the Camera path to the most recent points */

	private static void updatePoints() {
		path = Camera.getPath();
	}
}
