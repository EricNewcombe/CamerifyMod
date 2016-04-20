package com.ericnewcombe.camerify.eventhandler;

import com.ericnewcombe.camerify.camera.Camera;
import com.ericnewcombe.camerify.camera.CameraPathHandler;
import com.ericnewcombe.camerify.camera.CameraPoint;
import com.ericnewcombe.camerify.gui.ModGui;
import com.ericnewcombe.camerify.proxy.ClientProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModEventHandler {
private boolean[] pressed = new boolean[ClientProxy.keyBindings.length];
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(KeyInputEvent event) {

	    KeyBinding[] keyBindings = ClientProxy.keyBindings;
	   
	    
	    if (keyBindings[0].isPressed() ) {
	    	// Options key
	    	EntityPlayer p = Minecraft.getMinecraft().thePlayer;
	        Minecraft.getMinecraft().displayGuiScreen(new ModGui(p));
	        pressed[0] = true;
	    }
	    else if ( !keyBindings[0].isPressed() && pressed[0] ) { pressed[0] = false; }
	    
	    if (keyBindings[1].isPressed() && !pressed[1]) {
	    	// New point key
	    	EntityPlayer p = Minecraft.getMinecraft().thePlayer;
	        Camera.addPoint(new CameraPoint(p.posX, p.posY, p.posZ, p.rotationPitch, p.rotationYaw));
	        pressed[1] = true;
	    }
	    else if ( !keyBindings[1].isPressed() && pressed[1] ) { pressed[1] = false; }
	    
	}
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(ServerTickEvent e) {
		CameraPathHandler.tick();
	}
}
