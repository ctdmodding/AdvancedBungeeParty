package dev.ctdmodding.advancedbungeeparty.commands.leader;

import dev.ctdmodding.advancedbungeeparty.PartySystem;
import dev.ctdmodding.advancedbungeeparty.commands.SubCommand;
import dev.ctdmodding.advancedbungeeparty.core.Party;
import dev.ctdmodding.advancedbungeeparty.managers.PartyManager;
import dev.ctdmodding.advancedbungeeparty.settings.PartySettings;
import dev.ctdmodding.advancedbungeeparty.utils.ChatUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartySettingsCommand extends SubCommand {
	
	public PartySettingsCommand(PartySystem plugin) {
		super("settings", plugin);
	}

	@Override
	public String getUsage() {
		return "&cUsage: /party settings <allinvite | public | autowarp | list>";
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			PartyManager partyManager = getPlugin().getPartyManager();
			ProxiedPlayer player = (ProxiedPlayer) sender;
			
			if (args.length == 2) {
				if (partyManager.hasParty(player)) {
					Party party = partyManager.getParty(player);
					PartySettings settings = party.getSettings();
					
					if (party.getLeaderID().equals(player.getUniqueId()) || args[1].equalsIgnoreCase("list")) {
						switch (args[1].toLowerCase()) {
							case "allinvite":
								settings.toggleAllInvite();
								if (settings.getAllInvite())
									party.broadcastPartyMessage("&aAll Invite was enabled! You may now invite anyone to the party!");
								else
									party.broadcastPartyMessage("&cAll Invite was disabled! You may not invite anyone else to the party!");
								break;
							case "public":
								settings.toggleAnyJoin();
								if (settings.getAnyJoin())
									party.broadcastPartyMessage("&aPublic Joining was enabled!");
								else
									party.broadcastPartyMessage("&cPublic Joining was disabled!");
								break;
							case "autowarp":
								settings.toggleAutoWarp();
								if (settings.getAutoWarp())
									party.broadcastPartyMessage("&aAutomatic Warping was enabled!");
								else
									party.broadcastPartyMessage("&cAutomatic Warping was disabled!");
								break;
							case "list":
								ChatUtil.sendMessage(player, "AllInvite: " + (settings.getAllInvite() ? "&aEnabled" : "&cDisabled"));
								ChatUtil.sendMessage(player, "Public: " + (settings.getAnyJoin() ? "&aEnabled" : "&cDisabled"));
								ChatUtil.sendMessage(player, "AutoWarp: " + (settings.getAutoWarp() ? "&aEnabled" : "&cDisabled"));
								break;
							default:
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
