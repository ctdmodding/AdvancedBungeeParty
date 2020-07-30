package dev.ctdmodding.advancedbungeeparty.core;

import dev.ctdmodding.advancedbungeeparty.PartySystem;
import dev.ctdmodding.advancedbungeeparty.settings.MuteSettings;
import dev.ctdmodding.advancedbungeeparty.settings.PartySettings;
import dev.ctdmodding.advancedbungeeparty.utils.ChatUtil;
import dev.ctdmodding.advancedbungeeparty.utils.Util;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Party {
	
	private UUID leaderID;
	private PartySettings settings;
	private PartySystem plugin;
	//private List<Party> pendingMergeRequests;
	private List<UUID> muteList;
	private HashMap<UUID, ScheduledTask> offline;
	private HashMap<UUID, ScheduledTask> invites;
	private HashMap<Party, ScheduledTask> pendingMergeRequests;
	private MuteSettings muteSettings;
	private LinkedHashMap<UUID, String> memberList; // UUID, DisplayName
	
	public Party(ProxiedPlayer player, PartySystem plugin) {
		this.plugin = plugin;
		leaderID = player.getUniqueId();
		settings = new PartySettings();
		muteSettings = new MuteSettings();
		muteList = new ArrayList<>();
		pendingMergeRequests = new HashMap<>();
		offline = new HashMap<>();
		invites = new HashMap<>();
		memberList = new LinkedHashMap<>();
		memberList.put(leaderID, player.getDisplayName());
	}
	
	public UUID getLeaderID() {
		return leaderID;
	}
	
	public HashMap<UUID, ScheduledTask> getInvites() {
		return invites;
	}
	
	public String getLeaderDisplayName() {
		return memberList.get(leaderID);
	}
	
	public PartySettings getSettings() {
		return settings;
	}

	public MuteSettings getMuteSettings() {
		return muteSettings;
	}
	
	public boolean isInParty(UUID player) {
		return memberList.containsKey(player);
	}
	
	public boolean hasBeenInvited(ProxiedPlayer player) {
		return invites.containsKey(player.getUniqueId());
	}
	
	public boolean hasMergeRequest(Party party) {
		return pendingMergeRequests.containsKey(party);
	}
	
	public void promote(ProxiedPlayer player) {
		promote(player, player.getDisplayName() + " &ehas been promoted to party leader!");
	}
	
	public void promote(ProxiedPlayer player, String message) {
		leaderID = player.getUniqueId();
		
		broadcastPartyMessage(message);
	}
	
	public void steal(ProxiedPlayer player) {
		memberList.put(player.getUniqueId(), player.getDisplayName());
		promote(player, player.getDisplayName() + " has stolen the party!");
	}
	
	public LinkedHashMap<UUID, String> getMembers() {
		return memberList;
	}
	
	public void addMember(ProxiedPlayer player) {
		memberList.put(player.getUniqueId(), player.getDisplayName());
		
		if (muteSettings.getJoins()) {
			broadcastPartyMessage(player.getDisplayName() + " &ehas joined the party!");
		}
	}
	
	public void acceptInvite(ProxiedPlayer player) {
		if (invites.containsKey(player.getUniqueId())) {
			ProxyServer.getInstance().getScheduler().cancel(invites.get(player.getUniqueId()));
			invites.remove(player.getUniqueId());
			addMember(player);
		}
	}
	
	public void removeMember(ProxiedPlayer player, String message) {
		if (muteSettings.getLeaves()) {
			broadcastPartyMessage(player.getDisplayName() + message);
		}
		
		memberList.remove(player.getUniqueId());
		
		if (player.getUniqueId().equals(leaderID)) {
			plugin.getPartyManager().disbandParty(this);
		}
	}
	
	public void removeMember(String username, String message) {
		String[][] member = getMember(username);
		assert member != null;
		UUID uuid = UUID.fromString(member[0][0]);
		String displayName = member[1][0];
		
		if (muteSettings.getLeaves()) {
			broadcastPartyMessage(displayName + message);
		}

		memberList.remove(uuid);

		if (uuid.equals(leaderID)) {
			plugin.getPartyManager().disbandParty(this);
		}
	}
	
	public void removeMemberLeft(ProxiedPlayer player) {
		removeMember(player, " &ehas left the party!");
	}
	
	public void kickMember(ProxiedPlayer player) {
		removeMember(player, " &ehas been kicked from the party!");
		ChatUtil.sendMessage(player, "&cYou have been kicked from the party!");
	}
	
	public void kickMember(String username) {
		removeMember(username, " &ehas been kicked from the party!");
	}
	
	public void removeOfflineMember(ProxiedPlayer player) {
		removeMember(player, " &ehas been offline for five minutes and has been removed from the party!");
	}
	
	public void addInvite(final UUID playerID) {
		ProxiedPlayer player = Util.getPlayer(playerID);
		
		if (muteSettings.getInvites()) {
			broadcastPartyMessage(Util.getPlayer(playerID).getDisplayName() + " has been invited to the party!");
		}
		
		ChatUtil.sendFormattedMessage(player, "&eYou've been invited to " + memberList.get(leaderID) + "&e's party! &c/party accept " + Util.stripRank(memberList.get(leaderID)) + " &eto accept!");
		
		invites.put(playerID,
			plugin.getProxy().getScheduler().schedule(plugin, () -> {
				invites.remove(playerID);

				if (!isInParty(playerID)) {
					if (Util.isOnline(playerID)) {
						ProxiedPlayer player1 = Util.getPlayer(playerID);
						ChatUtil.sendMessage(player1, "&cParty invite expired from " + memberList.get(leaderID));
						if (muteSettings.getInvites())
							broadcastPartyMessage("&cParty invite to " + player1.getDisplayName() + " expired!");
					}
				}
			}, 1, TimeUnit.MINUTES)
		);
	}
	
	public boolean addMergeRequest(final Party party) {
		if (!pendingMergeRequests.containsKey(party)) {
			broadcastPartyMessage(memberList.get(leaderID) + " has requested to merge with " + party.getLeaderDisplayName() + "'s party!");
			
			pendingMergeRequests.put(party, plugin.getProxy().getScheduler().schedule(plugin, () -> {
				pendingMergeRequests.remove(party);

				if (Util.isOnline(party.getLeaderID())) {
					ChatUtil.sendFormattedMessage(Util.getPlayer(party.getLeaderID()), "&cMerge request to " + memberList.get(leaderID) + " expired!");
				}
				if (Util.isOnline(leaderID)) {
					ChatUtil.sendFormattedMessage(Util.getPlayer(party.getLeaderID()), "&cMerge request from " + party.getLeaderDisplayName() + " expired!");
				}
			}, 1, TimeUnit.MINUTES));
			return true;
		} else {
			return false;
		}
	}
	
	public void broadcastPartyMessage(String message) {
		if (muteSettings.getBroadcasts()) {
			for (UUID uuid : memberList.keySet()) {
				if (Util.isOnline(uuid)) {
					ChatUtil.sendFormattedMessage(Util.getPlayer(uuid), message);
				}
			}
		}
	}
	
	public void sendPartyChat(ProxiedPlayer sender, String message) {
		if (muteSettings.getChat()) {
			String formattedMessage = "&dParty: &e" + sender.getDisplayName() + "&f: " + message;
			
			for (UUID uuid : memberList.keySet()) {
				if (Util.isOnline(uuid) && !muteList.contains(uuid)) {
					ChatUtil.sendMessage(Util.getPlayer(uuid), formattedMessage);
				}
			}
		}
	}
	
	public void merge(Party targetParty) {
		ProxiedPlayer targetLeader = Util.getPlayer(targetParty.getLeaderID());
		HashMap<UUID, String> members = targetParty.getMembers();
		plugin.getPartyManager().disbandParty(targetParty);
		targetParty = null;
		
		addMember(targetLeader);
		for (UUID uuid : members.keySet()) {
			if (Util.isOnline(uuid)) {
				addMember(Util.getPlayer(uuid));
			}
		}
	}
	
	public String getPartyList() {
		StringBuilder partyList = new StringBuilder(memberList.get(leaderID));
		
		for (UUID uuid : memberList.keySet()) {
			if (!uuid.equals(leaderID)) {
				if (Util.isOnline(uuid)) {
					partyList.append("&9, ").append(Util.getPlayer(uuid).getDisplayName()).append("&f");
				} else {
					partyList.append("&9, &c[OFFLINE]").append(memberList.get(uuid)).append("&f");
				}
			}
		}
		
		partyList.insert(0, "&9Party List: (&b" + memberList.size() + "&9) - ");
		
		return ChatUtil.toFormattedMessage(partyList.toString());
	}
	
	public void addMute(ProxiedPlayer player) {
		muteList.add(player.getUniqueId());
		ChatUtil.sendFormattedMessage(player, "&cParty chat muted!");
	}
	
	public void removeMute(ProxiedPlayer player) {
		muteList.remove(player.getUniqueId());
		ChatUtil.sendFormattedMessage(player, "&aParty chat unmuted!");
	}
	
	public boolean hasChatMuted(ProxiedPlayer player) {
		return muteList.contains(player.getUniqueId());
	}
	
	public void setOffline(final ProxiedPlayer player) {
		offline.put(player.getUniqueId(), 
			ProxyServer.getInstance().getScheduler().schedule(plugin, () -> {
				if (!player.getUniqueId().equals(leaderID)) {
					removeOfflineMember(player);
					offline.remove(player.getUniqueId());
				} else {
					offlineLeaderDisband();
				}
			}, 5, TimeUnit.MINUTES)
		);
	}
	
	public void setOnline(ProxiedPlayer player) {
		if (offline.containsKey(player.getUniqueId())) {
			ProxyServer.getInstance().getScheduler().cancel(offline.get(player.getUniqueId()));
			offline.remove(player.getUniqueId());
		}
	}
	
	public void warp() {
		warp(Util.getPlayer(leaderID).getServer().getInfo());
	}
	
	public void warp(ServerInfo server) {
		if (Util.isOnline(leaderID)) {
			
			broadcastPartyMessage("&eYou have been warped to " + memberList.get(leaderID) + "&e's server!" );
			
			for (UUID uuid : memberList.keySet()) {
				if (!uuid.equals(leaderID)) {
					if (Util.isOnline(uuid)) {
						Util.getPlayer(uuid).connect(server);
					}
				}
			}
		}
	}
	
	// TODO: Create a member object
	private String[][] getMember(String username) {
		String[][] member = new String[2][1];
		
		for (UUID uuid : memberList.keySet()) {
			String displayName = memberList.get(uuid);
			BungeeCord.getInstance().broadcast(displayName + " -- " + Util.stripRank(displayName));
			if (Util.stripRank(displayName).equalsIgnoreCase(username)) {
				member[0][0] = uuid.toString();
				member[1][0] = displayName;
				return member;
			}
		}
		return null;
	}
	
	public UUID getUUID(String displayName) {
		for (UUID uuid : memberList.keySet()) {
			if (displayName.equals(memberList.get(uuid))) {
				return uuid;
			}
		}
		return null;
	}
	
	private void offlineLeaderDisband() {
		plugin.getPartyManager().disbandParty(this, "&cThe party leader has been offline for 5 minutes, so the party has been disbanded!");
	}
	
	public void reset() {
		leaderID = null;
		settings = new PartySettings();
		muteSettings = new MuteSettings();
		pendingMergeRequests = new HashMap<>();
		muteList = new ArrayList<>();
		offline = new HashMap<>();
		invites = new HashMap<>();
		memberList = new LinkedHashMap<>();
	}
}
