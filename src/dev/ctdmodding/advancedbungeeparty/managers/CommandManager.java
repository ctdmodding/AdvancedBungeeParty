package dev.ctdmodding.advancedbungeeparty.managers;

import dev.ctdmodding.advancedbungeeparty.commands.SubCommand;
import dev.ctdmodding.advancedbungeeparty.utils.ChatUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager extends Command {
	private Map<String, List<SubCommand>> commands;
	
	public CommandManager() {
		super("party", "", "p");
		
		commands = new HashMap<>();
	}
	
	public void registerCommand(String command, SubCommand subCommand) {
		System.out.println("*******Registering command: " + command + " - " + subCommand.getCommandName());
		try {
			if (!commands.get(command).contains(subCommand)) {
				commands.get(command).add(subCommand);
			}
		} catch (NullPointerException e) {
			List<SubCommand> subCommands = new ArrayList<>();
			subCommands.add(subCommand);
			commands.put(command, subCommands);
		}
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			for (String command : commands.keySet()) {
				for (SubCommand subCommand : commands.get(command)) {
					if (args.length > 0) {
						if (args[0].equalsIgnoreCase(subCommand.getCommandName())) {
							subCommand.execute(sender, args);
							return;
						}
					} else {
						if ("help".equalsIgnoreCase(subCommand.getCommandName())) {
							subCommand.execute(sender, args);
							return;
						}
					}
				}
			}
			ChatUtil.sendMessage((ProxiedPlayer) sender, "&cInvalid party command. See /party help");
		}
	}
}
