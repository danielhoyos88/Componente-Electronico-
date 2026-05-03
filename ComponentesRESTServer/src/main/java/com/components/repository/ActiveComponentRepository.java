package com.components.repository;

import com.components.model.ActiveComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActiveComponentRepository extends JpaRepository<ActiveComponent, Integer> {

    Optional<ActiveComponent> findByBrandIgnoreCase(String brand);

    List<ActiveComponent> findByBrandIgnoreCaseAndPackageTypeIgnoreCase(String brand, String packageType);

    List<ActiveComponent> findByBrandIgnoreCase(String brand, org.springframework.data.domain.Sort sort);

    List<ActiveComponent> findByPackageTypeIgnoreCase(String packageType);

    // Consulta personalizada 2: obtener fabricante con sus componentes activos y pasivos
    // Trae activos de un fabricante específico junto con los datos del fabricante
    @Query("SELECT a FROM ActiveComponent a JOIN FETCH a.fabricante f WHERE f.id = :fabricanteId")
    List<ActiveComponent> findByFabricanteId(@Param("fabricanteId") int fabricanteId);
}
