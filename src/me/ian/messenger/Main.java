package me.ian.messenger;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	// Stores sender and receiver for use in /reply command
	public HashMap<String, String> conversations = new HashMap<String, String>();
	
	
	// Suppresses deprecated functions (out of date)
	@SuppressWarnings("deprecation")
	// The following code runs when a command is executed
	public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
		// Checks if the command was a /message command (or alias)
		if (label.equalsIgnoreCase("message") || label.equalsIgnoreCase("msg") || label.equalsIgnoreCase("m") || label.equalsIgnoreCase("tell") || label.equalsIgnoreCase("t") || label.equalsIgnoreCase("whisper") || label.equalsIgnoreCase("w") || label.equalsIgnoreCase("pm")) {
			// This checks if the command sender was not a player (eg. console command)
			if (!(sender instanceof Player)) {
				// Sends a message to the command sender telling them that only players can send messages
				sender.sendMessage("§cOnly players can send messages...");
				// Exits the command listener (no code will run after here)
				return true;
			}
			// Creates a player object from the sender input parameter
			Player player = (Player) sender;
			// Checks if the command was not "/msg (player) (message)"
			if (args.length < 2) {
				// Displays an error message
				player.sendMessage("§cInvalid arguments...");
				player.sendMessage("§cUsage: /msg (player) (message)");
				// Exits the command listener (no code will run after here)
				return true;
			}
			// Checks if the player they are trying to message is offline
			if (Bukkit.getPlayer(args[0]) == null) {
				// Sends an error messaging telling them that the person is offline
				player.sendMessage("§cThat person has gone offline...");
				// Exits the command listener (no code will run after here)
				return true;
			}
			// Creates a receiver player object out of the command's "(player)" value
			Player receiver = (Player) Bukkit.getPlayer(args[0]);
			// Deletes the receiver from their current reply conversation
			conversations.remove(receiver.getName());
			// Adds the receiver back into the reply conversation hashmap, with their conversation set to the player
			conversations.put(receiver.getName(), player.getName());
			// Creates and initialises the variable for the "(message)" part of the command
			String message = "";
			// Starts a for loop to add each argument after the "(message)" part of the command
			for (int i = 1; i < args.length; i++)
				// This adds a new part onto the message variable for each word in their message, to create a longer string
				message += " " + args[i];
			// This sends the receiver the message
			receiver.sendMessage("§6From " + player.getName() + " §8>&f" + message);
			// This sends the sender the message
			player.sendMessage("§6To " + receiver.getName() + " §8>&f" + message);
			// Exits the command listener (no code will run after here)
			return true;
		// Checks if the command was a /reply command (or alias) 
		} else if (label.equalsIgnoreCase("reply") || label.equalsIgnoreCase("r")) {
			// Checks if the command sender was not a player (eg. console command)
			if (!(sender instanceof Player)) {
				// Sends a message to the command sender telling them that only players can send messages
				sender.sendMessage("§cOnly players can send messages");
				// Exits the command listener (no code will run after here)
				return true;
			}
			// Creates a player object from the sender input parameter
			Player player = (Player) sender;
			// Checks if the command was not "/r (message)"
			if (args.length < 2) {
				// Displays an error message
				player.sendMessage("§cInvalid arguments...");
				player.sendMessage("§cUsage: /r (message)");
				// Exits the command listener (no code will run after here)
				return true;
			}
			// Checks if the player is in a conversation (if they have someone to reply to)
			if (!conversations.containsKey(player.getName())) {
				// Sends an error message
				player.sendMessage("§You have no one to reply to...");
				// Exits the command listener (no code will run after here)
				return true;
			}
			// Checks if the person they are replying to is offline
			if (Bukkit.getPlayer(conversations.get(player.getName())) == null) {
				// Sends an error message
				player.sendMessage("§cThat person has gone offline...");
				// Exits the command listener (no code will run after here)
				return true;
			}
			// Creates a receiver player object out of the receiver stored in the conversations hashmap
			Player receiver = Bukkit.getPlayer(conversations.get(player.getName()));
			// Removes the receiver from the hashmap
			conversations.remove(receiver.getName());
			// Restores the receiver to the hashmap with their new conversation player to reply to
			conversations.put(receiver.getName(), player.getName());
			// Creates and initialises the variable for the "(message)" part of the command
			String message = "";
			// Starts a for loop to add each argument after the "(message)" part of the command
			for (int i = 0; i < args.length; i++)
				// This adds a new part onto the messages variable for each word int their message to create a longer string
				message += " " + args[i];
			// This sends the receiver the message
			receiver.sendMessage("§6From " + player.getName() + " §8>&f" + message);
			// This sends the sender the message
			player.sendMessage("§6To " + receiver.getName() + " §8>&f" + message);
		}
		// Exits the command listener (no code will run after here)
		return false;
	}

}
