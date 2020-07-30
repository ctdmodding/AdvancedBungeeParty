package dev.ctdmodding.advancedbungeeparty.listeners.server;

import dev.ctdmodding.advancedbungeeparty.PartySystem;
import dev.ctdmodding.advancedbungeeparty.core.Party;
import dev.ctdmodding.advancedbungeeparty.managers.PartyManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerConnected implements Listener {
	
	private PartySystem plugin;

	public ServerConnected(PartySystem plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onServerConnected(ServerConnectedEvent event) {
		ProxiedPlayer player = event.getPlayer();
		PartyManager partyManager = plugin.getPartyManager();
		Party party;
		
		if (partyManager.hasParty(player)) {
			party = partyManager.getParty(player);
			
			if (party.getSettings().getAutoWarp()) {
				if (party.getLeaderID().equals(player.getUniqueId())) {
					party.warp(event.getServer().getInfo());
				}
			}
		}
	}
}
