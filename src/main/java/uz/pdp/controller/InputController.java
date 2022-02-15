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

import uz.pdp.entity.Input;
import uz.pdp.payload.InputDto;
import uz.pdp.payload.Result;
import uz.pdp.service.InputService;

@RestController
@RequestMapping(value = "/input")
public class InputController {

	@Autowired
	InputService inputService;
	
	@PostMapping
	public HttpEntity<?> addInput(@Valid @RequestBody InputDto inputDto) {
		Result result = inputService.addInput(inputDto);
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.BAD_REQUEST).body(result);
	}
	@GetMapping
	public HttpEntity<?> getInputs(){
		List<Input> inputs = inputService.geInputs();
		return ResponseEntity.status(inputs.isEmpty()?HttpStatus.NOT_FOUND:HttpStatus.OK).body(inputs);
	}
	@GetMapping(value = "/{id}")
	public HttpEntity<?> getInput(@PathVariable Integer id) {
		Result result = inputService.getInput(id); 
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.NOT_FOUND).body(result);
	}
	@PutMapping("/{id}")
	public HttpEntity<?> editsInput(@PathVariable Integer id, @Valid @RequestBody InputDto inputDto) {
		Result result = inputService.editInput(id, inputDto);
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(result);
	}
	@DeleteMapping(value = "/{id}")
	public HttpEntity<?> deletInput(@PathVariable Integer id) {
		Result result = inputService.deleteInput(id);
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
