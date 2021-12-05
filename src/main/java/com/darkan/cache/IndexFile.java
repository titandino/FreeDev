// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright © 2021 Trenton Kress
//  This file is part of project: FreeDev
//
package com.darkan.cache;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.darkan.BasePlugin;
import com.darkan.api.util.Logger;

class IndexFile implements Closeable, AutoCloseable {
	public ReferenceTable table;
	private Connection connection;

	public IndexFile(Path path) {
		try {
			Driver driver = (Driver) Class.forName("org.sqlite.JDBC", true, BasePlugin.class.getClassLoader()).getDeclaredConstructor().newInstance();
			DriverManager.registerDriver(driver);
			this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
			connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cache`(`KEY` INTEGER PRIMARY KEY, `DATA` BLOB, `VERSION` INTEGER, `CRC` INTEGER);").executeUpdate();
			connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cache_index`(`KEY` INTEGER PRIMARY KEY, `DATA` BLOB, `VERSION` INTEGER, `CRC` INTEGER);").executeUpdate();
		} catch (SQLException | SecurityException | IllegalAccessException | IllegalArgumentException | InstantiationException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException e) {
			Logger.logError("Error initializing index");
			Logger.handle(e);
		}
	}

	public boolean hasReferenceTable() {
		try {
			return connection.prepareStatement("SELECT DATA FROM cache_index WHERE KEY = 1").executeQuery().next();
		} catch (SQLException e) {
			Logger.handle(e);
		}
		return false;
	}

	@Override
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			Logger.handle(e);
		}
	}

	public int getMaxArchive() {
		try {
			ResultSet result = connection.prepareStatement("SELECT MAX(`KEY`) FROM cache").executeQuery();
			if (!result.next())
				return 0;
			return result.getInt(1);
		} catch (SQLException e) {
			Logger.handle(e);
		}
		return -1;
	}

	public boolean exists(int id) {
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT 1 FROM cache WHERE KEY = ?");
			stmt.setInt(1, id);
			return stmt.executeQuery().next();
		} catch (SQLException e) {
			Logger.handle(e);
		}
		return false;
	}

	public byte[] getRaw(int id) {
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT DATA, CRC, VERSION FROM cache WHERE KEY = ?");
			stmt.setInt(1, id);
			ResultSet result = stmt.executeQuery();
			if (!result.next())
				return null;
			int crc = result.getInt("CRC");
			int version = result.getInt("VERSION");
			if (crc == 0 || version == 0)
				throw new FileNotFoundException("Archive does not exist.");
			return result.getBytes("DATA");
		} catch (SQLException | FileNotFoundException e) {
			Logger.handle(e);
		}
		return null;
	}

	public byte[] getRawTable() {
		try {
			ResultSet result = connection.prepareStatement("SELECT DATA FROM cache_index").executeQuery();
			if (!result.next())
				return null;
			return result.getBytes("DATA");
		} catch (SQLException e) {
			Logger.handle(e);
		}
		return null;
	}
}
