package dev.ctdmodding.advancedbungeeparty.commands.leader;

import dev.ctdmodding.advancedbungeeparty.PartySystem;
import dev.ctdmodding.advancedbungeeparty.commands.SubCommand;
import dev.ctdmodding.advancedbungeeparty.core.Party;
import dev.ctdmodding.advancedbungeeparty.managers.PartyManager;
import dev.ctdmodding.advancedbungeeparty.utils.ChatUtil;
import dev.ctdmodding.advancedbungeeparty.utils.Util;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyInviteCommand extends SubCommand {

	public PartyInviteCommand(PartySystem plugin) {
		super("invite", plugin);
	}
	
	@Override
	public String getUsage() {
		return "&cUsage: /party invite <username>";
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			PartyManager partyManager = getPlugin().getPartyManager();
			ProxiedPlayer player = (ProxiedPlayer) sender;
			
			if (args.length == 2) {
				if (partyManager.hasParty(player)) {
					Party party = partyManager.getParty(player);
					
					if (party.getLeaderID().equals(player.getUniqueId()) || party.getSettings().getAllInvite()) {
						if (Util.isOnline(args[1])) {
							ProxiedPlayer target = Util.getPlayer(args[1]);
							if (!party.hasBeenInvited(target)) {
								if (!party.getMembers().containsKey(target.getUniqueId())) {
									invitePlayer(player, target, party);
								} else {
									ChatUtil.sendMessage(player, "&b" + target.getDisplayName() + "&c is already in your party!");
								}
							} else
								ChatUtil.sendMessage(player, "&cYou have already invited " + target.getDisplayName() + "&c!");
						} else {
							ChatUtil.sendMessage(player, "&c" + args[1] + " is not online!");
						}
					} else {
						ChatUtil.sendMessage(player, "&cYou are not the party leader!");
					}
				} else {
					partyManager.createParty(player);
					Party party = partyManager.getParty(player);
					
					if (Util.isOnline(args[1])) {
						ProxiedPlayer target = Util.getPlayer(args[1]);
						invitePlayer(player, target, party);
					} else {
						ChatUtil.sendMessage(player, "&c" + args[1] + " is not online!");
					}
				}
			} else {
				ChatUtil.sendMessage(player, getUsage());
			}
		}
	}
	
	private void invitePlayer(ProxiedPlayer leader, ProxiedPlayer target, Party party) {
		if (!leader.getUniqueId().toString().equalsIgnoreCase(target.getUniqueId().toString())) {
			party.addInvite(target.getUniqueId());
		} else {
			ChatUtil.sendMessage(leader, "&cYou cannot invite yourself!");
		}
	}
}
