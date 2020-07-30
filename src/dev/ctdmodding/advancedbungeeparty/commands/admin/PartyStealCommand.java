package dev.ctdmodding.advancedbungeeparty.commands.admin;

import dev.ctdmodding.advancedbungeeparty.PartySystem;
import dev.ctdmodding.advancedbungeeparty.commands.SubCommand;
import dev.ctdmodding.advancedbungeeparty.core.Party;
import dev.ctdmodding.advancedbungeeparty.managers.PartyManager;
import dev.ctdmodding.advancedbungeeparty.utils.ChatUtil;
import dev.ctdmodding.advancedbungeeparty.utils.Util;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyStealCommand extends SubCommand {

	public PartyStealCommand(PartySystem plugin) {
		super("steal", plugin);
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer player = (ProxiedPlayer) sender;
			
			if (player.hasPermission("party.steal")) {
				if (args.length == 2) {
					PartyManager partyManager = getPlugin().getPartyManager();
					Party party;
					ProxiedPlayer target;
					
					if (!partyManager.hasParty(player)) {
						if (Util.isOnline(args[1])) {
							target = Util.getPlayer(args[1]);
							
							if (partyManager.hasParty(target)) {
								party = partyManager.getParty(target);
								
								party.steal(player);
							} else {
								ChatUtil.sendMessage(player, target.getDisplayName() + " &cdoes not have a party!");
							}
						} else {
							ChatUtil.sendMessage(player, "&c" + args[1] + " is not online!");
						}
					} else {
						ChatUtil.sendMessage(player, "&cYou are already in a party! Please leave first!");
					}
				} else {
					ChatUtil.sendMessage(player, getUsage());
				}
			} else {
				ChatUtil.sendMessage(player, "&cYou don't have permission to run this command!");
			}
		}
	}

	@Override
	public String getUsage() {
		return "&cUsage: /party steal <username>";
	}
}
