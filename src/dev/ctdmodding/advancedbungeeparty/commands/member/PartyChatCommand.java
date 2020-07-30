package dev.ctdmodding.advancedbungeeparty.commands.member;

import dev.ctdmodding.advancedbungeeparty.PartySystem;
import dev.ctdmodding.advancedbungeeparty.commands.SubCommand;
import dev.ctdmodding.advancedbungeeparty.core.Party;
import dev.ctdmodding.advancedbungeeparty.managers.PartyManager;
import dev.ctdmodding.advancedbungeeparty.utils.ChatUtil;
import dev.ctdmodding.advancedbungeeparty.utils.Util;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyChatCommand extends SubCommand {

	public PartyChatCommand(PartySystem plugin) {
		super("chat", plugin);
	}
	
	@Override
	public String getUsage() {
		return "&cUsage: /party chat <message>";
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			PartyManager partyManager = getPlugin().getPartyManager();
			ProxiedPlayer player = (ProxiedPlayer) sender;
			
			if (args.length > 1) {
				if (partyManager.hasParty(player)) {
					Party party = partyManager.getParty(player);
					
					party.sendPartyChat(player, Util.argsToString(args, 1));
				} else {
					ChatUtil.sendMessage(player, "&cYou are not in a party!!");
				}
			} else {
				ChatUtil.sendMessage(player, getUsage());
			}
		}
	}
}
