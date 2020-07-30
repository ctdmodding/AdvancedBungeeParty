package dev.ctdmodding.advancedbungeeparty.managers;

import com.google.common.collect.Maps;
import dev.ctdmodding.advancedbungeeparty.PartySystem;
import dev.ctdmodding.advancedbungeeparty.core.Party;
import dev.ctdmodding.advancedbungeeparty.utils.Util;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.UUID;

public class PartyManager {
	
	private HashMap<UUID, Party> parties;
	private PartySystem plugin;
	
	public PartyManager(PartySystem plugin) {
		this.plugin = plugin;
		parties = Maps.newHashMap();
	}
	
	public void createParty(ProxiedPlayer player) {
		if (!hasParty(player)) {
			parties.put(player.getUniqueId(), new Party(player, plugin));
		}
	}
	
	public void disbandParty(Party party) {
		disbandParty(party, "&cThe party has been disbanded!");
	}
	
	public void disbandParty(Party party, String message) {
		for (UUID uuid : parties.keySet()) {
			if (parties.get(uuid).equals(party)) {
				party.broadcastPartyMessage(message);
				parties.remove(uuid);
				party.reset();
				break;
			}
		}
	}
	
	public Party getParty(ProxiedPlayer player) {
		if (hasParty(player)) {
			for (Party party : parties.values()) {
				if (party.getLeaderID().equals(player.getUniqueId())) {
					return party;
				} else {
					if (party.getMembers().containsKey(player.getUniqueId())) {
						return party;
					}
				}
			}
		}
		return null;
	}
	
	public Party getParty(String username) {
		if (hasParty(username)) {
			for (Party party : parties.values()) {
				for (String displayName : party.getMembers().values()) {
					if (Util.stripRank(displayName).equalsIgnoreCase(username)) {
						return party;
					}
				}
			}
		}
		return null;
	}
	
	public boolean hasParty(ProxiedPlayer player) {
		for (Party party : parties.values()) {
			if (party.getLeaderID().equals(player.getUniqueId())) {
				return true;
			} else {
				if (party.getMembers().containsKey(player.getUniqueId())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hasParty(String username) {
		for (Party party : parties.values()) {
			for (String displayName : party.getMembers().values()) {
				if (Util.stripRank(displayName).equalsIgnoreCase(username)) {
					return true;
				}
			}
		}
		return false;
	}
}
