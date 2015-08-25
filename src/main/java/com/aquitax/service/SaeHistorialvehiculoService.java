/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.service;

import com.aquitax.dao.SaeHistorialvehiculoJpaController;
import com.aquitax.domain.SaeHistorialvehiculo;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Hector Ivan
 */
@Path("/saehistorialvehiculo")
public class SaeHistorialvehiculoService {
    SaeHistorialvehiculoJpaController dao;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<SaeHistorialvehiculo> findAll() {
        dao = new SaeHistorialvehiculoJpaController();
        List<SaeHistorialvehiculo> listAllSaeHistorialvehiculo = dao.findSaeHistorialvehiculoEntities();
        return listAllSaeHistorialvehiculo;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public SaeHistorialvehiculo findById(@PathParam("id") int id) {
        dao = new SaeHistorialvehiculoJpaController();
        SaeHistorialvehiculo saeHistorialvehiculo= dao.findSaeHistorialvehiculo(id);
        return saeHistorialvehiculo;
    }
    
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response create(SaeHistorialvehiculo entity) {
        try {
            dao = new SaeHistorialvehiculoJpaController();
            dao.create(entity);
            return Response.created(URI.create(entity.getHisIntId().toString())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    
    
    @PUT
    @Consumes({"application/xml", "application/json"})
    public Response edit(SaeHistorialvehiculo saeHistorialVehiculo) {
        try {
            dao = new SaeHistorialvehiculoJpaController();
            
            dao.edit(saeHistorialVehiculo);
            return Response.created(URI.create(saeHistorialVehiculo.getHisIntId().toString())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") int id) {
        try {
            dao = new SaeHistorialvehiculoJpaController();
            dao.destroy(id);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    
    @GET
    @Path("posicion/{latitud}/{longitud}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<SaeHistorialvehiculo> obtenerVehiculosCercanosParaTaxi(@PathParam("latitud") String latitud, @PathParam("longitud") String longitud) {
        ServiceOperation serviceOperation= new ServiceOperation();
        List<SaeHistorialvehiculo> listAllSaeHistorialvehiculo = serviceOperation.obtenerVehiculosCercanosParaTaxi(latitud, longitud);
        return listAllSaeHistorialvehiculo;
    }
    
}
