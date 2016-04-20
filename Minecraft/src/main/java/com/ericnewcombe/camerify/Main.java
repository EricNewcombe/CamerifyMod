package com.ericnewcombe.camerify;

import com.ericnewcombe.camerify.camera.Camera;
import com.ericnewcombe.camerify.commandhandler.ModCommandHandler;
import com.ericnewcombe.camerify.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Main.MODID, name = Main.MODNAME, version = Main.VERSION)
public class Main {

	public static final String MODID = "Camerify";
	public static final String MODNAME = "Camerify Mod";
	public static final String VERSION = "0.0.1";
	
	public static Camera camPath = Camera.getInstance();
	
	@Instance
	public static Main instance = new Main();

	@SidedProxy(clientSide="com.ericnewcombe.camerify.proxy.ClientProxy", serverSide="com.ericnewcombe.camerify.proxy.ServerProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event){
	    event.registerServerCommand(new ModCommandHandler());
	}
	
}
