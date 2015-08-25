/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.service;


import com.aquitax.dao.SaeVehiculoJpaController;
import com.aquitax.domain.SaeHistorialvehiculo;
import com.aquitax.domain.SaeServicio;
import com.aquitax.domain.SaeTurnoconductor;
import com.aquitax.domain.SaeVehiculo;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
/**
 *
 * @author Hector Ivan
 */
@Path("/saevehiculo")
public class SaeVehiculoService {
    SaeVehiculoJpaController dao;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<SaeVehiculo> findAll() {
        dao = new SaeVehiculoJpaController();
        List<SaeVehiculo> listAllSaeVehiculo = dao.findSaeVehiculoEntities();
        return listAllSaeVehiculo;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public SaeVehiculo findById(@PathParam("id") String id) {
        dao = new SaeVehiculoJpaController();
       SaeVehiculo saeVehiculo= dao.findSaeVehiculo(id);
        return saeVehiculo;
    }
    
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response create(SaeVehiculo entity) {
        try {
            dao = new SaeVehiculoJpaController();
            dao.create(entity);
            return Response.created(URI.create(entity.getVehStrPlaca())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    
    
    @PUT
    @Consumes({"application/xml", "application/json"})
    public Response edit(SaeVehiculo saeVehiculo) {
        try {
            dao = new SaeVehiculoJpaController();
            if (saeVehiculo.getSaeServicioList() == null) {
                saeVehiculo.setSaeServicioList(new ArrayList<SaeServicio>());
            }
            if (saeVehiculo.getSaeHistorialvehiculoList() == null) {
                saeVehiculo.setSaeHistorialvehiculoList(new ArrayList<SaeHistorialvehiculo>());
            }
            if (saeVehiculo.getSaeTurnoconductorList() == null) {
                saeVehiculo.setSaeTurnoconductorList(new ArrayList<SaeTurnoconductor>());
            }
            dao.edit(saeVehiculo);
            return Response.created(URI.create(saeVehiculo.getVehStrPlaca())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") String id) {
        try {
            dao = new SaeVehiculoJpaController();
            dao.destroy(id);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
}
