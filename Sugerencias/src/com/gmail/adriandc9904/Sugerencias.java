package com.gmail.adriandc9904;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.adriandc9904.actualizar.Actualizar;
import com.gmail.adriandc9904.comandos.Comandos;
import com.gmail.adriandc9904.eventos.Eventos;

public class Sugerencias extends JavaPlugin {
	
	PluginDescriptionFile pdffile = getDescription();
	public String version = ChatColor.GREEN + pdffile.getVersion();
	public String nombre = ChatColor.DARK_BLUE + "[" + ChatColor.BLUE + pdffile.getName() + ChatColor.DARK_BLUE + "] ";
	public String autor = ChatColor.GREEN + "adrian9904";
	
	//Config
	public String rutaConfig;
	
	// Messages
	private FileConfiguration messages = null;
	private File messagesFile = null;
	
	private void msg(String texto) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', nombre + texto));
	}
	
	//Lenguaje
	private String lenguaje = "messages_ES";
	
	@Override
	public void onEnable() {
		
		Configuracion();
		
		//Cargar version de la config
		int versionConfig = 0;
		
		if(getConfig().get("config-version") != null) {
			versionConfig = getConfig().getInt("config-version");
		}
		
		Actualizar update = new Actualizar(this);
		update.configVersion(2/*version actual*/, versionConfig);
		
		
		//Plugin Iniciado
		if(getConfig().getString("language").equalsIgnoreCase("messages_EN")) {
			msg("&aSuggestions are ready to be received");
		}
		else {
			msg("&aLas sugerencias estan listas para ser recibidas");
		}
		
		//Cargar todo despues de que se termine de crear y actualizar la config
		Messages();
		Comandos();
		Eventos();
		lenguaje = getConfig().getString("language");
		
		//Buscar idiomas
		List<String> idiomas = new ArrayList<String>();
		idiomas.add("messages_ES");
		idiomas.add("messages_EN");
		
		for(int i = 0; idiomas.size() > i; i++) {
			if(!getConfig().getString("language").equalsIgnoreCase(idiomas.get(i))) {
				if(idiomas.size() == i + 1) {
					msg("&cEl idioma escrito en la config.yml aun no esta registrado, se usara el idioma español");
					i = idiomas.size();
				}
			}
			else {
				break;
			}
		}
	}
	
	@Override
	public void onDisable() {
		if(getConfig().getString("language").equalsIgnoreCase("messages_EN")) {
			msg("&cSuggestions were disabled");
		}
		else {
			msg("&cLas sugerencias fueron desactivadas");
		}
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
	
	public FileConfiguration getMessages() {
		if (messages == null) {
			reloadMessages();
		}
		return messages;
	}
	
	public void reloadMessages() {
		try {
			if (messages == null) {
				messagesFile = new File(getDataFolder(), lenguaje + ".yml");
			}
			messages = YamlConfiguration.loadConfiguration(messagesFile);
			Reader defConfigStream;
			try {
				defConfigStream = new InputStreamReader(this.getResource(lenguaje + ".yml"), "UTF8");
				if (defConfigStream != null) {
					YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
					messages.setDefaults(defConfig);
				}
			} catch (UnsupportedEncodingException error) {
				error.printStackTrace();
			}
		}
		catch(NullPointerException e){
			lenguaje = "messages_ES";
			if (messages == null) {
				messagesFile = new File(getDataFolder(), lenguaje + ".yml");
			}
			messages = YamlConfiguration.loadConfiguration(messagesFile);
			Reader defConfigStream;
			try {
				defConfigStream = new InputStreamReader(this.getResource(lenguaje + ".yml"), "UTF8");
				if (defConfigStream != null) {
					YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
					messages.setDefaults(defConfig);
				}
			} catch (UnsupportedEncodingException error) {
				error.printStackTrace();
			}
		}
	}

	public void saveMessages() {
		try {
			messages.save(messagesFile);
		} catch (IOException error) {
			error.printStackTrace();
		}
	}

	public void Messages() {
		try {
			messagesFile = new File(this.getDataFolder(), lenguaje + ".yml");
			if (!messagesFile.exists()) {
				this.getMessages().options().copyDefaults(true);
				saveMessages();
			}
		}
		catch(NullPointerException e) {
			lenguaje = "messages_ES";
			messagesFile = new File(this.getDataFolder(), lenguaje + ".yml");
			if (!messagesFile.exists()) {
				this.getMessages().options().copyDefaults(true);
				saveMessages();
			}
		}
	}
}
