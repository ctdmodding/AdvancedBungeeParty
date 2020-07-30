package dev.ctdmodding.advancedbungeeparty;

import dev.ctdmodding.advancedbungeeparty.commands.admin.PartyStealCommand;
import dev.ctdmodding.advancedbungeeparty.commands.leader.*;
import dev.ctdmodding.advancedbungeeparty.commands.member.*;
import dev.ctdmodding.advancedbungeeparty.listeners.player.PlayerJoin;
import dev.ctdmodding.advancedbungeeparty.listeners.player.PlayerLeave;
import dev.ctdmodding.advancedbungeeparty.listeners.server.ServerConnected;
import dev.ctdmodding.advancedbungeeparty.managers.CommandManager;
import dev.ctdmodding.advancedbungeeparty.managers.PartyManager;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * Copyright 2020, "ctd", All rights reserved.
 */
public class PartySystem extends Plugin {
	
	private PartyManager partyManager;
	
	public void onEnable() {
		partyManager = new PartyManager(this);
		registerCommands();
		registerListeners();
	}
	
	public void onDisable() {

	}
	
	public PartyManager getPartyManager() {
		return partyManager;
	}
	
	public void registerCommands() {
		CommandManager commandManager = new CommandManager();
		getProxy().getPluginManager().registerCommand(this, commandManager);
		
		commandManager.registerCommand("party", new PartyInviteCommand(this));
		commandManager.registerCommand("party", new PartyListCommand(this));
		commandManager.registerCommand("party", new PartyAcceptCommand(this));
		commandManager.registerCommand("party", new PartyLeaveCommand(this));
		commandManager.registerCommand("party", new PartyDisbandCommand(this));
		commandManager.registerCommand("party", new PartyPromoteCommand(this));
		commandManager.registerCommand("party", new PartyWarpCommand(this));
		commandManager.registerCommand("party", new PartyChatCommand(this));
		commandManager.registerCommand("party", new PartyKickCommand(this));
		commandManager.registerCommand("party", new PartyRouletteCommand(this));
		commandManager.registerCommand("party", new PartySettingsCommand(this));
		commandManager.registerCommand("party", new PartyMergeCommand(this));
		commandManager.registerCommand("party", new PartyJoinCommand(this));
		commandManager.registerCommand("party", new PartyToggleCommand(this));
		commandManager.registerCommand("party", new PartyHelpCommand(this));
		commandManager.registerCommand("party", new PartyMuteCommand(this));
		commandManager.registerCommand("party", new PartyTeleportCommand(this));
		commandManager.registerCommand("party", new PartyStealCommand(this));
	}

	public void registerListeners() {
		getProxy().getPluginManager().registerListener(this, new PlayerJoin(this));
		getProxy().getPluginManager().registerListener(this, new PlayerLeave(this));
		getProxy().getPluginManager().registerListener(this, new ServerConnected(this));
	}
}
