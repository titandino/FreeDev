package com.darkan.cache;

import java.io.Closeable;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class IndexFile implements Closeable, AutoCloseable {
	public ReferenceTable table;
	private Connection connection;
	private PreparedStatement archiveExistsStmt;
	private PreparedStatement getMaxArchiveStmt;
	private PreparedStatement getArchiveDataStmt;
	private PreparedStatement getReferenceDataStmt;
	private PreparedStatement putArchiveDataStmt;
	private PreparedStatement putReferenceDataStmt;

	public IndexFile(Path path) {
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);
			archiveExistsStmt = connection.prepareStatement("SELECT 1 FROM `cache` WHERE `KEY` = ?;");
			getMaxArchiveStmt = connection.prepareStatement("SELECT MAX(`KEY`) FROM `cache`;");
			getArchiveDataStmt = connection.prepareStatement("SELECT `DATA` FROM `cache` WHERE `KEY` = ?;");
			getReferenceDataStmt = connection.prepareStatement("SELECT `DATA` FROM `cache_index` WHERE `KEY` = 1;");
			connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cache`(`KEY` INTEGER PRIMARY KEY, `DATA` BLOB, `VERSION` INTEGER, `CRC` INTEGER);").executeUpdate();
			connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cache_index`(`KEY` INTEGER PRIMARY KEY, `DATA` BLOB, `VERSION` INTEGER, `CRC` INTEGER);").executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error initializing index");
			e.printStackTrace();
		}
	}

	public int putRawTable(byte[] data, int version, int crc) {
		try {
			putReferenceDataStmt.clearParameters();
			putReferenceDataStmt.setBytes(1, data);
			putReferenceDataStmt.setInt(2, version);
			putReferenceDataStmt.setInt(3, crc);
			putReferenceDataStmt.setBytes(4, data);
			putReferenceDataStmt.setInt(5, version);
			putReferenceDataStmt.setInt(6, crc);
			return putReferenceDataStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int putRaw(int archive, byte[] data, int version, int crc) {
		try {
			PreparedStatement stmt = connection.prepareStatement("""
					INSERT INTO `cache`(`KEY`, `DATA`, `VERSION`, `CRC`)
					  VALUES(?, ?, ?, ?)
					  ON CONFLICT(`KEY`) DO UPDATE SET
					    `DATA` = ?, `VERSION` = ?, `CRC` = ?
					  WHERE `KEY` = ?;
					""");
			stmt.clearParameters();
			stmt.setInt(1, archive);
			stmt.setBytes(2, data);
			stmt.setInt(3, version);
			stmt.setInt(4, crc);
			stmt.setBytes(5, data);
			stmt.setInt(6, version);
			stmt.setInt(7, crc);
			stmt.setInt(8, archive);
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public boolean hasReferenceTable() {
		try {
			return getReferenceDataStmt.executeQuery().next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void close() {
		try {
			getMaxArchiveStmt.close();
			getArchiveDataStmt.close();
			getReferenceDataStmt.close();
			putArchiveDataStmt.close();
			putReferenceDataStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getMaxArchive() {
		try {
	        ResultSet result = getMaxArchiveStmt.executeQuery();
	        if (!result.next()) 
	        	return 0;
	        return result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
    }

	public boolean exists(int id) {
		try {
			archiveExistsStmt.clearParameters();
			archiveExistsStmt.setInt(1, id);
			return archiveExistsStmt.executeQuery().next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public byte[] getRaw(int id) {
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT `DATA` FROM `cache` WHERE `KEY` = ?;");
			stmt.clearParameters();
			stmt.setInt(1, id);
			ResultSet result = stmt.executeQuery();
			if (!result.next())
				return null;
			return result.getBytes("DATA");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] getRawTable() {
		try {
			ResultSet result = getReferenceDataStmt.executeQuery();
			if (!result.next())
				return null;
			return result.getBytes("DATA");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
