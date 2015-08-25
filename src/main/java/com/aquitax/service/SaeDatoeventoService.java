/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.service;

import com.aquitax.dao.SaeDatoeventoJpaController;
import com.aquitax.domain.SaeDatoevento;
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
@Path("/saedatoevento")
public class SaeDatoeventoService {
    
    SaeDatoeventoJpaController dao;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<SaeDatoevento> findAll() {
        dao = new SaeDatoeventoJpaController();
        List<SaeDatoevento> listAllSaeDatoevento = dao.findSaeDatoeventoEntities();
        return listAllSaeDatoevento;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public SaeDatoevento findById(@PathParam("id") int id) {
        dao = new SaeDatoeventoJpaController();
        SaeDatoevento saeDatoevento= dao.findSaeDatoevento(id);
        return saeDatoevento;
    }
    
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response create(SaeDatoevento entity) {
        try {
            dao = new SaeDatoeventoJpaController();
            dao.create(entity);
            return Response.created(URI.create(entity.getDatIntId().toString())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    
    
    @PUT
    @Consumes({"application/xml", "application/json"})
    public Response edit(SaeDatoevento saeDatoEvento) {
        try {
            dao = new SaeDatoeventoJpaController();
            if (saeDatoEvento.getSaeHistorialvehiculoList() == null) {
                saeDatoEvento.setSaeHistorialvehiculoList(new ArrayList<SaeHistorialvehiculo>());
            }
            
            dao.edit(saeDatoEvento);
            return Response.created(URI.create(saeDatoEvento.getDatIntId().toString())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") int id) {
        try {
            dao = new SaeDatoeventoJpaController();
            dao.destroy(id);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
}
