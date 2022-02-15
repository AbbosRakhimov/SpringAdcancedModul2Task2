package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uz.pdp.entity.InputProduct;

@Repository
public interface InputProductRepository extends JpaRepository<InputProduct, Integer> {

	boolean existsByProductId(Integer id);
}
