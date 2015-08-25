/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquitax.service;

/**
 *
 * @author SAE2
 */
import com.aquitax.dao.SaeUsuariovehiculoJpaController;
import com.aquitax.domain.SaeServicio;
import com.aquitax.domain.SaeUsuariovehiculo;
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


@Path("/saeusuariovehiculo")
public class SaeUsuariovehiculoService {
    
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    SaeUsuariovehiculoJpaController dao;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<SaeUsuariovehiculo> findAll() {
        dao = new SaeUsuariovehiculoJpaController();
        List<SaeUsuariovehiculo> listAllSaeUsuariovehiculo = dao.findSaeUsuariovehiculoEntities();
        return listAllSaeUsuariovehiculo;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public SaeUsuariovehiculo findById(@PathParam("id") String id) {
        dao = new SaeUsuariovehiculoJpaController();
        SaeUsuariovehiculo usuarioVehiculo= dao.findSaeUsuariovehiculo(BigDecimal.valueOf(Double.parseDouble(id)));
        return usuarioVehiculo;
    }
    
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response create(SaeUsuariovehiculo entity) {
        try {
            dao = new SaeUsuariovehiculoJpaController();
            dao.create(entity);
            return Response.created(URI.create(entity.getUsuStrNombre())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    
    
    @PUT
    @Consumes({"application/xml", "application/json"})
    public Response edit(SaeUsuariovehiculo saeUsuarioVehiculo) {
        try {
            dao = new SaeUsuariovehiculoJpaController();
            if (saeUsuarioVehiculo.getSaeServicioList() == null) {
                saeUsuarioVehiculo.setSaeServicioList(new ArrayList<SaeServicio>());
            }
            dao.edit(saeUsuarioVehiculo);
            return Response.created(URI.create(saeUsuarioVehiculo.getUsuStrNombre())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    
    
    /*
    @PUT
    @Consumes({"application/xml", "application/json"})
    public Response edit(SaeUsuariovehiculo saeUsuariovehiculo) {
        try {
            dao = new SaeUsuariovehiculoJpaController();
            dao.edit(saeUsuariovehiculo);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    */

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") String id) {
        try {
            dao = new SaeUsuariovehiculoJpaController();
            dao.destroy(BigDecimal.valueOf(Double.parseDouble(id)));
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
}
