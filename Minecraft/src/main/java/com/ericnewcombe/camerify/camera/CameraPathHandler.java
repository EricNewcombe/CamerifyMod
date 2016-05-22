package com.ericnewcombe.camerify.camera;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.ericnewcombe.camerify.chat.ChatHandler;

import net.minecraft.client.Minecraft;
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
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			
			Vec3d startingPosition = generateVecFromPoint( path.get(0) );
			float[] startingAngles = calculateHeadDirectionOnPath(0);
			
			setPlayerPositionAndAngles(startingPosition, startingAngles);
			
			
			ChatHandler.sendMessage("Travelling started! " + dateFormat.format(cal.getTime()));
			
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
			
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			
			ChatHandler.sendMessage("Travelling ended. " + dateFormat.format(cal.getTime()));
			
			moving = false;
			return true; // Success
		}
		return false; // Not moving
	}
	
	private static Vec3d calculateMovementVector( double pointPositionInPath ) {
		
		switch ( Camera.getSize() ) {
			case 1:
				return generateVecFromPoint( path.get(0) );
				// TODO add other options
			default:
				return calculateLinearMovementVector ( pointPositionInPath );
		}
	}
	
	/**
	 * Calculates the movement vector that will bring the player to the next point in the path
	 * @param pointPositionInPath Number of points and how far in between two current points the player is
	 * @return {@link Vec3d} representation of the amount the player has to move
	 */
	
	public static Vec3d calculateLinearMovementVector ( double pointPositionInPath ) {
		Vec3d movementVector;
		
		double xChange = 0,
			   yChange = 0,
			   zChange = 0;
		
		int start = (int) pointPositionInPath;
		if ( start >= path.size() - 2 ) { start = path.size() - 2; }
		
		double timeBetweenPoints = travelTime / path.size();
		
		xChange = ( path.get(start + 1).x - path.get(start).x ) / timeBetweenPoints;
		yChange = ( path.get(start + 1).y - path.get(start).y ) / timeBetweenPoints;
		zChange = ( path.get(start + 1).z - path.get(start).z ) / timeBetweenPoints;
		
		movementVector = new Vec3d(xChange, yChange, zChange );
		
		return movementVector;
	}
	
	public static Vec3d calculateQuadraticMovementVector ( double pointPositionInPath ) {
		Vec3d movementVector;
		
		double xChange = 0,
			   yChange = 0,
			   zChange = 0;
		
		movementVector = new Vec3d(xChange, yChange, zChange );
		
		return movementVector;
	}
	
	public static Vec3d calculateSplineMovementVector ( double pointPositionInPath ) {
		Vec3d movementVector;
		
		double xChange = 0,
			   yChange = 0,
			   zChange = 0;
		
		movementVector = new Vec3d(xChange, yChange, zChange );
		
		return movementVector;
	}
	/**
	 * Creates a {@link Vec3d} representation of a {@link CameraPoint}
	 * @param p {@link CameraPoint} to be represented
	 * @return {@link Vec3d} representation of the {@link CameraPoint}
	 */
	public static Vec3d generateVecFromPoint(CameraPoint p) {
		return new Vec3d(p.x, p.y, p.z);
	}
	
	/**
	 * Linearly calculates the position of the head based on the point in the path
	 * @param pointPositioninPath the point position in the path
	 * @return an array with the pitch and yaw in it
	 */

	public static float[] calculateHeadDirectionOnPath ( double pointPositioninPath ) {
		
		pointPositioninPath = Math.ceil(pointPositioninPath * 1000) / 1000;
		
		float pitch = 0, yaw = 0;
		
		int startPoint = (int) Math.floor(pointPositioninPath);
		
		if ( startPoint >= path.size() - 2 ) { startPoint = path.size() - 2; }
		
		// TODO write the pitch, yaw transition logic here
		
		double percentCompletedBetweenTwoPoints = pointPositioninPath - startPoint;
		
		pitch = (float) (path.get(startPoint).camPitch * ( 1 - percentCompletedBetweenTwoPoints ) + path.get(startPoint + 1).camPitch * percentCompletedBetweenTwoPoints );
		yaw = (float) (path.get(startPoint).camYaw * ( 1 - percentCompletedBetweenTwoPoints ) + path.get(startPoint + 1).camYaw * percentCompletedBetweenTwoPoints );
		
		float[] view = {pitch, yaw};
		
		return view;
		
	}
	
	/**
	 * Updates the player position and view angles based on the the amount of the path that has been travelled
	 * then updates the values increasing the amount of time passed
	 */
	public static void tick() {
		// TODO write the logic to update camera at every tick
		
		if ( moving ) {
			
			// The current frame is representative of how many ticks have passed. The travel time divided by number of points is the
			// number of milliseconds of travel in between points. Dividing the current frame by the number of milliseconds between each point
			// gives the number of points that have fully been travelled and how far in between points is left
			double pointsTravelled = (double) currentFrame / ( travelTime / path.size() - 1 );
			
			pointsTravelled = Math.ceil( pointsTravelled * 1000 ) / 1000;
			
			if ( pointsTravelled > path.size() - 1 ) { endTravel(); return; }
			
			float[] view = calculateHeadDirectionOnPath(pointsTravelled);
			
			Vec3d movementVector = calculateMovementVector(pointsTravelled);
			float[] playerView = calculateHeadDirectionOnPath(pointsTravelled);
			
			movePlayer(movementVector);
			setPlayerAngles(playerView);
			
			
			currentFrame++;
		}
	}
	
	private static void setPlayerPositionFromVector( Vec3d pos ) {
		Minecraft.getMinecraft().thePlayer.setPosition(pos.xCoord, pos.yCoord, pos.zCoord);
	}
	
	private static void setPlayerPositionAndAngles ( Vec3d pos, float[] view ) {
		Minecraft.getMinecraft().thePlayer.setPositionAndRotation(pos.xCoord, pos.yCoord, pos.zCoord, view[1], view[0]);
	}
	
	private static void movePlayer(Vec3d movementVector) {
		Minecraft.getMinecraft().thePlayer.moveEntity(movementVector.xCoord, movementVector.yCoord, movementVector.zCoord);
	}
	
	private static void setPlayerAngles( float[] angles ) {
		Minecraft.getMinecraft().thePlayer.setAngles(angles[1], angles[0]);
	}
	
	/**
	 * sets the time that it will take to traverse the path in seconds
	 * @param seconds Number of seconds that it will take to traverse the path
	 */
	public static void setTravelTime( int seconds ) {
		
		//TODO find accurate way to set number of ticks per second
		travelTime = seconds * 160;
	}
	
	/** updates the Camera path to the most recent points */

	public static void updatePoints() {
		path = Camera.getPath();
	}
}
