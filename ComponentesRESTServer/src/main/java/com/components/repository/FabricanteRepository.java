package com.components.repository;

import com.components.model.Fabricante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FabricanteRepository extends JpaRepository<Fabricante, Integer> {

    Optional<Fabricante> findByNombreIgnoreCase(String nombre);

    // Consulta personalizada 1: buscar fabricantes por país
    @Query("SELECT f FROM Fabricante f WHERE LOWER(f.pais) = LOWER(:pais)")
    java.util.List<Fabricante> findByPais(@Param("pais") String pais);
}
