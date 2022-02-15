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

import uz.pdp.entity.InputProduct;
import uz.pdp.payload.InputProductDto;
import uz.pdp.payload.Result;
import uz.pdp.service.InputProductSercive;

@RestController
@RequestMapping(value = "/inputProduct")
public class InputProductController {

	@Autowired
	InputProductSercive inputProductSercive;
	
	@PostMapping
	public HttpEntity<?> addInputProduct(@Valid @RequestBody InputProductDto inputProductDto) {
		Result result = inputProductSercive.addInputProduct(inputProductDto);
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.ACCEPTED).body(result);
	}
	@GetMapping
	public HttpEntity<?> geInputProducts(){
		List<InputProduct> inputProducts = inputProductSercive.getInputProducts();
		return ResponseEntity.status(inputProducts.isEmpty()?HttpStatus.NOT_FOUND:HttpStatus.OK).body(inputProducts);
	}
	@GetMapping(value = "/{id}")
	public HttpEntity<?> getInputProduct(@PathVariable Integer id) {
		Result result = inputProductSercive.getInputProduct(id);
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.NOT_FOUND).body(result);
	}
	@PutMapping(value = "/{id}")
	public HttpEntity<?> editInputProduct(@PathVariable Integer id, @Valid @RequestBody InputProductDto inputProductDto) {
		Result result = inputProductSercive.editInputProduct(id, inputProductDto);
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.NOT_FOUND).body(result);
	}
	@DeleteMapping("/{id}")
	public HttpEntity<?> deletInputProduct(@PathVariable Integer id) {
		Result result = inputProductSercive.deletInputProduct(id);
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
