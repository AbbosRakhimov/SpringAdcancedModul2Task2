package uz.pdp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;
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

import uz.pdp.entity.Client;
import uz.pdp.payload.ClientDto;
import uz.pdp.payload.Result;
import uz.pdp.service.ClientService;

@RestController
@RequestMapping(value = "/client")
public class ClientController {

	@Autowired
	ClientService clientService;
	
	@PostMapping
	public HttpEntity<?> addClient(@Valid @RequestBody ClientDto clientDto) {
		Result result = clientService.addClient(clientDto);
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.BAD_REQUEST).body(result);
	}
	@GetMapping
	public HttpEntity<?> getClients(){
		List<Client> clients = clientService.geClients();
		return ResponseEntity.status(clients.isEmpty()?HttpStatus.NOT_FOUND:HttpStatus.OK).body(clients);
	}
	@GetMapping(value = "/{id}")
	public HttpEntity<?> getClient(@PathVariable Integer id) {
		Result result = clientService.getCleint(id);
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.NOT_FOUND).body(result);
	}
	@PutMapping(value = "/{id}")
	public HttpEntity<?> editCleint(@PathVariable Integer id, @Valid @RequestBody ClientDto clientDto) {
		Result result = clientService.editClient(id, clientDto);
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(result);
	}
	@DeleteMapping(value = "/{id}")
	public HttpEntity<?> deleteClient(@PathVariable Integer id) {
		Result result = clientService.deleteClient(id);
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
