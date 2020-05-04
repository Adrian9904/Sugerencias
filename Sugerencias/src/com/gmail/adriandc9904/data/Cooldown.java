package com.gmail.adriandc9904.data;

import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import com.gmail.adriandc9904.Sugerencias;

public class Cooldown {
	
	private Sugerencias plugin;
	
	public Cooldown(Sugerencias plugin) {
		this.plugin = plugin;
	}
	
	public String getCooldown(UUID uuid) {
		FileConfiguration config = plugin.getConfig();
		String timecooldownString = Data.getCooldown(uuid);
		long timecooldown = Long.valueOf(timecooldownString);
		long millis = System.currentTimeMillis();
		long cooldown = config.getLong("cooldown"); // El cooldown de la config; En Segundos
		long cooldownmil = cooldown * 1000;

		long espera = millis - timecooldown;
		long esperaDiv = espera / 1000;
		long esperatotalseg = cooldown - esperaDiv;
		long esperatotalmin = esperatotalseg / 60;
		long esperatotalhour = esperatotalmin / 60;
		if (((timecooldown + cooldownmil) > millis) && (timecooldown != 0)) {
			if (esperatotalseg > 59) {
				esperatotalseg = esperatotalseg - 60 * esperatotalmin;
			}
			String time = "";
			if (esperatotalseg != 0) {
				time = esperatotalseg + "s";
			}

			if (esperatotalmin > 59) {
				esperatotalmin = esperatotalmin - 60 * esperatotalhour;
			}
			if (esperatotalmin > 0) {
				time = esperatotalmin + "min" + " " + time;
			}

			if (esperatotalhour > 0) {
				time = esperatotalhour + "h" + " " + time;
			}
			return time;
		} else {
			return "-1";
		}
	}
}
