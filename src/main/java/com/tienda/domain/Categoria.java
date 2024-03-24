package com.tienda.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data //es una clase donde se recolectan datos
@Entity //entidad=tabla
@Table(name = "categoria")

public class Categoria implements Serializable { //serializacion porque se va almacenar ciertos datos en el disco

    private static final long serialVersionUID = 1L; //para poder hacer el ciclo de la sumatoria de la categoria.

    @Id //id categoria es la llave de la tabla categoria. Aqui va el primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Los valores generados que estrategia usan, identico a la BD 
    @Column(name = "id_categoria") //decir cual es el nombre en la base de datos. Se hace la asociación 
    private long idCategoria;
    private String descripcion;
    private String rutaImagen;
    private boolean activo;

    @OneToMany //uno a muchos
    @JoinColumn(name="id_categoria", updatable=false) //no ocupo que actualice la tabla, solo join (traerme la info), aqui se usa el fk para unir
    List<Producto> productos;
    
    public Categoria() {
    }

    public Categoria(String descripcion, boolean activo) {
        this.descripcion = descripcion;
        this.activo = activo;
    }

}