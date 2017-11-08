/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.far.carbooking.rest;

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
    @GET @Path("cities")
    public String getCities() {
	return "Cities List!";
    }
    @GET @Path("cities/{city_id}")
    public String getCars(@PathParam("city_id") String cityID) {
	return "Cars List for City #" + cityID;
    }
    @GET @Path("car/{car_id}")
    public String getCar(@PathParam("car_id") String carID) {
	return "Car #" + carID;
    }
    @GET @Path("car/{car_id}/status")
    public String getCarStatus(@PathParam("car_id") String carID) {
	return "Car Status #" + carID;
    }
    @GET @Path("trans")
    public String listTransaction(
	    @DefaultValue("0") @QueryParam("from") long from,
	    @DefaultValue(""+Long.MAX_VALUE) @QueryParam("to") long to) {
	return "Transaction from " + from + " to " + to;
    }
    @GET @Path("trans/{trans_id}")
    public String getTransaction(@PathParam("trans_id") String transID) {
	return "Transaction #" + transID;
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