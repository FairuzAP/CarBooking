/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.far.carbooking.rest;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import com.sun.jersey.spi.container.servlet.ServletContainer;

/**
 *
 * @author USER
 */
public class App {
    public static void main(String args[]){
	
	ServletHolder servlet = new ServletHolder(new ServletContainer());
	servlet.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
	servlet.setInitParameter("com.sun.jersey.config.property.packages", "com.far.carbooking.rest");
	
	Server server = new Server(2222);
	ServletContextHandler context = new ServletContextHandler(server, "/*");
	context.addServlet(servlet, "/*");

	try {
	    server.start();
	    server.join();
	} catch (Exception ex) {
	    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    server.destroy();
	}
	
    }
}
