/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.service;

import com.aquitax.dao.SaePropietarioconductorJpaController;
import com.aquitax.domain.SaePropietarioconductor;
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
@Path("/saepropietarioconductor")
public class SaePropietarioconductorService {
    SaePropietarioconductorJpaController dao;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<SaePropietarioconductor> findAll() {
        dao = new SaePropietarioconductorJpaController();
        List<SaePropietarioconductor> listAllSaePropietarioconductor = dao.findSaePropietarioconductorEntities();
        return listAllSaePropietarioconductor;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public SaePropietarioconductor findById(@PathParam("id") int id) {
        dao = new SaePropietarioconductorJpaController();
        SaePropietarioconductor saePropietarioconductor= dao.findSaePropietarioconductor(id);
        return saePropietarioconductor;
    }
    
     @POST
    @Consumes({"application/xml", "application/json"})
    public Response create(SaePropietarioconductor entity) {
        try {
            dao = new SaePropietarioconductorJpaController();
            dao.create(entity);
            return Response.created(URI.create(entity.getPrcoIntId().toString())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    
    
    @PUT
    @Consumes({"application/xml", "application/json"})
    public Response edit(SaePropietarioconductor saePropiedadConductor) {
        try {
            dao = new SaePropietarioconductorJpaController();
            
            dao.edit(saePropiedadConductor);
            return Response.created(URI.create(saePropiedadConductor.getPrcoIntId().toString())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") int id) {
        try {
            dao = new SaePropietarioconductorJpaController();
            dao.destroy(id);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
}
