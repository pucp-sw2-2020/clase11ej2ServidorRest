package sw2.clase11ej2servidorrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sw2.clase11ej2servidorrest.entity.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Integer> {
}
