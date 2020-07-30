package dev.ctdmodding.advancedbungeeparty.listeners.player;

import dev.ctdmodding.advancedbungeeparty.PartySystem;
import dev.ctdmodding.advancedbungeeparty.core.Party;
import dev.ctdmodding.advancedbungeeparty.managers.PartyManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerLeave implements Listener {

	private PartySystem plugin;

	public PlayerLeave(PartySystem plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerDisconnectEvent event) {
		ProxiedPlayer player = event.getPlayer();
		PartyManager partyManager = plugin.getPartyManager();
		
		if (partyManager.hasParty(player)) {
			Party party = partyManager.getParty(player);
			party.setOffline(player);
			party.broadcastPartyMessage(player.getDisplayName() + " has logged out!");
		}
	}
}
