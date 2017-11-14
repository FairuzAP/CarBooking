/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.far.carbooking.rest;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author USER
 */
@Path("api/rest")
@Produces(MediaType.APPLICATION_JSON)
public class Resource {
    
    static final String ERROR_RESPONSE = "{\"success\": false}";
    static final String OK_RESPONSE = "{\"success\": true}";
    
    @GET @Path("cities")
    public String getCities() {
	try {
	    return DBConn.getCitiesJSON();
	} catch (SQLException ex) {
	    Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
	}
	return ERROR_RESPONSE;
    }
    @GET @Path("cities/{city_id}")
    public String getCars(@PathParam("city_id") String cityID) {
	try {
	    return DBConn.getCarsJSON(Integer.parseInt(cityID));
	} catch (NumberFormatException | SQLException ex) {
	    Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
	}
	return ERROR_RESPONSE;
    }
    @GET @Path("car/{car_id}")
    public String getCar(@PathParam("car_id") String carID) {
	try {
	    return DBConn.getCarJSON(Integer.parseInt(carID));
	} catch (NumberFormatException | SQLException ex) {
	    Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
	}
	return ERROR_RESPONSE;
    }
    @GET @Path("car/{car_id}/status")
    public String getCarStatus(@PathParam("car_id") String carID) {
	try {
	    return DBConn.getCarStatusJSON(Integer.parseInt(carID));
	} catch (NumberFormatException | SQLException ex) {
	    Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
	}
	return ERROR_RESPONSE;
    }
    @GET @Path("trans")
    public String listTransaction(
	    @DefaultValue("0") @QueryParam("from") long from,
	    @DefaultValue(""+Long.MAX_VALUE) @QueryParam("to") long to) {
	try {
	    return DBConn.getTransactionsJSON(new Timestamp(from), new Timestamp(to));
	} catch (NumberFormatException | SQLException ex) {
	    Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
	}
	return ERROR_RESPONSE;
    }
    @GET @Path("trans/{trans_id}")
    public String getTransaction(@PathParam("trans_id") String transID) {
	try {
	    return DBConn.getTransaction(Integer.parseInt(transID));
	} catch (NumberFormatException | SQLException ex) {
	    Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
	}
	return ERROR_RESPONSE;
    }
    
    @POST @Path("car")
    @Consumes("application/x-www-form-urlencoded")
    public String addCar(MultivaluedMap<String, String> formParams) {
	try {
	    DBConn.addCar(
		    formParams.getFirst("merk"), 
		    formParams.getFirst("jenis"), 
		    Integer.parseInt(formParams.getFirst("km")), 
		    Integer.parseInt(formParams.getFirst("biaya_per_hari")), 
		    formParams.getFirst("deskripsi"),
		    Integer.parseInt(formParams.getFirst("tahun_produksi")), 
		    Integer.parseInt(formParams.getFirst("id_kota")));
	    return OK_RESPONSE;
	} catch (NumberFormatException | SQLException ex) {
	    Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
	}
	return ERROR_RESPONSE;
    }
    @POST @Path("car/{car_id}")
    @Consumes("application/x-www-form-urlencoded")
    public String updateCar(@PathParam("car_id") String carID, MultivaluedMap<String, String> formParams) {
	try {
	    DBConn.updateCar(
		    Integer.parseInt(carID),
		    formParams.getFirst("merk"), 
		    formParams.getFirst("jenis"), 
		    Integer.parseInt(formParams.getFirst("km")), 
		    Integer.parseInt(formParams.getFirst("biaya_per_hari")), 
		    formParams.getFirst("deskripsi"),
		    Integer.parseInt(formParams.getFirst("tahun_produksi")), 
		    Integer.parseInt(formParams.getFirst("id_kota")));
	    return OK_RESPONSE;
	} catch (NumberFormatException | SQLException ex) {
	    Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
	}
	return ERROR_RESPONSE;
    }
    @POST @Path("car/{car_id}/status")
    @Consumes("application/x-www-form-urlencoded")
    public String updateCarStatus(@PathParam("car_id") String carID, MultivaluedMap<String, String> formParams) {
	try {
	    DBConn.updateCarStatus(
		    Integer.parseInt(carID),
		    Integer.parseInt(formParams.getFirst("status")));
	    return OK_RESPONSE;
	} catch (NumberFormatException | SQLException ex) {
	    Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
	}
	return ERROR_RESPONSE;
    }
    @POST @Path("trans")
    @Consumes("application/x-www-form-urlencoded")
    public String addTrans(MultivaluedMap<String, String> formParams) {
	try {
	    DBConn.addTransaction(
		    Integer.parseInt(formParams.getFirst("status")), 
		    new Timestamp(Long.parseLong(formParams.getFirst("waktu_mulai"))), 
		    Integer.parseInt(formParams.getFirst("biaya_total")), 
		    Integer.parseInt(formParams.getFirst("id_mobil")), 
		    Integer.parseInt(formParams.getFirst("id_kota")), 
		    formParams.getFirst("hp_peminjam"), 
		    formParams.getFirst("email_peminjam"), 
		    formParams.getFirst("nama_peminjam"), 
		    new Timestamp(Long.parseLong(formParams.getFirst("waktu_selesai"))));
	    return OK_RESPONSE;
	} catch (NumberFormatException | SQLException ex) {
	    Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
	}
	return ERROR_RESPONSE;
    }
    @POST @Path("trans/{trans_id}")
    @Consumes("application/x-www-form-urlencoded")
    public String editTrans(@PathParam("trans_id") String trans_id, MultivaluedMap<String, String> formParams) {
	try {
	    DBConn.updateTransaction(
		    Integer.parseInt(trans_id), 
		    Integer.parseInt(formParams.getFirst("status")), 
		    new Timestamp(Long.parseLong(formParams.getFirst("waktu_mulai"))), 
		    Integer.parseInt(formParams.getFirst("biaya_total")), 
		    Integer.parseInt(formParams.getFirst("id_mobil")), 
		    Integer.parseInt(formParams.getFirst("id_kota")), 
		    formParams.getFirst("hp_peminjam"), 
		    formParams.getFirst("email_peminjam"), 
		    formParams.getFirst("nama_peminjam"), 
		    new Timestamp(Long.parseLong(formParams.getFirst("waktu_selesai"))));
	    return OK_RESPONSE;
	} catch (NumberFormatException | SQLException ex) {
	    Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
	}
	return ERROR_RESPONSE;
    }
    @POST @Path("trans/{trans_id}/status")
    @Consumes("application/x-www-form-urlencoded")
    public String editTransStatus(@PathParam("trans_id") String trans_id, MultivaluedMap<String, String> formParams) {
	try {
	    DBConn.updateTransactionStatus(
		    Integer.parseInt(trans_id), 
		    Integer.parseInt(formParams.getFirst("status")));
	    return OK_RESPONSE;
	} catch (NumberFormatException | SQLException ex) {
	    Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
	}
	return ERROR_RESPONSE;
    }
	@POST @Path("trans/{trans_id}/date")
    @Consumes("application/x-www-form-urlencoded")
    public String editTransDate(@PathParam("trans_id") String trans_id, MultivaluedMap<String, String> formParams) {
	try {
	    DBConn.updateTransactionStatus(
		    Integer.parseInt(trans_id), 
		    new Timestamp(Long.parseLong(formParams.getFirst("waktu_selesai"))));
	    return OK_RESPONSE;
	} catch (NumberFormatException | SQLException ex) {
	    Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
	}
	return ERROR_RESPONSE;
    }
}