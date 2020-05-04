package com.gmail.adriandc9904.eventos;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.gmail.adriandc9904.Sugerencias;
import com.gmail.adriandc9904.data.Data;

public class Eventos implements Listener {
	
	private Sugerencias plugin;
	
	public Eventos(Sugerencias plugin) {
		this.plugin = plugin;
	}
	
	private void msg(Player jugador, String texto) {
		jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto));
	}
	
	@EventHandler
	public void Notificar(PlayerJoinEvent event) {
		Player jugador = event.getPlayer();
		if(jugador.hasPermission("sugerencia.leer")) {
			FileConfiguration config = plugin.getConfig();
			if(config.getBoolean("notificar")) {
				msg(jugador, "&aHay &7" + config.getList("sugerencias").size() + " &asugerencias archivadas");
				//Falta crear una manera para deshabilitarlo :u
			}
		}
		return;
	}
	@EventHandler
	public void CrearJugador(PlayerJoinEvent event) {
		Player jugador = event.getPlayer();
		Data.CrearJugador(jugador.getUniqueId());
		return;
	}
}
