/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tienda.dao;

import com.tienda.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Ariana
 */
public interface ProductoDao extends JpaRepository <Producto,Long>  {
    //aqui se crean los filtros
    public List<Producto> findByPrecioBetweenOrderByDescripcion(double precioInf, double precioSup);//filtro entre un rango de precios
    
}
