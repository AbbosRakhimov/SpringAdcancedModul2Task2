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

import uz.pdp.entity.OutputProduct;

import uz.pdp.payload.OutputProductDto;
import uz.pdp.payload.Result;
import uz.pdp.service.OutputProductService;

@RestController
@RequestMapping(value = "/outputProduct")
public class OutputProductController {

	@Autowired
	OutputProductService outputProductService;
	

	@PostMapping
	public HttpEntity<?> addInputProduct(@Valid @RequestBody OutputProductDto outputProductDto) {
		Result result = outputProductService.addOutputProduct(outputProductDto);
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.BAD_REQUEST).body(result);
	}
	@GetMapping
	public HttpEntity<?> geOutputProducts(){
		List<OutputProduct> outputProducts = outputProductService.getOutputProducts();;
		return ResponseEntity.status(outputProducts.isEmpty()?HttpStatus.NOT_FOUND:HttpStatus.OK).body(outputProducts);
	}
	@GetMapping(value = "/{id}")
	public Result getOutputProduct(@PathVariable Integer id) {
		return outputProductService.getOutputProduct(id);
	}
	@PutMapping(value = "/{id}")
	public HttpEntity<?> editOutputProduct(@PathVariable Integer id, @Valid @RequestBody OutputProductDto outputProductDto) {
		Result result = outputProductService.editOutputProduct(id, outputProductDto);
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(result);
	}
	@DeleteMapping("/{id}")
	public HttpEntity<?> deletOutputProduct(@PathVariable Integer id) {
		Result result = outputProductService.deletOutputProduct(id);
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(result);
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
