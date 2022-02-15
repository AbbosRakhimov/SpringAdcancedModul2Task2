package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uz.pdp.entity.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

	boolean existsByName(String name);
}
