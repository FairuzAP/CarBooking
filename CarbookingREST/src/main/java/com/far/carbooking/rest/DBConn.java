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
    
    public static void addCar(String merk, String jenis, int km, int biaya_per_hari, 
	    String deskripsi, int tahun_produksi, int id_kota) throws SQLException {
	
	String sql = "INSERT INTO mobil(merk, jenis, km, biaya_per_hari, deskripsi, tahun_produksi, id_kota, `status`)\n"
		+ "VALUES (?, ?, ?, ?, ?, ?, ?, 0);";
	
	if(!conn.isValid(10)) {
	    conn = getConnection();
	}
	try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    stmt.setString(1, merk);
	    stmt.setString(2, jenis);
	    stmt.setInt(3, km);
	    stmt.setInt(4, biaya_per_hari);
	    stmt.setString(5, deskripsi);
	    stmt.setInt(6, tahun_produksi);
	    stmt.setInt(7, id_kota);
	    stmt.executeUpdate();
	}
    }
    public static void updateCar(int carID, String merk, String jenis, int km, int biaya_per_hari, 
	    String deskripsi, int tahun_produksi, int id_kota) throws SQLException {
	
	String sql = "UPDATE mobil SET merk=?, jenis=?, km=?, biaya_per_hari=?, deskripsi=?, tahun_produksi=?, id_kota=?\n"
		+ "WHERE id_mobil=?";
	
	if(!conn.isValid(10)) {
	    conn = getConnection();
	}
	try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    stmt.setString(1, merk);
	    stmt.setString(2, jenis);
	    stmt.setInt(3, km);
	    stmt.setInt(4, biaya_per_hari);
	    stmt.setString(5, deskripsi);
	    stmt.setInt(6, tahun_produksi);
	    stmt.setInt(7, id_kota);
	    stmt.setInt(8, carID);
	    int numrows = stmt.executeUpdate();
	    if(numrows<1) {
		throw new SQLException("No row updated");
	    }
	}
    }
    public static void updateCarStatus(int carID, int status) throws SQLException {
	String sql = "UPDATE mobil SET `status`=?\n"
		+ "WHERE id_mobil=?";
	
	if(!conn.isValid(10)) {
	    conn = getConnection();
	}
	try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    stmt.setInt(1, status);
	    stmt.setInt(2, carID);
	    int numrows = stmt.executeUpdate();
	    if(numrows<1) {
		throw new SQLException("No row updated");
	    }
	}
    }
    
    public static String addTransaction(int status, Timestamp waktu_mulai, int biaya_total, 
	    int id_mobil, int id_kota, String hp_peminjam, String email_peminjam, 
	    String nama_peminjam, Timestamp waktu_selesai) throws SQLException {
	
	String sql = "INSERT INTO transaksi(`status`, waktu_mulai, biaya_total, id_mobil, id_kota, hp_peminjam, email_peminjam, nama_peminjam, waktu_selesai)\n"
	+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
	if(!conn.isValid(10)) {
	    conn = getConnection();
	}
        int id;
	try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	    stmt.setInt(1, status);
	    stmt.setTimestamp(2, waktu_mulai);
	    stmt.setInt(3, biaya_total);
	    stmt.setInt(4, id_mobil);
	    stmt.setInt(5, id_kota);
	    stmt.setString(6, hp_peminjam);
	    stmt.setString(7, email_peminjam);
	    stmt.setString(8, nama_peminjam);
	    stmt.setTimestamp(9, waktu_selesai);
	    stmt.executeUpdate();
	}
        
        String sql2 = "SELECT last_insert_id()";
        JSONArray res;
	try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql2)) {
	    res = parseResult(rs);
	}
	return "{\"trans_id\":" + ((JSONObject)res.get(0)).get("last_insert_id()") + "}";
    }
    public static void updateTransaction(int transID, int status, Timestamp waktu_mulai, int biaya_total, 
	    int id_mobil, int id_kota, String hp_peminjam, String email_peminjam, 
	    String nama_peminjam, Timestamp waktu_selesai) throws SQLException {
	
	String sql = "UPDATE transaksi SET `status`=?, waktu_mulai=?, biaya_total=?, id_mobil=?, "
		+ "id_kota=?, hp_peminjam=?, email_peminjam=?, nama_peminjam=?, waktu_selesai=?\n"
		+ "WHERE id_transaksi=?";
	
	if(!conn.isValid(10)) {
	    conn = getConnection();
	}
	try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    stmt.setInt(1, status);
	    stmt.setTimestamp(2, waktu_mulai);
	    stmt.setInt(3, biaya_total);
	    stmt.setInt(4, id_mobil);
	    stmt.setInt(5, id_kota);
	    stmt.setString(6, hp_peminjam);
	    stmt.setString(7, email_peminjam);
	    stmt.setString(8, nama_peminjam);
	    stmt.setTimestamp(9, waktu_selesai);
	    stmt.setInt(10, transID);
	    int numrows = stmt.executeUpdate();
	    if(numrows<1) {
		throw new SQLException("No row updated");
	    }
	}
    }

    public static void updateTransactionStatus(int transID, int status) throws SQLException {
	
	String sql = "UPDATE transaksi SET `status`=?, \n"
		+ "WHERE id_transaksi=?";
	
	if(!conn.isValid(10)) {
	    conn = getConnection();
	}
	try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    stmt.setInt(1, status);
	    stmt.setInt(2, transID);
	    int numrows = stmt.executeUpdate();
	    if(numrows<1) {
		throw new SQLException("No row updated");
	    }
	}
    }
    
    public static void extendTransaction(int transID, int biaya_total, Timestamp waktu_selesai) throws SQLException {
	
	String sql = "UPDATE transaksi SET `biaya_total`=?, `waktu_selesai`=?\n"
		+ "WHERE id_transaksi=?";
	
	if(!conn.isValid(10)) {
	    conn = getConnection();
	}
	try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    stmt.setInt(1, biaya_total);
            stmt.setTimestamp(2, waktu_selesai);
	    stmt.setInt(3, transID);
	    int numrows = stmt.executeUpdate();
	    if(numrows<1) {
		throw new SQLException("No row updated");
	    }
	}
    }
}
