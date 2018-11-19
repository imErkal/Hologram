package ro.imerkal.Hologram;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements CommandExecutor {
	
	public void onEnable() {
		getCommand("createholo").setExecutor(this);
		getConfig().options().copyDefaults();
		saveConfig();
		if(!getConfig().getKeys(false).isEmpty()) {
			for(String name : getConfig().getKeys(false)) {
				String world = getConfig().getString(name + ".World");
				double x = getConfig().getDouble(name + ".X");
				double y = getConfig().getDouble(name + ".Y");
				double z = getConfig().getDouble(name + ".Z");
				List<String> lines = getConfig().getStringList(name + ".Lines");
				Location loc = new Location(Bukkit.getWorld(world), x, y, z);
				
				Hologram holo = new Hologram(name, loc);
				holo.addLines(lines);
				holo.spawn();
			}
		}
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(!(cs instanceof Player)) {
			cs.sendMessage("Only players can execute this command.");
		}else {
			Player p = (Player) cs;
			if(cmd.getName().equalsIgnoreCase("createholo")) {
				if(args.length == 0) {
					p.sendMessage("Usage /createholo <name>");
				}else {
					Hologram holo = new Hologram(args[0], p.getLocation());
					holo.addLine(args[0]);
					holo.spawn();
					saveHologram(args[0], p.getLocation(), holo.getLines());
				}
			}
		}
		return false;
	}
	
	private void saveHologram(String name, Location loc, List<String> lines) {
		String world = loc.getWorld().getName();
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		getConfig().set(name + ".World", world);
		getConfig().set(name + ".X", x);
		getConfig().set(name + ".Y", y);
		getConfig().set(name + ".Z", z);
		getConfig().set(name + ".Lines", lines);
		saveConfig();
	}
}