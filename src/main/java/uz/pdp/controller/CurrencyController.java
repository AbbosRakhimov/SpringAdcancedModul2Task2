package uz.pdp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import uz.pdp.entity.Currency;
import uz.pdp.payload.Result;
import uz.pdp.service.CurrencyService;

@RestController
@RequestMapping(value = "/currency")
public class CurrencyController {

	@Autowired
	CurrencyService currencyService;
	
	@PostMapping
	public HttpEntity<?> addCurrency(@Valid @RequestBody Currency currency) {
		Result result = currencyService.addCurrent(currency);
		return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(result);
	}
	@GetMapping
	public HttpEntity<?> getCurrencyc() {
		List<Currency> currencies = currencyService.getCurrencyc();
		return ResponseEntity.status(currencies.isEmpty()?HttpStatus.NOT_FOUND:HttpStatus.OK).body(currencies);
	}
	@GetMapping(value = "/{id}")
	public HttpEntity<?> getCurrency(@PathVariable Integer id) {
		Currency currency= currencyService.getCurrency(id);
		return ResponseEntity.status(currency!=null?HttpStatus.OK:HttpStatus.NOT_FOUND).body(currency);
	}
	@PutMapping(value = "/{id}")
	public HttpEntity<?> editCurreny(@PathVariable Integer id, @Valid @RequestBody Currency currency) {
		Result result = currencyService.editCurreny(id, currency);
		return ResponseEntity.status(result.isSuccess()? HttpStatus.OK : HttpStatus.CONFLICT).body(result);
	}
	@DeleteMapping(value = "/{id}")
	public HttpEntity<?> deleteCurrency(@PathVariable Integer id) {
		Result result = currencyService.deletCurrency(id);
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.NOT_FOUND).body(result);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
}
