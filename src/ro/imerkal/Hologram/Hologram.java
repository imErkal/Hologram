package ro.imerkal.Hologram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import com.google.common.collect.Lists;

public class Hologram {
	
	public String name;
	private Location loc;
	private ArrayList<String> lines = new ArrayList<>();
	private HashMap<Location, Entity> entities = new HashMap<>();

	public Hologram(String name, Location loc) {
		this.name = name;
		this.loc = loc;
	}
	
	public Hologram(String name, World world, double x, double y, double z) {
		this.name = name;
		this.loc = new Location(world, x, y, z);
	}
	
	public Hologram(String name, String world, double x, double y, double z) {
		this.name = name;
		this.loc = new Location(Bukkit.getWorld(world), x, y, z);
	}
	
	public Hologram setLine(int line, String text) {
		this.lines.set(line, text);
		return this;
	}
	
	public Hologram addLines(List<String> lines) {
		for(String line : lines) {
			this.lines.add(line);
		}
		return this;
	}
	
	public Hologram addLine(String line) {
		this.lines.add(line);
		return this;
	}
	
	public Hologram removeLine(String line) {
		this.lines.remove(line);
		return this;
	}

	public ArrayList<String> getLines() {
		return lines;
	}
	
	public Hologram spawn() {
		Location loc = this.loc;
		for(String line : Lists.reverse(this.lines)) {
			ArmorStand entity = (ArmorStand) this.loc.getWorld().spawnEntity(this.loc, EntityType.ARMOR_STAND);
			entity.setCustomName(line);
			entity.setCustomNameVisible(true);
			entity.setVisible(false);
			entity.setGravity(false);
			entity.setBasePlate(false);
			loc = loc.add(0.0D, 0.25D, 0.0D);
		}
		return this;
	}
	
	public void remove(Location loc) {
		if(this.entities.containsKey(loc)) {
			this.entities.get(loc).remove();
			this.entities.remove(loc);
		}
	}
}