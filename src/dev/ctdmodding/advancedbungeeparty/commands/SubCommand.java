package dev.ctdmodding.advancedbungeeparty.commands;

import dev.ctdmodding.advancedbungeeparty.PartySystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public abstract class SubCommand {
	
	private String commandName;
	private PartySystem plugin;
	
	public SubCommand(String commandName, PartySystem plugin) {
		this.commandName = commandName;
		this.plugin = plugin;
	}
	
	public abstract void onCommand(CommandSender sender, String[] args);
	
	public abstract String getUsage();
	
	public boolean canRunFromConsole() {
		return false;
	}
	
	public PartySystem getPlugin() {
		return plugin;
	}
	
	public boolean execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			onCommand(sender, args);
		} else if (canRunFromConsole()) {
			onCommand(sender, args);
		}
		
		return true;
	}
	
	public String getCommandName() {
		return commandName;
	}
}
