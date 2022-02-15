package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uz.pdp.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

	boolean existsByNameOrPhonNumber(String name, String phonNumber);
}
