package com.gmail.adriandc9904;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.adriandc9904.comandos.Comandos;
import com.gmail.adriandc9904.eventos.Eventos;

public class Sugerencias extends JavaPlugin {
	
	PluginDescriptionFile pdffile = getDescription();
	public String version = ChatColor.GREEN + pdffile.getVersion();
	public String nombre = ChatColor.DARK_BLUE + "[" + ChatColor.BLUE + pdffile.getName() + ChatColor.DARK_BLUE + "] ";
	public String autor = ChatColor.GREEN + "adrian9904";
	
	//Config
	public String rutaConfig;
	
	private void msg(String texto) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', nombre + texto));
	}
	
	@Override
	public void onEnable() {
		msg("&aLas sugerencias estan listas para ser recibidas");
		Comandos();
		Eventos();
		Configuracion();
	}
	
	@Override
	public void onDisable() {
		msg("&cLas sugerencias fueron desactivadas");
	}
	
	public void Comandos() {
		this.getCommand("sugerencia").setExecutor(new Comandos(this));
	}
	
	public void Eventos() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new Eventos(this), this);
	}
	
	public void Configuracion() {
		File config = new File(this.getDataFolder(), "config.yml");
		rutaConfig = config.getPath();
		if (!config.exists()) {
			this.getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}
}
