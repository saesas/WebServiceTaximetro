/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.service;

import com.aquitax.dao.SaeUsuarioJpaController;
import com.aquitax.domain.SaeUsuario;
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
@Path("/saeusuario")
public class SaeUsuarioService {
    SaeUsuarioJpaController dao;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<SaeUsuario> findAll() {
        dao = new SaeUsuarioJpaController();
        List<SaeUsuario> listAllSaeUsuario = dao.findSaeUsuarioEntities();
        return listAllSaeUsuario;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public SaeUsuario findById(@PathParam("id") String id) {
        dao = new SaeUsuarioJpaController();
       SaeUsuario saeTurnoconductor= dao.findSaeUsuario(id);
        return saeTurnoconductor;
    }
    
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response create(SaeUsuario entity) {
        try {
            dao = new SaeUsuarioJpaController();
            dao.create(entity);
            return Response.created(URI.create(entity.getUsuStrNombre())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    
    
    @PUT
    @Consumes({"application/xml", "application/json"})
    public Response edit(SaeUsuario saeServicio) {
        try {
            dao = new SaeUsuarioJpaController();
            dao.edit(saeServicio);
            return Response.created(URI.create(saeServicio.getUsuStrNombre())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") String id) {
        try {
            dao = new SaeUsuarioJpaController();
            dao.destroy(id);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
}
