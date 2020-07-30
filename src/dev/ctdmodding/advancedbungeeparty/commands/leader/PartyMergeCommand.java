package dev.ctdmodding.advancedbungeeparty.commands.leader;

import dev.ctdmodding.advancedbungeeparty.PartySystem;
import dev.ctdmodding.advancedbungeeparty.commands.SubCommand;
import dev.ctdmodding.advancedbungeeparty.core.Party;
import dev.ctdmodding.advancedbungeeparty.managers.PartyManager;
import dev.ctdmodding.advancedbungeeparty.utils.ChatUtil;
import dev.ctdmodding.advancedbungeeparty.utils.Util;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyMergeCommand extends SubCommand {

	public PartyMergeCommand(PartySystem plugin) {
		super("merge", plugin);
	}

	@Override
	public String getUsage() {
		return "&cUsage: /party merge <request | accept> <username>";
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			PartyManager partyManager = getPlugin().getPartyManager();
			ProxiedPlayer player = (ProxiedPlayer) sender;
			
			if (args.length == 3) {
				if (partyManager.hasParty(player)) {
					Party party = partyManager.getParty(player);
					
					if (party.getLeaderID().equals(player.getUniqueId())) {
						if (args[1].equalsIgnoreCase("request")) {
							if (Util.isOnline(args[2])) {
								ProxiedPlayer targetPlayer = Util.getPlayer(args[2]);
								
								if (!player.getUniqueId().toString().equalsIgnoreCase(targetPlayer.getUniqueId().toString())) {
									partyManager.getParty(targetPlayer).addMergeRequest(party);
								} else {
									ChatUtil.sendMessage(player, "&cYou cannot request to merge with yourself!");
								}
							} else {
								ChatUtil.sendMessage(player, "&c" + args[2] + " is not online!");
							}
						} else if (args[1].equalsIgnoreCase("accept")) {
							if (Util.isOnline(args[2])) {
								ProxiedPlayer targetPlayer = Util.getPlayer(args[2]);
								Party targetParty = partyManager.getParty(targetPlayer);
								
								if (party.hasMergeRequest(targetParty)) {
									targetParty.merge(party);
								} else {
									ChatUtil.sendMessage(player, "&cYou have not been requested to merge with this party!");
								}
							} else {
								ChatUtil.sendMessage(player, "&c" + args[2] + " is not online!");
							}
						} else {
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

//if (Util.isOnline(args[1])) {
//	ProxiedPlayer target = Util.getPlayer(args[1]);
//	
//	if (partyManager.hasParty(target)) {
//		if (partyManager.getParty(target).getLeaderID().equals(target.getUniqueId())) {
//			party.merge(partyManager.getParty(target));
//		} else {
//			// errors
//		}
//	} else {
//		ChatUtil.sendMessage(player, target.getDisplayName() + "&c does not have a party!");
//	}
//} else {
//	ChatUtil.sendMessage(player, "&c" + args[1] + " is not online!!");
//}
