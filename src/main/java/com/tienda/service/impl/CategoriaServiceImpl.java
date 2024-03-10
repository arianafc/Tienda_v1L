package com.tienda.service.impl;

import com.tienda.dao.CategoriaDao;
import com.tienda.domain.Categoria;
import com.tienda.service.CategoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service //es como una descripcion, un TAG
public class CategoriaServiceImpl implements CategoriaService { //IMPLEMENTA LA LISTA QUE SE CREO EN EL SERVICE
    //AQUI SE HACEN LAS ACCIONES SOBRE LA TABLA, ES DECIR PUEDO FILTAR ESA LISTA

    @Autowired
    private CategoriaDao categoriaDao; //

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> getCategorias(boolean activos) {
        var lista = categoriaDao.findAll(); //BUSCAR TODA LA INFORMACION QUE YO TENGA EN CATEGORIA DAO (MI LISTA QUE NO TIENE NINGUN FILTRO)
        if (activos) {
            lista.removeIf(e -> !e.isActivo()); //REMUEVE LOS DATOS QUE NO TENGA ACTIVOS
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true) //solamente se lee
    public Categoria getCategoria(Categoria categoria) {
        return categoriaDao.findById(categoria.getIdCategoria()).orElse(null);
    }

    @Override
    @Transactional //es porque va a hacer un request a la tabla
    public void save(Categoria categoria) {
        categoriaDao.save(categoria);
    }

    @Override
    @Transactional
    public void delete(Categoria categoria) {
        categoriaDao.deleteById(categoria.getIdCategoria());
    }

}
