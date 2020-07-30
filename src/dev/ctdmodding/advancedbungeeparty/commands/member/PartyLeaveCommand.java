package dev.ctdmodding.advancedbungeeparty.commands.member;

import dev.ctdmodding.advancedbungeeparty.PartySystem;
import dev.ctdmodding.advancedbungeeparty.commands.SubCommand;
import dev.ctdmodding.advancedbungeeparty.core.Party;
import dev.ctdmodding.advancedbungeeparty.managers.PartyManager;
import dev.ctdmodding.advancedbungeeparty.utils.ChatUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyLeaveCommand extends SubCommand {

	public PartyLeaveCommand(PartySystem plugin) {
		super("leave", plugin);
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			PartyManager partyManager = getPlugin().getPartyManager();
			ProxiedPlayer player = (ProxiedPlayer) sender;
			
			if (args.length == 1) {
				if (partyManager.hasParty(player)) {
					Party party = partyManager.getParty(player);
					
					if (!party.getLeaderID().equals(player.getUniqueId())) {
						party.removeMemberLeft(player);
					} else {
						ChatUtil.sendMessage(player, "&cYou are the party leader. Please promote someone or disband the party!");
					}
				} else {
					ChatUtil.sendMessage(player, "&cYou are not in a party!!");
				}
			} else {
				ChatUtil.sendMessage(player, getUsage());
			}
		}
	}
	
	@Override
	public String getUsage() {
		return "&cUsage: /party leave";
	}
}
