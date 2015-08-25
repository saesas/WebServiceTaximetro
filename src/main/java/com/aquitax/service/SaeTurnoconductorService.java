/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.service;

import com.aquitax.dao.SaeTurnoconductorJpaController;
import com.aquitax.domain.SaeTurnoconductor;
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
@Path("/saeturnoconductor")
public class SaeTurnoconductorService {
  SaeTurnoconductorJpaController dao;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<SaeTurnoconductor> findAll() {
        dao = new SaeTurnoconductorJpaController();
        List<SaeTurnoconductor> listAllSaeTurnoconductor = dao.findSaeTurnoconductorEntities();
        return listAllSaeTurnoconductor;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public SaeTurnoconductor findById(@PathParam("id") int id) {
        dao = new SaeTurnoconductorJpaController();
       SaeTurnoconductor saeTurnoconductor= dao.findSaeTurnoconductor(id);
        return saeTurnoconductor;
    }
    
     @POST
    @Consumes({"application/xml", "application/json"})
    public Response create(SaeTurnoconductor entity) {
        try {
            dao = new SaeTurnoconductorJpaController();
            dao.create(entity);
            return Response.created(URI.create(entity.getTurIntId().toString())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    
    
    @PUT
    @Consumes({"application/xml", "application/json"})
    public Response edit(SaeTurnoconductor saeTurnoConductor) {
        try {
            dao = new SaeTurnoconductorJpaController();
            dao.edit(saeTurnoConductor);
            return Response.created(URI.create(saeTurnoConductor.getTurIntId().toString())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") int id) {
        try {
            dao = new SaeTurnoconductorJpaController();
            dao.destroy(id);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
}
