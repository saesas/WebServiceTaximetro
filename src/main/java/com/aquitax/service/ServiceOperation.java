/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aquitax.service;

import com.aquitax.dao.SaeHistorialvehiculoJpaController;
import com.aquitax.domain.SaeHistorialvehiculo;
import com.aquitax.domain.SaeVehiculo;
import java.util.List;

/**
 *
 * @author Hector Ivan
 */
public class ServiceOperation{
    
        
     public List<SaeHistorialvehiculo>  obtenerVehiculosCercanosParaTaxi( String latitud, String longitud ) {  
        
         SaeHistorialvehiculoJpaController dao = new SaeHistorialvehiculoJpaController() ;
           
         return dao.obtenerVehiculosCercanosParaTaxi(latitud, longitud);
         
     }
    
}
