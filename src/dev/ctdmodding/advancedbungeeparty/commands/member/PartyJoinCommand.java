package dev.ctdmodding.advancedbungeeparty.commands.member;

import dev.ctdmodding.advancedbungeeparty.PartySystem;
import dev.ctdmodding.advancedbungeeparty.commands.SubCommand;
import dev.ctdmodding.advancedbungeeparty.core.Party;
import dev.ctdmodding.advancedbungeeparty.managers.PartyManager;
import dev.ctdmodding.advancedbungeeparty.utils.ChatUtil;
import dev.ctdmodding.advancedbungeeparty.utils.Util;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyJoinCommand extends SubCommand {

	public PartyJoinCommand(PartySystem plugin) {
		super("join", plugin);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			PartyManager partyManager = getPlugin().getPartyManager();
			ProxiedPlayer player = (ProxiedPlayer) sender;
			
			if (args.length == 2) {
				if (!partyManager.hasParty(player)) {
					if (partyManager.hasParty(Util.getPlayer(args[1]))) {
						Party party = partyManager.getParty(Util.getPlayer(args[1]));
						
						if (party.getSettings().getAnyJoin()) {
							party.addMember(player);
						} else {
							ChatUtil.sendMessage(player, "&cYou are not allowed to join this party!");
						}
					} else {
						ChatUtil.sendMessage(player, "&cThis party is no longer valid!");
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
		return "&cUsage: /party join <username>";
	}
}
