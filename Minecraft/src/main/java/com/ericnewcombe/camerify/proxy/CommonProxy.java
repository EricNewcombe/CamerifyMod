package com.ericnewcombe.camerify.proxy;

import com.ericnewcombe.camerify.eventhandler.ModEventHandler;
import com.ericnewcombe.camerify.gui.GuiHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent e) {

	}

	public void init(FMLInitializationEvent e) {
		new GuiHandler();
	}

	public void postInit(FMLPostInitializationEvent e) {MinecraftForge.EVENT_BUS.register(new ModEventHandler());
		FMLCommonHandler.instance().bus().register(new ModEventHandler());
	}
	
}
