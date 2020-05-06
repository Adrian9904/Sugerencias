package com.gmail.adriandc9904.comandos;

import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.gmail.adriandc9904.Sugerencias;
import com.gmail.adriandc9904.data.*;
import com.gmail.adriandc9904.mensajes.Messages;

public class Comandos implements CommandExecutor {
	
	private Sugerencias plugin;
	
	public Comandos(Sugerencias plugin) {
		this.plugin = plugin;
	}

	private void msg(CommandSender sender, String texto) {
		String nombre = plugin.nombre.replace(" ", "");
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', texto
				.replaceAll("%plugin%", nombre)));
	}
	
	private void msg(CommandSender sender, String texto, int pagina) {
		String nombre = plugin.nombre.replace(" ", "");
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', texto
				.replaceAll("%plugin%", nombre).replaceAll("%page%", String.valueOf(pagina))));
	}
	
	private void msg(CommandSender sender, List<String> lista) {
		for(int i = 0; i < lista.size() ;i++) {
			msg(sender, lista.get(i));
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Messages mensajes = new Messages(plugin);
		
		if(label.equalsIgnoreCase("sugerencia") || command.getAliases().contains(label)) {
			
			FileConfiguration config = plugin.getConfig();
			List<String> lista = config.getStringList("sugerencias");
			List<String> importante = config.getStringList("sug-importantes");
			
			if(args.length == 0) {
				msg(sender, mensajes.unwrittenSug());
				if(sender.hasPermission("sugerencia.ayuda")) {
					msg(sender, mensajes.sugFormatHelp());
				}
			}
			
			else if((args[0].equalsIgnoreCase("leer") || args[0].equalsIgnoreCase("read"))
					&& sender.hasPermission("sugerencia.leer")) {
				if(args.length == 2) {
					try {
						int indice = Integer.valueOf(args[1]);
						if(indice <= lista.size() && indice > 0) {
							msg(sender, plugin.nombre + ChatColor.RESET + lista.get(indice - 1));
						}
						else {
							if(lista.size() < 1) {
								msg(sender, mensajes.emptyList());
							}
							else if(indice < 1) {
								msg(sender, mensajes.negativeIndex());
							}
							else {
								msg(sender, mensajes.topIndex());	
							}
						}
					}
					catch(NumberFormatException e) {
						msg(sender, mensajes.invalidIndex());
					}
				}
				else {
					msg(sender, mensajes.readFormat());
				}
			}
			
			else if((args[0].equalsIgnoreCase("borrar") || args[0].equalsIgnoreCase("delete"))
					&& sender.hasPermission("sugerencia.borrar")) {
				if(args.length == 2) {
					try {
						int eliminacion = Integer.valueOf(args[1]) - 1;   
						if(Integer.valueOf(args[1]) > 0 && Integer.valueOf(args[1]) <= lista.size()) {
							lista.remove(eliminacion);
							config.set("sugerencias", lista);
							plugin.saveConfig();
							msg(sender, mensajes.deleteSuccess());
						}
						else if(lista.size() < 1) {
							msg(sender, mensajes.emptyList());
						}
						else if(eliminacion < 0) {
							msg(sender, mensajes.negativeIndex());
						}
						else {
							msg(sender, mensajes.topIndex());
						}
					}
					catch(NumberFormatException e) {
						msg(sender, mensajes.invalidIndex());
					}
				}
				else {
					msg(sender, mensajes.deleteFormat());
				}
			}
			
			else if((args[0].equalsIgnoreCase("lista") || args[0].equalsIgnoreCase("list"))
					&& sender.hasPermission("sugerencia.lista")) {
				if(args.length == 1) {
					msg(sender, mensajes.listHeader());
					for(int i = 0; i < 10; i++) {
						if(lista.size() > i) {
							int orden = i + 1;
							msg(sender, "&a" + orden + ". &7" + lista.get(i));
						}
					}
					if(lista.size() == 0) {
						msg(sender, mensajes.emptyList());
					}
				}
				else if(args.length == 2) {
					try {
						int pagina = Integer.valueOf(args[1]);
						if(pagina > 0 && lista.size() > 0) {
							if(((pagina - 1) * 10) + 1 <= lista.size()) {
								msg(sender, mensajes.listPageHeader(), pagina);
								int i = 0;
								if(pagina > 1) {
									i = (pagina - 1) * 10;
								}
								for(; i < 10*pagina; i++) {
									if(lista.size() > i) {
										int orden = i + 1;
										msg(sender, "&a" + orden + ". &7"+ lista.get(i));
									}
								}
							}
							else {
								msg(sender, mensajes.emptyPage());
							}
						}
						else {
							msg(sender, mensajes.invalidPage());
						}
					}
					catch(NumberFormatException error) {
						if(args[1].equalsIgnoreCase("importantes") || args[1].equalsIgnoreCase("importante")
								|| args[1].equalsIgnoreCase("important")) {
							msg(sender, mensajes.listImportantHeader());
							for(int i = 0; i < 10; i++) {
								if(importante.size() > i) {
									int orden = i + 1;
									msg(sender, "&a" + orden + ". &7" + importante.get(i));
								}
							}
							if(importante.size() == 0) {
								msg(sender, mensajes.emptyList());
							}
						}
						else {
							msg(sender, mensajes.listFormat());
						}
					}
				}
				else {
					msg(sender, mensajes.listFormat());
				}
			}
			
			else if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("sugerencia.reload")) {
				plugin.reloadConfig();
				plugin.reloadMessages();
				msg(sender, mensajes.successReload());
			}
			
			else if((args[0].equalsIgnoreCase("importante") || args[0].equalsIgnoreCase("importantes")
					|| args[0].equalsIgnoreCase("important"))
					&& sender.hasPermission("sugerencia.importante")) {
				if(args.length == 2) {
					try {
						int imp = Integer.valueOf(args[1]) - 1;
						if(imp >= 0) {
							importante.add(lista.get(imp));
							config.set("sug-importantes", importante);
							plugin.saveConfig();
							msg(sender, mensajes.setImportantSuccessfully());
						}
						else {
							msg(sender, mensajes.negativeIndex());
						}
					}
					catch(NumberFormatException e) {
						msg(sender, mensajes.invalidIndex());
					}
				}
				else {
					msg(sender, mensajes.importantFormat());
				}
				//Aun falta evitar que se repitan, se puede poner /sugerencia importante 1 varias veces dandonos la misma sugerencia
			}
			
			else if((args[0].equalsIgnoreCase("cantidad") || args[0].equalsIgnoreCase("amount"))
					&& sender.hasPermission("sugerencia.leer")) {
				msg(sender, mensajes.sugAmount().replaceAll("%amount%", String.valueOf(lista.size())));
			}
			
			else if((args[0].equalsIgnoreCase("ayuda") || args[0].equalsIgnoreCase("help")) &&
					args.length == 1 && sender.hasPermission("sugerencia.ayuda")) {
				msg(sender, mensajes.helpList());
			}
			
			else {
				if(cooldown(sender)) {
					String texto = "";
					for(int i = 0; i < args.length; i++) {
						if(i < args.length - 1) {
							texto = texto + args[i] + " ";
						}
						else {
							texto = texto + args[i];
						}
					}
					texto = texto + ". " + sender.getName();
					lista.add(texto);
					config.set("sugerencias", lista);
					plugin.saveConfig();
					msg(sender, mensajes.success());
				}
			}
			return true;
		}
		return false;
	}
	
	private boolean cooldown(CommandSender sender) {
		Messages mensajes = new Messages(plugin);
		
		if(sender instanceof Player) {
			FileConfiguration config = plugin.getConfig();
			Player jugador = (Player) sender;
			Cooldown c = new Cooldown(plugin);
			UUID uuid = jugador.getUniqueId();
			int SugActuales = Data.getSugerencias(uuid);
			int SugPermitidas = config.getInt("sugerencias-permitidas");
			if(SugActuales < SugPermitidas) {
				Data.addSugerencias(uuid);
				if(Data.getSugerencias(uuid) >= SugPermitidas) {
					long millis = System.currentTimeMillis();
					Data.setCooldown(uuid, String.valueOf(millis));
				}
				return true;
			}
			else if(c.getCooldown(uuid).equals("-1")) {
				Data.setSugerencias(uuid, 1);
				if(Data.getSugerencias(uuid) >= SugPermitidas) {
					long millis = System.currentTimeMillis();
					Data.setCooldown(uuid, String.valueOf(millis));
				}
				return true;
			}
			msg(sender, mensajes.cooldown().replaceAll("%cooldown%", c.getCooldown(uuid)));
			return false;
		}
		return true;
	}
}