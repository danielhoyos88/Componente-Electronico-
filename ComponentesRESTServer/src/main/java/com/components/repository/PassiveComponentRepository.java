package com.components.repository;

import com.components.model.PassiveComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassiveComponentRepository extends JpaRepository<PassiveComponent, Integer> {

    Optional<PassiveComponent> findByBrandIgnoreCase(String brand);

    List<PassiveComponent> findByBrandIgnoreCaseAndPackageTypeIgnoreCase(String brand, String packageType);

    List<PassiveComponent> findByPackageTypeIgnoreCase(String packageType);

    // Consulta personalizada: pasivos de un fabricante específico
    @Query("SELECT p FROM PassiveComponent p JOIN FETCH p.fabricante f WHERE f.id = :fabricanteId")
    List<PassiveComponent> findByFabricanteId(@Param("fabricanteId") int fabricanteId);
}
