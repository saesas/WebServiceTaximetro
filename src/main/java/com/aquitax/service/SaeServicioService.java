/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.service;

import com.aquitax.dao.SaeServicioJpaController;
import com.aquitax.domain.SaeServicio;
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
@Path("/saeservicio")
public class SaeServicioService {
    SaeServicioJpaController dao;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<SaeServicio> findAll() {
        dao = new SaeServicioJpaController();
        List<SaeServicio> listAllSaeServicio = dao.findSaeServicioEntities();
        return listAllSaeServicio;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public SaeServicio findById(@PathParam("id") int id) {
        dao = new SaeServicioJpaController();
        SaeServicio saeServicio= dao.findSaeServicio(id);
        return saeServicio;
    }
    
     @POST
    @Consumes({"application/xml", "application/json"})
    public Response create(SaeServicio entity) {
        try {
            dao = new SaeServicioJpaController();
            dao.create(entity);
            return Response.created(URI.create(entity.getSerIntId().toString())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    
    
    @PUT
    @Consumes({"application/xml", "application/json"})
    public Response edit(SaeServicio saeServicio) {
        try {
            dao = new SaeServicioJpaController();
            dao.edit(saeServicio);
            return Response.created(URI.create(saeServicio.getSerIntId().toString())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") int id) {
        try {
            dao = new SaeServicioJpaController();
            dao.destroy(id);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
}
