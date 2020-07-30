package dev.ctdmodding.advancedbungeeparty.commands.leader;

import dev.ctdmodding.advancedbungeeparty.PartySystem;
import dev.ctdmodding.advancedbungeeparty.commands.SubCommand;
import dev.ctdmodding.advancedbungeeparty.core.Party;
import dev.ctdmodding.advancedbungeeparty.managers.PartyManager;
import dev.ctdmodding.advancedbungeeparty.utils.ChatUtil;
import dev.ctdmodding.advancedbungeeparty.utils.Util;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Random;
import java.util.UUID;

public class PartyRouletteCommand extends SubCommand {

	public PartyRouletteCommand(PartySystem plugin) {
		super("roulette", plugin);
	}

	@Override
	public String getUsage() {
		return "&cUsage: /party roulette";
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			PartyManager partyManager = getPlugin().getPartyManager();
			ProxiedPlayer player = (ProxiedPlayer) sender;
			
			if (args.length == 1) {
				if (partyManager.hasParty(player)) {
					Party party = partyManager.getParty(player);
					
					if (party.getLeaderID().equals(player.getUniqueId())) {
						Random rand = new Random();
						int choice;

						if (party.getMembers().size() > 0) {
							choice = rand.nextInt(party.getMembers().size());
							BungeeCord.getInstance().broadcast(ChatColor.RED + String.format("Size: %d - Choice: %d - Index: %d", party.getMembers().size(), choice, choice - 1));

							Object[] values = party.getMembers().keySet().toArray();
							Object randomValue = values[rand.nextInt(values.length)];
							UUID uuid = (UUID) randomValue;
							if (uuid != null) {
								if (Util.isOnline(uuid)) {
									party.promote(Util.getPlayer(uuid));
								} else {
									ChatUtil.sendMessage(player, "Selected member is offline. Re-roll!");
								}
							}
						} else {
							ChatUtil.sendMessage(player, "&cYour party isn't big enough! Invite more players!");
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
