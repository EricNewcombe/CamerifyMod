package com.ericnewcombe.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ericnewcombe.camerify.camera.Camera;
import com.ericnewcombe.camerify.camera.CameraPathHandler;
import com.ericnewcombe.camerify.camera.CameraPoint;

import net.minecraft.util.math.Vec3d;

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
		
		// initialize camera points
		CameraPoint a = new CameraPoint(50, 50, 50, 10, 10),
					b = new CameraPoint(100, 45, 60, 60, 80),
					c = new CameraPoint(110, 55, 80, 30, 60),
					d = new CameraPoint(160, 435, 10, 50, 70);
		
		Camera.clearPath();
		
		// add to camera
		Camera.addPoint(a);
		Camera.addPoint(b);
		Camera.addPoint(c);
		Camera.addPoint(d);
		
		CameraPathHandler.updatePoints();
		
		// Test converting point to Vec3D
		
		Vec3d vecA = CameraPathHandler.generateVecFromPoint(a);
		assertEquals(vecA.xCoord, a.x, 0);
		assertEquals(vecA.yCoord, a.y, 0);
		assertEquals(vecA.zCoord, a.z, 0);
		
		
		// Head position
		
		assertEquals(CameraPathHandler.calculateHeadDirectionOnPath(0)[0], a.camPitch, 0 );
		assertEquals(CameraPathHandler.calculateHeadDirectionOnPath(0.5)[0], 35, 0 );
		assertEquals(CameraPathHandler.calculateHeadDirectionOnPath(1)[0], b.camPitch, 0 );
		assertEquals(CameraPathHandler.calculateHeadDirectionOnPath(1.5)[0], 45, 0 );
		assertEquals(CameraPathHandler.calculateHeadDirectionOnPath(2)[0], c.camPitch, 0 );
		assertEquals(CameraPathHandler.calculateHeadDirectionOnPath(2.5)[0], 40, 0 );
		assertEquals(CameraPathHandler.calculateHeadDirectionOnPath(3)[0], d.camPitch, 0 );
		
		assertEquals(CameraPathHandler.calculateHeadDirectionOnPath(0)[1], a.camYaw, 0 );
		assertEquals(CameraPathHandler.calculateHeadDirectionOnPath(0.5)[1], 45, 0 );
		assertEquals(CameraPathHandler.calculateHeadDirectionOnPath(1)[1], b.camYaw, 0 );
		assertEquals(CameraPathHandler.calculateHeadDirectionOnPath(1.5)[1], 70, 0 );
		assertEquals(CameraPathHandler.calculateHeadDirectionOnPath(2)[1], c.camYaw, 0 );
		assertEquals(CameraPathHandler.calculateHeadDirectionOnPath(2.5)[1], 65, 0 );
		assertEquals(CameraPathHandler.calculateHeadDirectionOnPath(3)[1], d.camYaw, 0 );
		
		
		
	}
}
