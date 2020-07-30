package dev.ctdmodding.advancedbungeeparty.commands.leader;

import dev.ctdmodding.advancedbungeeparty.PartySystem;
import dev.ctdmodding.advancedbungeeparty.commands.SubCommand;
import dev.ctdmodding.advancedbungeeparty.core.Party;
import dev.ctdmodding.advancedbungeeparty.managers.PartyManager;
import dev.ctdmodding.advancedbungeeparty.utils.ChatUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyKickCommand extends SubCommand {

	public PartyKickCommand(PartySystem plugin) {
		super("kick", plugin);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			PartyManager partyManager = getPlugin().getPartyManager();
			ProxiedPlayer player = (ProxiedPlayer) sender;
			
			if (args.length == 2) {
				if (partyManager.hasParty(player)) {
					Party party = partyManager.getParty(player);
					
					if (party.getLeaderID().equals(player.getUniqueId())) {
						String target = args[1];
						
						if (partyManager.hasParty(target) && partyManager.getParty(target).getLeaderID().equals(player.getUniqueId())) {
							if (!player.getName().equalsIgnoreCase(target))
								party.kickMember(target);
							else
								ChatUtil.sendMessage(player, "&cYou cannot kick yourself!");
						} else {
							ChatUtil.sendMessage(player, "&c" + target + " is not in your party!");
						}
						
//						if (Util.isOnline(args[1])) {
//							ProxiedPlayer target = Util.getPlayer(args[1]);
//							
//							if (partyManager.hasParty(target) && partyManager.getParty(target).getLeaderID().equals(player.getUniqueId())) {
//								if (!player.getUniqueId().equals(target.getUniqueId()))
//									party.kickMember(target);
//								else
//									ChatUtil.sendMessage(player, "&cYou cannot kick yourself!");
//							} else {
//								ChatUtil.sendMessage(player, target.getDisplayName() + "&c is not in your party!");
//							}
//						} else {
//							ChatUtil.sendMessage(player, "&c" + args[1] + " is not online!");
//						}
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
	
	@Override
	public String getUsage() {
		return "&cUsage: /party kick <username>";
	}
}
