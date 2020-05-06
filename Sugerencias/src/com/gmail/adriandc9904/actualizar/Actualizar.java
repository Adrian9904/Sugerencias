package com.gmail.adriandc9904.actualizar;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.adriandc9904.Sugerencias;

public class Actualizar {
	
	private Sugerencias plugin;
	
	public Actualizar(Sugerencias plugin) {
		this.plugin = plugin;
	}
	
	public void configVersion(int versionActual, int versionConfig) {
		FileConfiguration config = plugin.getConfig();
		
		List<String> sug = new ArrayList<String>();
		sug.add("Aca se guardaran las sugerencias. adrian9904");
		sug.add("Here the suggestions will be saved. adrian9904");
		
		List<String> sugImp = new ArrayList<String>();
		sugImp.add("Aca se guardan las sugerencias marcadas como importantes con /sugerencia importante (indice)");
		sugImp.add("Suggestions marked as important with /suggestion important (index) are saved here");
		
		if(versionActual > versionConfig) {
			if(config.get("activar") == null) config.set("activar", true);
			if(config.get("cooldown") == null) config.set("cooldown", 3600);
			if(config.get("notificar") == null) config.set("notificar", true);
			if(config.get("language") == null) config.set("language", "messages_ES");
			if(config.get("sugerencias-permitidas") == null) config.set("sugerencias-permitidas", 2);
			if(config.get("sugerencias") == null) config.set("sugerencias", sug);
			if(config.get("sug-importantes") == null) config.set("sug-importantes", sugImp);
			if(config.get("config-version") == null) config.set("config-version", 1);
			
			
			config.set("config-version", versionActual);
			plugin.saveConfig();
			
			if(config.getString("language").equalsIgnoreCase("messages_EN")) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', 
						plugin.nombre + "&aFile config.yml updated"));
			}
			else {
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', 
						plugin.nombre + "&aArchivo config.yml actualizado"));
			}
			return;
		}
	}
}
