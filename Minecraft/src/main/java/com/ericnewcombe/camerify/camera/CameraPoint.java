package com.ericnewcombe.camerify.camera;

/*
* Represents a position in the minecraft world with x,y,z coords and pitch and roll
*/
public class CameraPoint {
	
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

	public boolean equals( CameraPoint p ) {
		if ( p.x == x && p.y == y && p.z == z ) { return true; }
		return false;
	}

	
	
}