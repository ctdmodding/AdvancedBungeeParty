package dev.ctdmodding.advancedbungeeparty.commands.member;

import dev.ctdmodding.advancedbungeeparty.PartySystem;
import dev.ctdmodding.advancedbungeeparty.commands.SubCommand;
import dev.ctdmodding.advancedbungeeparty.utils.ChatUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyHelpCommand extends SubCommand {

	public PartyHelpCommand(PartySystem plugin) {
		super("help", plugin);
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer player = (ProxiedPlayer) sender;
			
			if (args.length <= 1) {
				ChatUtil.sendMessage(player, "&9--------------------&bParty Commands&9--------------------");
				ChatUtil.sendMessage(player, "&b/party invite <username> - &7Invite a player to your party");
				ChatUtil.sendMessage(player, "&b/party accept <username> - &7Accept a party invite");
				ChatUtil.sendMessage(player, "&b/party leave - &7Leaves the party you're in");
				ChatUtil.sendMessage(player, "&b/party list - &7Lists the players in your party");
				ChatUtil.sendMessage(player, "&b/party chat <message> - &7Talk in party chat");
				ChatUtil.sendMessage(player, "&b/party kick <username> - &7Kick a player from your party");
				ChatUtil.sendMessage(player, "&b/party warp - &7Warp your party to your server");
				ChatUtil.sendMessage(player, "&b/party promote <username> - &7Promote a player to the party leader");
				ChatUtil.sendMessage(player, "&b/party disband - &7Disband your party");
				ChatUtil.sendMessage(player, "&b/party settings <allinvite | public | autowarp | list> - &7Toggle party settings");
				ChatUtil.sendMessage(player, "&b/party roulette - &7Promote a random player to the party leader");
				ChatUtil.sendMessage(player, "&b/party merge <username> - &7Merge another party with yours");
				ChatUtil.sendMessage(player, "&b/party toggle - &7Toggle the party chat");
				ChatUtil.sendMessage(player, "&b/party join <username> - &7Join a public party");
				ChatUtil.sendMessage(player, "&b/party tp - &7Teleport to the leader's server");
				ChatUtil.sendMessage(player, "&9-----------------------------------------------------");
			} else {
				ChatUtil.sendMessage(player, getUsage());
			}
		}
	}

	@Override
	public String getUsage() {
		return "&cUsage: /party help";
	}
}
