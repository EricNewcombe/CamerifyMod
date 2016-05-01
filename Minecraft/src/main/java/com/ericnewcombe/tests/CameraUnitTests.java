package com.ericnewcombe.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ericnewcombe.camerify.camera.Camera;
import com.ericnewcombe.camerify.camera.CameraPoint;

public class CameraUnitTests {
	
	@Test
	public void testCamera() {
		
		// initialize camera points
		CameraPoint a = new CameraPoint(50, 50, 50, 10, 10),
					b = new CameraPoint(100, 45, 60, 60, 80),
					c = new CameraPoint(110, 55, 80, 30, 60),
					d = new CameraPoint(160, 435, 10, 50, 70);
		
		// Make sure camera is set to base line
		assertTrue( Camera.clearPath() );
		assertEquals( 0, Camera.getSize() );
		
		// Test adding points
		assertTrue( Camera.addPoint( a ) );
		assertTrue( Camera.addPoint( b ) );
		assertTrue( Camera.addPoint( c ) );
		assertTrue( Camera.addPoint( d ) );
		assertEquals( 4, Camera.getSize() );
		
		// Test removing points
		Camera.removePoint(5);
		assertEquals( 4, Camera.getSize() );
		Camera.removePoint(2);
		assertEquals( Camera.getSize(), 3 );
		
		// Test adding point at position
		
		assertFalse( Camera.addPointAtPosition(a, 10) ); // Check out of bounds
		assertFalse( Camera.addPointAtPosition(a, -1) ); // Check out of bounds
		assertTrue( Camera.addPointAtPosition(c, 2) );
		assertTrue( Camera.getPoint(2).equals(c) ); // Ensure it was placed in the correct position
		
		// Test setting a point at a position
		
		assertTrue( Camera.getPoint(0).equals(a) );
		assertTrue( Camera.setPointAtPosition(c, 0) );
		assertFalse( Camera.getPoint(0).equals(a) );
		assertTrue( Camera.getPoint(0).equals(c) );
		
		assertFalse( Camera.setPointAtPosition(d, -1) );
		assertFalse( Camera.setPointAtPosition(d, 10) );
		
		// Test clearing the path
		assertTrue( Camera.clearPath() );
		assertEquals( Camera.getSize(), 0 );
	}

	@Test
	public void testCameraPathHandler() {
		
	}
}
