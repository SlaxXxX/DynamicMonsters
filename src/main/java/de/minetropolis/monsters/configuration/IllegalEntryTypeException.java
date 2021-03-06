/*
 * Copyright (C) 2017 Minetropolis
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
package de.minetropolis.monsters.configuration;

public class IllegalEntryTypeException extends InvalidConfigurationException {

	private static final long serialVersionUID = 4425257604344903391L;

	public IllegalEntryTypeException () {
	}

	public IllegalEntryTypeException (String message) {
		super(message);
	}

	public IllegalEntryTypeException (Throwable cause) {
		super(cause);
	}

	public IllegalEntryTypeException (String message, Throwable cause) {
		super(message, cause);
	}
}
