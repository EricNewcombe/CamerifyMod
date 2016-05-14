package com.ericnewcombe.camerify.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;


/** Simple helper class to display messages to the user */

public class ChatHandler {
	
	private static final String PREFIX = "§a<Camerify> §f";
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	public static void sendMessage( String msg ) {
		if ( mc != null ) {
			mc.thePlayer.addChatMessage(new TextComponentString(PREFIX + msg));
		}
	}
	
}
