/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.service;

import com.aquitax.dao.SaeEventoJpaController;
import com.aquitax.domain.SaeEvento;
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
@Path("/saeevento")
public class SaeEventoService {
    SaeEventoJpaController dao;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<SaeEvento> findAll() {
        dao = new SaeEventoJpaController();
        List<SaeEvento> listAllSaeEvento = dao.findSaeEventoEntities();
        return listAllSaeEvento;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public SaeEvento findById(@PathParam("id") int id) {
        dao = new SaeEventoJpaController();
        SaeEvento saeEvento= dao.findSaeEvento(id);
        return saeEvento;
    }
    
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response create(SaeEvento entity) {
        try {
            dao = new SaeEventoJpaController();
            dao.create(entity);
            return Response.created(URI.create(entity.getEveIntId().toString())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    
    
    @PUT
    @Consumes({"application/xml", "application/json"})
    public Response edit(SaeEvento saeEvento) {
        try {
            dao = new SaeEventoJpaController();
            if (saeEvento.getSaeHistorialvehiculoList() == null) {
            saeEvento.setSaeHistorialvehiculoList(new ArrayList<SaeHistorialvehiculo>());
            }
            
            dao.edit(saeEvento);
            return Response.created(URI.create(saeEvento.getEveIntId().toString())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") int id) {
        try {
            dao = new SaeEventoJpaController();
            dao.destroy(id);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
}
