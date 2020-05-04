package com.gmail.adriandc9904.data;

import java.util.HashMap;
import java.util.UUID;

public class Data {
	
	public static HashMap<UUID, Integer> sugerencias = new HashMap<UUID, Integer>();
	public static HashMap<UUID, String> cooldown = new HashMap<UUID, String>();
	
	public static void CrearJugador(UUID uuid) {
		if(!sugerencias.containsKey(uuid)) {
			sugerencias.put(uuid, 0);
		}
		if(!cooldown.containsKey(uuid)) {
			cooldown.put(uuid, "-1");
		}
	}
	
	public static int getSugerencias(UUID uuid) {
		int sughechas = sugerencias.get(uuid);
		return sughechas;
	}
	
	public final static void addSugerencias(UUID uuid) {
		int sughechas = sugerencias.get(uuid);
		sugerencias.put(uuid, sughechas + 1);
	}
	
	public final static void setSugerencias(UUID uuid, int cantidad) {
		sugerencias.put(uuid, cantidad);
	}
	
	public static String getCooldown(UUID uuid) {
		String cd = cooldown.get(uuid);
		return cd;
	}
	public final static void setCooldown(UUID uuid, String time) {
		cooldown.put(uuid, time);
	}
}
