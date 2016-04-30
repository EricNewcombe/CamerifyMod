package com.ericnewcombe.camerify.commandhandler;

import java.util.ArrayList;
import java.util.List;

import com.ericnewcombe.camerify.Main;
import com.ericnewcombe.camerify.camera.Camera;
import com.ericnewcombe.camerify.camera.CameraPathHandler;
import com.ericnewcombe.camerify.camera.CameraPoint;
import com.ericnewcombe.camerify.chat.ChatHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class ModCommandHandler implements ICommand {
	
	private List<String> aliases;
	
	public ModCommandHandler() {
		this.aliases = new ArrayList();
		this.aliases.add("camera");
		this.aliases.add("cam");
	}

	@Override
	public int compareTo(ICommand o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCommandName() {
		return "Camera";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "sample <text>";
	}

	@Override
	public List<String> getCommandAliases() {
		return this.aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length == 0) {
			ChatHandler.sendMessage("Invalid arguments.");
			return;
	    }
	    
		EntityPlayer p = Minecraft.getMinecraft().thePlayer;
		
	    if( args[0].toLowerCase().equals("point") ) {
	        Camera.addPoint(new CameraPoint(p.posX, p.posY, p.posZ, p.rotationPitch, p.rotationYaw));
	    }
	    else if ( args[0].toLowerCase().equals("start") ){
	    	try {
	    		if(args[1].equals(null)){
	    		}
	    		else {
	    			try{
	    				int seconds = Integer.parseInt(args[1]);
	    				CameraPathHandler.setTravelTime(seconds);
	    				CameraPathHandler.startTravel();
	    			}
	    			catch(NumberFormatException  e) {
	    				ChatHandler.sendMessage("Please enter a valid number");
	    			}
	    		}
	    	}
	    	catch(ArrayIndexOutOfBoundsException e){
	    		ChatHandler.sendMessage("Please enter a time");
	    	}
	    }
	    else if ( args[0].toLowerCase().equals("stop") ) {
	    	CameraPathHandler.endTravel();
	    }
	    else if ( args[0].toLowerCase().equals("clear") ) {
	    	Camera.clearPath();
	    }
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
