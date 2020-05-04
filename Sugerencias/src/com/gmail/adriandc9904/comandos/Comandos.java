package com.gmail.adriandc9904.comandos;

import java.util.ArrayList;
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

public class Comandos implements CommandExecutor {
	
	private Sugerencias plugin;
	
	public Comandos(Sugerencias plugin) {
		this.plugin = plugin;
	}

	private void msg(CommandSender sender, String texto) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', texto));
	}
	
	private void msg(CommandSender sender, List<String> lista) {
		for(int i = 0; i < lista.size() ;i++) {
			msg(sender, lista.get(i));
		}
	}
	
	private List<String> comandos() {
		List<String> cmd = new ArrayList<String>();
		cmd.add("&cLos comandos existentes son:");
		cmd.add("&a/sugerencia (tu sugerencia) &7:Añade una sugerencia a la lista");
		cmd.add("&a/sugerencia leer (indice) &7:Lee una de las sugerencias guardadas");
		cmd.add("&a/sugerencia lista importantes &7:Mira las sugerencias importantes");
		cmd.add("&a/sugerencia lista [pagina] &7:Mira la lista de sugerencias");
		cmd.add("&a/sugerencia borrar (indice) &7:Borra una de las sugerencias");
		cmd.add("&a/sugerencia importante (indice) &7:Guarda una sugerencia como importante");
		cmd.add("&a/sugerencia cantidad &7:Muesta la cantidad de sugerencias");
		cmd.add("&a/sugerencia reload &7:Recarga la config.yml");
		cmd.add("&a/sugerencia ayuda &7:Abre este menu de ayuda");
		return cmd;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("sugerencia") || command.getAliases().contains(label)) {
			
			FileConfiguration config = plugin.getConfig();
			List<String> lista = config.getStringList("sugerencias");
			List<String> importante = config.getStringList("sug-importantes");
			
			if(args.length == 0) {
				msg(sender, "&aPara escribir tu sugerencia, pon &7/sugerencia (tu sugerencia)");	
				if(sender.hasPermission("sugerencia.ayuda")) {
					msg(sender, "&aMira los comandos disponibles con /sugerencia help");
				}
			}
			
			else if(args[0].equalsIgnoreCase("leer") && sender.hasPermission("sugerencia.leer")) {
				if(args.length == 2) {
					try {
						int indice = Integer.valueOf(args[1]);
						if(indice <= lista.size() && indice > 0) {
							sender.sendMessage(plugin.nombre + ChatColor.RESET + lista.get(indice - 1));
						}
						else {
							if(lista.size() < 1) {
								msg(sender, "&cLa lista esta &7vacia&a.");
							}
							else {
								msg(sender, "&cEl indice que escogiste es muy &7alto &co muy &7bajo.");	
							}
						}
					}
					catch(NumberFormatException e) {
						msg(sender, "&cEscribe un numero valido.");
					}
				}
				else {
					msg(sender, "&aEl uso correcto del comando es &7/sugerencia leer (indice)");
				}
			}
			
			else if(args[0].equalsIgnoreCase("borrar") && sender.hasPermission("sugerencia.borrar")) {
				if(args.length == 2) {
					try {
						int eliminacion = Integer.valueOf(args[1]) - 1;   
						if(Integer.valueOf(args[1]) > 0 && Integer.valueOf(args[1]) <= lista.size()) {
							lista.remove(eliminacion);
							config.set("sugerencias", lista);
							plugin.saveConfig();
							msg(sender, "&aLa sugerencia fue borrada con exito");
						}
						else if(eliminacion < 0) {
							msg(sender, "&cNecesitas ingresar un numero positivo.");
						}
						else if(lista.size() < 1) {
							msg(sender, "&cLa lista esta &7vacia&a.");
						}
						else {
							msg(sender, "&cEste numero es mayor que las sugerencias, usa &7/sugerencia cantidad");
						}
					}
					catch(NumberFormatException e) {
						msg(sender, "&cEscribe un numero valido ");
					}
				}
				else {
					msg(sender, "&aEl uso correcto del comando es &7/sugerencia borrar (indice)");
				}
			}
			
			else if(args[0].equalsIgnoreCase("lista") && sender.hasPermission("sugerencia.lista")) {
				if(args.length == 1) {
					msg(sender, plugin.nombre + "&cLas sugerencias más antiguas hechas fueron:");
					for(int i = 0; i < 10; i++) {
						if(lista.size() > i) {
							int orden = i + 1;
							sender.sendMessage(ChatColor.GREEN + "" + orden + ". " + ChatColor.GRAY + "" + lista.get(i));
						}
					}
					if(lista.size() == 0) {
						msg(sender, "&aLa lista de sugerencias esta &7vacia");
					}
				}
				else if(args.length == 2) {
					try {
						int pagina = Integer.valueOf(args[1]);
						if(pagina > 0 && lista.size() > 0) {
							if(((pagina - 1) * 10) + 1 <= lista.size()) {
								msg(sender, plugin.nombre + "&cLas sugerencias en la pagina " + pagina + " son:");
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
								msg(sender, "&cLa pagina que seleccionaste esta &7vacia");
							}
						}
						else {
							msg(sender, "&cLa pagina que seleccionaste esta &7vacia &co es un numero &7invalido");
						}
					}
					catch(NumberFormatException error) {
						if(args[1].equalsIgnoreCase("importantes") || args[1].equalsIgnoreCase("importante")) {
							msg(sender, plugin.nombre + "&cSugerencias marcadas como importantes:");
							for(int i = 0; i < 10; i++) {
								if(importante.size() > i) {
									int orden = i + 1;
									sender.sendMessage(ChatColor.GREEN + "" + orden + ". " + ChatColor.GRAY + "" + importante.get(i));
								}
							}
							if(importante.size() == 0) {
								msg(sender, "&aLa lista de sugerencias esta &7vacia");
							}
						}
						else {
							msg(sender, "&aUsa &7/sugerencias lista &ao &7/sugerencias lista importantes");
						}
					}
				}
				else {
					msg(sender, "&aUsa &7/sugerencias lista &ao &7/sugerencias lista importantes");
				}
			}
			
			else if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("sugerencia.reload")) {
				plugin.reloadConfig();
				msg(sender, plugin.nombre + "&aSugerencias recargadas correctamente");
			}
			
			else if((args[0].equalsIgnoreCase("importante") || args[0].equalsIgnoreCase("importantes"))
					&& sender.hasPermission("sugerencia.importante")) {
				if(args.length == 2) {
					try {
						int imp = Integer.valueOf(args[1]) - 1;
						if(imp >= 0) {
							importante.add(lista.get(imp));
							config.set("sug-importantes", importante);
							plugin.saveConfig();
							msg(sender, "&aSugerencia marcada como &7&limportante &acorrectamente.");
						}
						else {
							msg(sender, "&cIngresa un &7numero positivo &ccomo &7indice");
						}
					}
					catch(NumberFormatException e) {
						msg(sender, "&cIngresa un &7numero valido &ccomo &7indice");
					}
				}
				else {
					msg(sender, "&a el uso correcto del comando es &7/sugerencia importante (indice)");
				}
				//Aun falta evitar que se repitan, se puede poner /sugerencia importante 1 varias veces dandonos la misma sugerencia
			}
			else if(args[0].equalsIgnoreCase("cantidad") && sender.hasPermission("sugerencia.leer")) {
				msg(sender, "&aActualmente hay &7" + lista.size() + " &asugerencias almacenadas.");
			}
			else if((args[0].equalsIgnoreCase("ayuda") || args[0].equalsIgnoreCase("help")) &&
					args.length == 1 && sender.hasPermission("sugerencia.ayuda")) {
				msg(sender, comandos());
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
					msg(sender, "&aSugerencia guardada con exito");
				}
			}
			return true;
		}
		return false;
	}
	
	private boolean cooldown(CommandSender sender) {
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
			msg(sender, "&cAun necesitas esperar &7" + c.getCooldown(uuid)+ " &cpara hacer otra sugerencia.");
			return false;
		}
		return true;
	}
}