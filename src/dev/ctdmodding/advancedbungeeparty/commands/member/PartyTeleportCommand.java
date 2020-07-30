package dev.ctdmodding.advancedbungeeparty.commands.member;

import dev.ctdmodding.advancedbungeeparty.PartySystem;
import dev.ctdmodding.advancedbungeeparty.commands.SubCommand;
import dev.ctdmodding.advancedbungeeparty.core.Party;
import dev.ctdmodding.advancedbungeeparty.managers.PartyManager;
import dev.ctdmodding.advancedbungeeparty.utils.ChatUtil;
import dev.ctdmodding.advancedbungeeparty.utils.Util;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyTeleportCommand extends SubCommand {

	public PartyTeleportCommand(PartySystem plugin) {
		super("tp", plugin);
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer player = (ProxiedPlayer) sender;
			
			if (args.length == 1) {
				PartyManager partyManager = getPlugin().getPartyManager();
				Party party;
				ProxiedPlayer partyLeader;
				
				if (partyManager.hasParty(player)) {
					party = partyManager.getParty(player);
					
					if (Util.isOnline(party.getLeaderID())) {
						partyLeader = Util.getPlayer(party.getLeaderID());
						
						if (!partyLeader.getUniqueId().equals(player.getUniqueId())) {
							ChatUtil.sendMessage(player, "&aSending you to " + party.getLeaderDisplayName() + "&a's server!");
							player.connect(partyLeader.getServer().getInfo());
						} else {
							ChatUtil.sendMessage(player, "&cYou cannot teleport to yourself!");
						}
					} else {
						ChatUtil.sendFormattedMessage(player, party.getLeaderDisplayName() + " &cis currently offline!");
					}
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
		return "&cUsage: /party tp";
	}
}
