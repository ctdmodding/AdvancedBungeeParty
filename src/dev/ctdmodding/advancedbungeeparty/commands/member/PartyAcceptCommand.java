package dev.ctdmodding.advancedbungeeparty.commands.member;

import dev.ctdmodding.advancedbungeeparty.PartySystem;
import dev.ctdmodding.advancedbungeeparty.commands.SubCommand;
import dev.ctdmodding.advancedbungeeparty.core.Party;
import dev.ctdmodding.advancedbungeeparty.managers.PartyManager;
import dev.ctdmodding.advancedbungeeparty.utils.ChatUtil;
import dev.ctdmodding.advancedbungeeparty.utils.Util;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyAcceptCommand extends SubCommand {

	public PartyAcceptCommand(PartySystem plugin) {
		super("accept", plugin);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			PartyManager partyManager = getPlugin().getPartyManager();
			ProxiedPlayer player = (ProxiedPlayer) sender;
			
			if (args.length == 2) {
				if (!partyManager.hasParty(player)) {
					if (Util.isOnline(args[1])) {
						if (partyManager.hasParty(Util.getPlayer(args[1]))) {
							Party party = partyManager.getParty(Util.getPlayer(args[1]));
							
							if (party.hasBeenInvited(player)) {
								party.acceptInvite(player);
							} else {
								ChatUtil.sendMessage(player, "&cYou have not been invited to a party!");
							}
						} else {
							ChatUtil.sendMessage(player, "&cThis party is no longer valid!");
						}
					} else {
						ChatUtil.sendMessage(player, "&c" + args[1] + " is not online!");
					}
				} else {
					ChatUtil.sendMessage(player, "&cYou are already in a party! Enter &e/party leave &cand try again!");
				}
			} else {
				ChatUtil.sendMessage(player, getUsage());
			}
		}
	}
	
	@Override
	public String getUsage() {
		return "&cUsage: /party accept <username>";
	}
}
