package uz.pdp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uz.pdp.entity.Client;
import uz.pdp.payload.ClientDto;
import uz.pdp.payload.Result;
import uz.pdp.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	ClientRepository clientRepository;
	
	public Result addClient(ClientDto clientDto) {
		if(clientRepository.existsByNameOrPhonNumber(clientDto.getName(), clientDto.getPhonNumber()))
			return new Result("Client exist", false);
		if(clientDto.getPhonNumber().length()<7 || clientDto.getPhonNumber().length()>7)
			return new Result("please enter the correct telephone number", false);
		Client client = new Client();
		client.setName(clientDto.getName());
		client.setPhonNumber(clientDto.getPhonNumber());
		clientRepository.save(client);
		return new Result("Client added", true);
	}
	public List<Client> geClients(){
		return clientRepository.findAll();
	}
	public Result getCleint(Integer id) {
		Optional<Client> cOptional = clientRepository.findById(id);
		if(cOptional.isPresent()) {
			return new Result("Client exist", true, cOptional.get()); 
		}
		return new Result("Client not exist", false); 

	}
	public Result editClient(Integer id, ClientDto clientDto) {
		if(clientRepository.existsByNameOrPhonNumber(clientDto.getName(), clientDto.getPhonNumber()))
			return new Result("Client exist", false);
		if(clientDto.getPhonNumber().length()<7 || clientDto.getPhonNumber().length()>7)
			return new Result("please enter the correct telephone number", false);
		Optional<Client> cOptional = clientRepository.findById(id);
		if(!cOptional.isPresent())
			return new Result("Client not exist", false);
		Client client = cOptional.get();
		client.setName(clientDto.getName());
		client.setPhonNumber(clientDto.getPhonNumber());
		return new Result("Client successfuly edited", true);

	}
	public Result deleteClient(Integer id) {
		Optional<Client> cOptional= clientRepository.findById(id);
		if(cOptional.isPresent()) {
			cOptional.get().setAvtive(false);
			clientRepository.deleteById(id);
			return new Result("Client successfuly deleted", true);
		}
		return new Result("Client not exist", false);

	}
}
