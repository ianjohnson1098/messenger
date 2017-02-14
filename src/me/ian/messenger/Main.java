package me.ian.messenger;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public HashMap<String, String> conversations = new HashMap<String, String>();
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
		if (label.equalsIgnoreCase("message") || label.equalsIgnoreCase("msg") || label.equalsIgnoreCase("m") || label.equalsIgnoreCase("tell") || label.equalsIgnoreCase("t") || label.equalsIgnoreCase("whisper") || label.equalsIgnoreCase("w") || label.equalsIgnoreCase("pm")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("§cOnly players can send messages...");
				return true;
			}
			Player player = (Player) sender;
			if (args.length < 2) {
				player.sendMessage("§cInvalid arguments...");
				player.sendMessage("§cUsage: /msg (player) (message)");
				return true;
			}
			if (Bukkit.getPlayer(args[0]) == null) {
				player.sendMessage("§cThat person has gone offline...");
				return true;
			}
			Player receiver = (Player) Bukkit.getPlayer(args[0]);
			conversations.remove(receiver.getName());
			conversations.put(receiver.getName(), player.getName());
			String message = "";
			for (int i = 1; i < args.length; i++)
				message += " " + args[i];
			receiver.sendMessage("§6From " + player.getName() + " §8>&f" + message);
			player.sendMessage("§6To " + receiver.getName() + " §8>&f" + message);
			return true;
		} else if (label.equalsIgnoreCase("reply") || label.equalsIgnoreCase("r")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("§cOnly players can send messages");
				return true;
			}
			Player player = (Player) sender;
			if (args.length < 2) {
				player.sendMessage("§cInvalid arguments...");
				player.sendMessage("§cUsage: /r (message)");
				return true;
			}
			if (!conversations.containsKey(player.getName())) {
				player.sendMessage("§You have no one to reply to...");
				return true;
			}
			if (Bukkit.getPlayer(conversations.get(player.getName())) == null) {
				player.sendMessage("§cThat person has gone offline...");
				return true;
			}
			Player receiver = Bukkit.getPlayer(conversations.get(player.getName()));
			conversations.remove(receiver.getName());
			conversations.put(receiver.getName(), player.getName());
			String message = "";
			for (int i = 0; i < args.length; i++)
				message += " " + args[i];
			receiver.sendMessage("§6From " + player.getName() + " §8>&f" + message);
			player.sendMessage("§6To " + receiver.getName() + " §8>&f" + message);
		}
		return false;
	}

}
