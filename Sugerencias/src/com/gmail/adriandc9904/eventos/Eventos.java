package com.gmail.adriandc9904.eventos;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.gmail.adriandc9904.Sugerencias;
import com.gmail.adriandc9904.data.Data;
import com.gmail.adriandc9904.mensajes.Messages;

public class Eventos implements Listener {
	
	private Sugerencias plugin;
	
	public Eventos(Sugerencias plugin) {
		this.plugin = plugin;
	}
	
	private void msg(Player jugador, String texto) {
		jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto
				.replaceAll("%amount%", String.valueOf(plugin.getConfig().getList("sugerencias").size())))); //falta probar
	}
	
	@EventHandler
	public void Notificar(PlayerJoinEvent event) {
		Messages mensajes = new Messages(plugin);
		
		Player jugador = event.getPlayer();
		if(jugador.hasPermission("sugerencia.leer")) {
			FileConfiguration config = plugin.getConfig();
			if(config.getBoolean("notificar")) {
				msg(jugador, mensajes.joinEvent());
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
