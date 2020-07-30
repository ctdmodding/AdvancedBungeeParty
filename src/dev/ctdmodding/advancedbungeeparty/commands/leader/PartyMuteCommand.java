package dev.ctdmodding.advancedbungeeparty.commands.leader;

import dev.ctdmodding.advancedbungeeparty.PartySystem;
import dev.ctdmodding.advancedbungeeparty.commands.SubCommand;
import dev.ctdmodding.advancedbungeeparty.core.Party;
import dev.ctdmodding.advancedbungeeparty.managers.PartyManager;
import dev.ctdmodding.advancedbungeeparty.settings.MuteSettings;
import dev.ctdmodding.advancedbungeeparty.utils.ChatUtil;
import dev.ctdmodding.advancedbungeeparty.utils.Util;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyMuteCommand extends SubCommand {
	
	public PartyMuteCommand(PartySystem plugin) {
		super("mute", plugin);
	}

	@Override
	public String getUsage() {
		return "&cUsage: /party mute <joins | leaves | chat | invites | broadcasts | all | list>";
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			PartyManager partyManager = getPlugin().getPartyManager();
			ProxiedPlayer player = (ProxiedPlayer) sender;
			
			if (args.length == 2) {
				if (partyManager.hasParty(player)) {
					Party party = partyManager.getParty(player);
					MuteSettings settings = party.getMuteSettings();
					
					if (party.getLeaderID().equals(player.getUniqueId()) || args[1].equalsIgnoreCase("list")) {
						switch (args[1].toLowerCase()) {
							case "joins":
								settings.toggleJoins();
								
								if (settings.getJoins()) {
									party.broadcastPartyMessage("&aJoin messages were unmuted!");
								} else {
									party.broadcastPartyMessage("&cJoin messages were muted!");
								}
								break;
							case "leaves":
								settings.toggleLeaves();
								if (settings.getLeaves()) {
									party.broadcastPartyMessage("&aLeave messages were unmuted!");
								} else {
									party.broadcastPartyMessage("&cLeave messages were muted!");
								}
								break;
							case "broadcasts":
								if (!settings.getBroadcasts()) {
									party.broadcastPartyMessage("&aBroadcast messages unwere muted!");
								} else {
									party.broadcastPartyMessage("&cBroadcast messages were muted!");
								}
								settings.toggleBroadcasts();
								break;
							case "chat":
								settings.toggleChat();
								if (settings.getChat()) {
									party.broadcastPartyMessage("&aChat messages were unmuted!");
								} else {
									party.broadcastPartyMessage("&cChat messages were muted!");
								}
								break;
							case "invites":
								settings.toggleInvites();
								if (settings.getInvites()) {
									party.broadcastPartyMessage("&aInvite messages were unmuted!");
								} else {
									party.broadcastPartyMessage("&cInvite messages were muted!");
								}
								break;
							case "all":
								settings.toggleAll();
								if (settings.getAll()) {
									party.broadcastPartyMessage("&aAll party messages were unmuted!");
								} else {
									party.broadcastPartyMessage("&cAll party messages were muted!");
								}
								break;
							case "list":
								ChatUtil.sendMessage(player, "Join Messages: " + Util.fromBoolean(settings.getJoins()));
								ChatUtil.sendMessage(player, "Leave Messages: " + Util.fromBoolean(settings.getLeaves()));
								ChatUtil.sendMessage(player, "Invite Messages: " + Util.fromBoolean(settings.getInvites()));
								ChatUtil.sendMessage(player, "Broadcast Messages: " + Util.fromBoolean(settings.getBroadcasts()));
								ChatUtil.sendMessage(player, "Chat Messages: " + Util.fromBoolean(settings.getChat()));
								break;
							default:
								ChatUtil.sendMessage(player, getUsage());
						}
					} else {
						ChatUtil.sendMessage(player, "&cYou are not the party leader!");
					}
				} else {
					ChatUtil.sendMessage(player, "&cYou are not in a party!");
				}
			} else {
				ChatUtil.sendMessage(player, getUsage());
			}
		}
	}
}
