package com.ericnewcombe.camerify.camera;

import com.ericnewcombe.camerify.chat.ChatHandler;

public class CameraPathHandler {
	
	private static Camera camera = null;
	private static boolean moving = false;
	private static int currentFrame = 0;
	private static int travelTime = 200;
	
	public static boolean startTravel() {
		
		camera = Camera.getInstance();
		
		if ( camera.getSize() == 0 ) {
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
	
	private static void calculatePositionInPath ( int time ) {
		// TODO write travelling logic here
	}
	
	private static float[] calculateHeadDirectionOnPath ( int time ) {
		// TODO write the pitch, yaw transition logic here
		return new float[2];
	}
	
	public static void tick() {
		// TODO write the logic to update camera at every tick
	}
	
	public static void setTravelTime( int milliseconds ) {
		travelTime = milliseconds;
	}
}
