/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.service;


import com.aquitax.dao.SaeConductorJpaController;
import com.aquitax.domain.SaeConductor;
import com.aquitax.domain.SaePropietarioconductor;
import com.aquitax.domain.SaeServicio;
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
@Path("/saeconductor")
public class SaeConductorService {
    SaeConductorJpaController dao;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<SaeConductor> findAll() {
        dao = new SaeConductorJpaController();
        List<SaeConductor> listAllSaeConductor = dao.findSaeConductorEntities();
        return listAllSaeConductor;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public SaeConductor findById(@PathParam("id") String id) {
        dao = new SaeConductorJpaController();
        SaeConductor saeConductor= dao.findSaeConductor(BigDecimal.valueOf(Double.parseDouble(id)));
        return saeConductor;
    }
    
    
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response create(SaeConductor entity) {
        try {
            dao = new SaeConductorJpaController();
            dao.create(entity);
            return Response.created(URI.create(entity.getConIntCedula().toString())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    
    
    @PUT
    @Consumes({"application/xml", "application/json"})
    public Response edit(SaeConductor saeConductor) {
        try {
            dao = new SaeConductorJpaController();
            if (saeConductor.getSaeServicioList() == null) {
            saeConductor.setSaeServicioList(new ArrayList<SaeServicio>());
            }
            if (saeConductor.getSaePropietarioconductorList() == null) {
                saeConductor.setSaePropietarioconductorList(new ArrayList<SaePropietarioconductor>());
            }
            if (saeConductor.getSaeTurnoconductorList() == null) {
                saeConductor.setSaeTurnoconductorList(new ArrayList<SaeTurnoconductor>());
            }
            dao.edit(saeConductor);
            return Response.created(URI.create(saeConductor.getConIntCedula().toString())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") String id) {
        try {
            dao = new SaeConductorJpaController();
            dao.destroy(BigDecimal.valueOf(Double.parseDouble(id)));
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    
}
