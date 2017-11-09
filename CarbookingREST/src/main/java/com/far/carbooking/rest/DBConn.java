/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.far.carbooking.rest;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 *
 * @author USER
 */
public class DBConn {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/car_rental";
    static final String USER = "root";
    static final String PASS = "";

    private static Connection conn;
    static {
        try {
            conn = getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DBConn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Connection getConnection() throws SQLException {
	Connection conn2;
	try {
	    Class.forName(JDBC_DRIVER);
	} catch (ClassNotFoundException ex) {
	    Logger.getLogger(DBConn.class.getName()).log(Level.SEVERE, null, ex);
	    System.exit(1);
	}
	conn2 = DriverManager.getConnection(DB_URL,USER,PASS);
	return conn2;
    }
    
    private static JSONArray parseResult(ResultSet rs) throws SQLException {
	ResultSetMetaData metaData = rs.getMetaData();
	
	JSONArray res = new JSONArray();
	
	while(rs.next()) {
	    JSONObject obj = new JSONObject();
	    for(int i=1; i<=metaData.getColumnCount(); i++) {	
		obj.put(metaData.getColumnLabel(i), rs.getString(i));
	    }
	    res.put(obj);
	}
	
	return res;
    }
    
    public static String getCitiesJSON() throws SQLException {
	String sql = "SELECT kota.id_kota, kota.kota FROM kota";
	
	if(!conn.isValid(10)) {
	    conn = getConnection();
	}
	JSONArray res;
	try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
	    res = parseResult(rs);
	}
	return res.toString();
    }
    
    public static String getCarsJSON(int cityID) throws SQLException {
	String sql = "SELECT mobil.id_mobil, mobil.merk, mobil.jenis\n"
		+ "FROM kota INNER JOIN mobil ON mobil.id_kota = kota.id_kota\n"
		+ "WHERE kota.id_kota = ?";
	
	if(!conn.isValid(10)) {
	    conn = getConnection();
	}
	JSONArray res;
	try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    stmt.setInt(1, cityID);
	    try(ResultSet rs = stmt.executeQuery()) {
		res = parseResult(rs);
	    }
	}
	return res.toString();
    }
    public static String getCarJSON(int carID) throws SQLException {
	String sql = "SELECT mobil.id_mobil, mobil.merk, mobil.jenis, mobil.km, "
		+ "mobil.biaya_per_hari, mobil.deskripsi, mobil.tahun_produksi, mobil.id_kota\n"
		+ "FROM mobil\n"
		+ "WHERE mobil.id_mobil = ?;";
	
	if(!conn.isValid(10)) {
	    conn = getConnection();
	}
	JSONArray res;
	try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    stmt.setInt(1, carID);
	    try(ResultSet rs = stmt.executeQuery()) {
		res = parseResult(rs);
	    }
	}
	return res.getJSONObject(0).toString();
    }
    public static String getCarStatusJSON(int carID) throws SQLException {
	String sql = "SELECT mobil.id_mobil, mobil.`status`\n"
		+ "FROM mobil\n"
		+ "WHERE mobil.id_mobil = ?;";
	
	if(!conn.isValid(10)) {
	    conn = getConnection();
	}
	JSONArray res;
	try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    stmt.setInt(1, carID);
	    try(ResultSet rs = stmt.executeQuery()) {
		res = parseResult(rs);
	    }
	}
	return res.getJSONObject(0).toString();
    }
    
    public static String getTransactionsJSON(Timestamp from, Timestamp to) throws SQLException {
	String sql = "SELECT transaksi.id_transaksi, transaksi.`status`, transaksi.waktu_mulai\n"
		+ "FROM transaksi\n"
		+ "WHERE transaksi.waktu_mulai BETWEEN ? AND ?";
	
	if(!conn.isValid(10)) {
	    conn = getConnection();
	}
	JSONArray res;
	try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    stmt.setTimestamp(1, from);
	    stmt.setTimestamp(2, to);
	    try(ResultSet rs = stmt.executeQuery()) {
		res = parseResult(rs);
	    }
	}
	return res.toString();
    }
    public static String getTransaction(int transID) throws SQLException {
	String sql = "SELECT transaksi.id_transaksi, transaksi.`status`, transaksi.waktu_mulai, "
		+ "transaksi.biaya_total, transaksi.id_mobil, transaksi.id_kota, transaksi.hp_peminjam, "
		+ "transaksi.email_peminjam, transaksi.nama_peminjam, transaksi.waktu_selesai\n"
		+ "FROM transaksi\n"
		+ "WHERE transaksi.id_transaksi = ?";
	
	if(!conn.isValid(10)) {
	    conn = getConnection();
	}
	JSONArray res;
	try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    stmt.setInt(1, transID);
	    try(ResultSet rs = stmt.executeQuery()) {
		res = parseResult(rs);
	    }
	}
	return res.getJSONObject(0).toString();
    }
    
}
