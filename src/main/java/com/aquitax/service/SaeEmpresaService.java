/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.service;

import com.aquitax.dao.SaeEmpresaJpaController;
import com.aquitax.domain.SaeEmpresa;
import com.aquitax.domain.SaeOperador;
import com.aquitax.domain.SaePropietariovehiculo;
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
@Path("/saeempresa")
public class SaeEmpresaService {
    SaeEmpresaJpaController dao;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<SaeEmpresa> findAll() {
        dao = new SaeEmpresaJpaController();
        List<SaeEmpresa> listAllSaeEmpresa = dao.findSaeEmpresaEntities();
        return listAllSaeEmpresa;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public SaeEmpresa findById(@PathParam("id") String id) {
        dao = new SaeEmpresaJpaController();
        SaeEmpresa saeDatoevento= dao.findSaeEmpresa(id);
        return saeDatoevento;
    }
    
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response create(SaeEmpresa entity) {
        try {
            dao = new SaeEmpresaJpaController();
            dao.create(entity);
            return Response.created(URI.create(entity.getEmpStrNit())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    
    
    @PUT
    @Consumes({"application/xml", "application/json"})
    public Response edit(SaeEmpresa saeEmpresa) {
        try {
            dao = new SaeEmpresaJpaController();
            if (saeEmpresa.getSaeOperadorList() == null) {
            saeEmpresa.setSaeOperadorList(new ArrayList<SaeOperador>());
            }
            if (saeEmpresa.getSaePropietariovehiculoList() == null) {
                saeEmpresa.setSaePropietariovehiculoList(new ArrayList<SaePropietariovehiculo>());
            }
            
            dao.edit(saeEmpresa);
            return Response.created(URI.create(saeEmpresa.getEmpStrNit())).build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
    

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") String id) {
        try {
            dao = new SaeEmpresaJpaController();
            dao.destroy(id);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.notModified(ex.getMessage()).build();
        }
    }
}
