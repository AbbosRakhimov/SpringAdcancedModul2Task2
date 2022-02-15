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

import uz.pdp.entity.Product;
import uz.pdp.payload.ProductDto;
import uz.pdp.payload.Result;
import uz.pdp.service.ProductService;


@RestController
@RequestMapping(value = "/product")
public class ProductController {

	@Autowired
	ProductService productService;
	
	@PostMapping
	public HttpEntity<?> addProduct(@Valid @RequestBody ProductDto productDto) {
		Result result = productService.addProduct(productDto);
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.BAD_REQUEST).body(result);
	}
	@GetMapping
	public HttpEntity<?> getProducts(){
		List<Product> products = productService.getProducts();
		return ResponseEntity.status(products.isEmpty()?HttpStatus.NOT_FOUND:HttpStatus.OK).body(products);
	}
	@GetMapping(value = "/{id}")
	public HttpEntity<?> getProduct(@PathVariable Integer id) {
		Result result = productService.getProduct(id);
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.NOT_FOUND).body(result);
	}
	@PutMapping(value = "/{id}")
	public HttpEntity<?> editProduct(@PathVariable Integer id, @Valid @RequestBody ProductDto productDto) {
		Result result = productService.editProduct(id, productDto);
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(result);
	}
	@DeleteMapping(value = "/{id}")
	public HttpEntity<?> deletProduct(@PathVariable Integer id) {
		Result result = productService.deletePrduct(id);
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
