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

import uz.pdp.entity.Output;
import uz.pdp.payload.OutputDto;
import uz.pdp.payload.Result;
import uz.pdp.service.OutputService;

@RestController
@RequestMapping(value = "/output")
public class OutputController {

	@Autowired
	OutputService outputService;
	
	@PostMapping
	public HttpEntity<?> addOutput(@Valid @RequestBody OutputDto outputDto) {
		Result result = outputService.addOutput(outputDto);
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.BAD_REQUEST).body(result);
	}
	@GetMapping
	public HttpEntity<?> geOutputs(){
		List<Output> outputs = outputService.getOutputs();
		return ResponseEntity.status(outputs.isEmpty()?HttpStatus.NOT_FOUND:HttpStatus.OK).body(outputs);
	}
	@GetMapping(value = "/{id}")
	public HttpEntity<?> getOutput(@PathVariable Integer id) {
		Result result = outputService.getOutput(id); 
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.NOT_FOUND).body(result); 
	}
	@PutMapping(value = "/{id}")
	public HttpEntity<?> editOutput(@PathVariable Integer id, @Valid @RequestBody OutputDto outputDto) {
		Result result = outputService.editOutput(id, outputDto); 
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(result); 
	}
	@DeleteMapping(value = "/{id}")
	public HttpEntity<?> deleteOutput(@PathVariable Integer id) {
		Result result = outputService.deleteOutput(id); 
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
