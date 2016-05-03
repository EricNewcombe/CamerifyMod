package com.ericnewcombe.camerify.camera;

/*
* Represents a position in the minecraft world with x,y,z coords and pitch and roll
*/
public class CameraPoint {
	
	/**
	 * x position of the point
	 */
	
	public double x;
	
	/**
	 * y position of the point
	 */
	
	public double y;
	
	/**
	 * z position of the point
	 */
	
	public double z;
	
	/**
	 * angle of the pitch of the point
	 */
	
	public float camPitch;
	
	/**
	 * angle of the yaw of the point
	 */
	
	public float camYaw;
	
	public CameraPoint( double x, double y, double z, float camPitch, float camYaw  ){
		this.x = x;
		this.y = y;
		this.z = z;
		this.camPitch = camPitch;
		this.camYaw = camYaw;
	}
	
	/**
	 * Creates a {@link String} representation of the {@link CameraPoint}
	 */
	
	@Override
	public String toString() { return "x: " + (int)x + " y: " + (int)y + " z: " + (int)z; }

	/**
	 * Checks equality between two points
	 * @param p {@link CameraPoint} to compare to
	 * @return the result of the equality of two points
	 */
	
	public boolean equals( CameraPoint p ) {
		if ( p.x == x && p.y == y && p.z == z ) { return true; }
		return false;
	}

	
	
}