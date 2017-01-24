/*
 * Copyright (C) 2017 seyfahni
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.minetropolis.monsters;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 */
public final class DynamicMonstersPlugin extends JavaPlugin {

	public DynamicMonstersPlugin () {
	}

	@Override
	public void onEnable () {
		this.saveDefaultConfig();
		final Set<String> configKeys = this.getConfig().getKeys(true);
		this.getLogger().config("Dumping config keys:");
		configKeys.forEach(key -> this.getLogger().config(key));

		new ConfigurationParser(this).parseCurrentConfig();

		final Map<EntityType, Consumer<Entity>> modifications = new HashMap<>();
		modifications.put(EntityType.ZOMBIE, this::modifyMonster);
		getServer().getPluginManager().registerEvents(new MonsterSpawnEventListener(modifications), this);
		getCommand("dynamicmonster").setExecutor(this::dynamicMonsterCommand);
	}

	public void modifyMonster (Entity entity) {
		final double distance = entity.getWorld().getSpawnLocation().distanceSquared(entity.getLocation());
		final long protoLevel = Math.round(Math.sqrt(distance));
		final int level;
		if (protoLevel > Integer.MAX_VALUE) {
			level = Integer.MAX_VALUE;
		} else {
			level = (int) protoLevel;
		}
		entity.setCustomName(entity.getName() + " Lvl " + level);
		if (entity instanceof LivingEntity) {
			final LivingEntity livingEntity = (LivingEntity) entity;
			final AttributeModifier modifier = new AttributeModifier("dynamicMonsterModifier", Math.sqrt(level),
																	 AttributeModifier.Operation.MULTIPLY_SCALAR_1);
			final AttributeInstance maxHealth = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
			final AttributeInstance attackDamage = livingEntity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
			final AttributeInstance movementSpeed = livingEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
			if (maxHealth != null) {
				maxHealth.setBaseValue(19 + level);
				livingEntity.setHealth(maxHealth.getValue());
			}
			if (attackDamage != null) {
				attackDamage.addModifier(modifier);
			}
			if (movementSpeed != null) {
				movementSpeed.addModifier(modifier);
			}
		}
	}

	public boolean dynamicMonsterCommand (final CommandSender sender, final Command command,
										  final String label, final String[] args) {
		return true;
	}

}
