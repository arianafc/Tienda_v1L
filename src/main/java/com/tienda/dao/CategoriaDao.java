package com.tienda.dao;


import com.tienda.domain.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
 
public interface CategoriaDao extends JpaRepository <categoria,Long> { //long = id
    //crea una capa de datos que extiende a la clase creada, es decir, jala la info de los atributos

}