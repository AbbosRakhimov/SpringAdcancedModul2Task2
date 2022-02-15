package uz.pdp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uz.pdp.entity.Currency;
import uz.pdp.payload.Result;
import uz.pdp.repository.CurrencyRepository;

@Service
public class CurrencyService {

	@Autowired
	CurrencyRepository currencyRepository;
	
	public Result addCurrent(Currency currency) {
		if(currencyRepository.existsByName(currency.getName()))
			return new Result("Currency exist", false);
		Currency currency2 = new Currency();
		currency2.setName(currency.getName());
		currencyRepository.save(currency2);
		return new Result("Currency added", true);
	}
	
	public List<Currency> getCurrencyc(){
		return currencyRepository.findAll();
	}
	public Currency getCurrency(Integer id) {
		Optional<Currency> rOptional = currencyRepository.findById(id);
		return rOptional.orElse(null);
//		if(rOptional.isPresent()) {
//			return new Result("Currency exist", true, rOptional.get()); 
//		}
//		return new Result("Currency not found", false);
	}
	public Result editCurreny(Integer id, Currency currency) {
		if(currencyRepository.existsByName(currency.getName()))
			return new Result("Currency exist", false);
		Optional<Currency> cOptional = currencyRepository.findById(id);
		if(!cOptional.isPresent())
			return new Result("Currency not found", false);
		Currency currency2 = cOptional.get();
		currency2.setName(currency.getName());
		currencyRepository.save(currency2);
		return new Result("Currency edited", true);
	}
	public Result deletCurrency(Integer id) {
		Optional<Currency> rOptional = currencyRepository.findById(id);
		if(rOptional.isPresent()) {
			currencyRepository.deleteById(id);
			return new Result("Currency deleted", true); 
		}
		return new Result("Currency not found", false);
	}
}
