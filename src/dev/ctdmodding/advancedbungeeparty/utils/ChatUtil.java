package dev.ctdmodding.advancedbungeeparty.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ChatUtil {

	public static String colorize(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public static void sendMessage(ProxiedPlayer player, String message) {
		player.sendMessage(TextComponent.fromLegacyText(colorize(message)));
	}
	
	public static void sendFormattedMessage(ProxiedPlayer player, String message) {
		sendMessage(player, toFormattedMessage(message));
	}
	
	public static String toFormattedMessage(String message) {
		String header = "&b-----------------------------------------------------";
		return String.format("%s\n&f%s\n%s", header, message, header);
	}
}
