package sw2.clase11ej2servidorrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sw2.clase11ej2servidorrest.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
