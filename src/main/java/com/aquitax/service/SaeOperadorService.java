/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.service;

import com.aquitax.dao.SaeOperadorJpaController;
import com.aquitax.domain.SaeOperador;
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
@Path("/saeoperador")
public class SaeOperadorService {
    SaeOperadorJpaController dao;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<SaeOperador> findAll() {
        dao = new SaeOperadorJpaController();
        List<SaeOperador> listAllSaeOperador = dao.findSaeOperadorEntities();
        return listAllSaeOperador;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public SaeOperador findById(@PathParam("id") int id) {
        dao = new SaeOperadorJpaController();
        SaeOperador saeOperador= dao.findSaeOperador(id);
        return saeOperador;
    }
    
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response create(SaeOperador entity) {
        try {
            dao = new SaeOperadorJpaController();
            dao.create(entity);
            return Response.created(URI.create(entity.getOpeIntId().toString())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    
    
    @PUT
    @Consumes({"application/xml", "application/json"})
    public Response edit(SaeOperador saeOperador) {
        try {
            dao = new SaeOperadorJpaController();
             if (saeOperador.getSaeServicioList() == null) {
                saeOperador.setSaeServicioList(new ArrayList<SaeServicio>());
            }
            dao.edit(saeOperador);
            return Response.created(URI.create(saeOperador.getOpeIntId().toString())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") int id) {
        try {
            dao = new SaeOperadorJpaController();
            dao.destroy(id);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
}
