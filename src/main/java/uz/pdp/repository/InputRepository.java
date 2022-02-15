package uz.pdp.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uz.pdp.entity.Input;

@Repository
public interface InputRepository extends JpaRepository<Input, Integer>{

	boolean existsByFactureName(String factureName);
}
