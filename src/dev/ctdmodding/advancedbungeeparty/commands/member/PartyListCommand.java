package dev.ctdmodding.advancedbungeeparty.commands.member;

import dev.ctdmodding.advancedbungeeparty.PartySystem;
import dev.ctdmodding.advancedbungeeparty.commands.SubCommand;
import dev.ctdmodding.advancedbungeeparty.core.Party;
import dev.ctdmodding.advancedbungeeparty.managers.PartyManager;
import dev.ctdmodding.advancedbungeeparty.utils.ChatUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyListCommand extends SubCommand {

	public PartyListCommand(PartySystem plugin) {
		super("list", plugin);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer player = (ProxiedPlayer) sender;
			
			if (args.length == 1) {
				PartyManager partyManager = getPlugin().getPartyManager();
				Party party;
				
				if (partyManager.hasParty(player)) {
					party = partyManager.getParty(player);
					ChatUtil.sendMessage(player, party.getPartyList());
				} else {
					ChatUtil.sendMessage(player, "&cYou aren't in a party!");
				}
			} else {
				ChatUtil.sendMessage(player, getUsage());
			}
		}
	}

	@Override
	public String getUsage() {
		return "&cUsage: /party list";
	}
}
