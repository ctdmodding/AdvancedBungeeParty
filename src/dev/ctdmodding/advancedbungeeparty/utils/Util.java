package dev.ctdmodding.advancedbungeeparty.utils;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class Util {
	
	/**
	 * Check if a user is currently connected to the bungee
	 * 
	 * @param username The username to find
	 * @return if they're online or not
	 */
	public static boolean isOnline(String username) {
		for (ProxiedPlayer pl : BungeeCord.getInstance().getPlayers()) {
			if (pl.getName().equalsIgnoreCase(username)) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Check if a user is currently connected to the bungee
	 * 
	 * @param uuid The uuid to find
	 * @return if they're online or not
	 */
	public static boolean isOnline(UUID uuid) {
		for (ProxiedPlayer pl : BungeeCord.getInstance().getPlayers()) {
			if (pl.getUniqueId().equals(uuid)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Get an online player based on their username
	 * 
	 * @param username the username to search
	 * @return the player
	 */
	public static ProxiedPlayer getPlayer(String username) {
		ProxiedPlayer target = null;

		for (ProxiedPlayer p : BungeeCord.getInstance().getPlayers()) {
			if (p.getName().equalsIgnoreCase(username)) {
				target = p;
				break;
			}
		}

		return target;
	}
	
	/**
	 * Get an online player based on their username
	 * 
	 * @param uuid the player's uuid
	 * @return the player
	 */
	public static ProxiedPlayer getPlayer(UUID uuid) {
		ProxiedPlayer target = null;

		for (ProxiedPlayer p : BungeeCord.getInstance().getPlayers()) {
			if (p.getUniqueId().equals(uuid)) {
				target = p;
				break;
			}
		}

		return target;
	}
	
	public static String argsToString(String[] args) {
		return argsToString(args, 0);
	}

	public static String argsToString(String[] args, int start) {
		StringBuilder concat = new StringBuilder();

		for (int i = start; i < args.length; i++) {
			concat.append(args[i]).append(" ");
		}

		return concat.toString().trim();
	}
	
	public static String fromBoolean(boolean value) {
		return value ? "&aEnabled" : "&cDisabled";
	}
	
	public static String stripRank(String displayName) {
		if (displayName.contains("[") && displayName.contains("]")) {
			return displayName.split("]")[1].trim();
		} else {
			return displayName;
		}
	}
}
