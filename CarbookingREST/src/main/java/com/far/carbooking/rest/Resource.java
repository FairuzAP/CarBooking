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
    
    static final String ERROR_RESPONSE = "";
    
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
    public void addCar(MultivaluedMap<String, String> formParams) {
	
    }
    @POST @Path("car/{car_id}")
    @Consumes("application/x-www-form-urlencoded")
    public void updateCar(@PathParam("car_id") String carID, MultivaluedMap<String, String> formParams) {
	
    }
    @POST @Path("car/{car_id}/status")
    @Consumes("application/x-www-form-urlencoded")
    public String updateCarStatus(@PathParam("car_id") String carID) {
	return "Hello, world!";
    }
    @POST @Path("trans")
    @Consumes("application/x-www-form-urlencoded")
    public void addTrans(MultivaluedMap<String, String> formParams) {
	
    }
    @POST @Path("trans/{trans_id}")
    @Consumes("application/x-www-form-urlencoded")
    public void editTrans(@PathParam("trans_id") String trans_id, MultivaluedMap<String, String> formParams) {
	
    }
}