package uz.pdp.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uz.pdp.entity.Client;
import uz.pdp.entity.Currency;
import uz.pdp.entity.Input;
import uz.pdp.entity.Output;
import uz.pdp.entity.Supplier;
import uz.pdp.entity.Warehouse;
import uz.pdp.payload.InputDto;
import uz.pdp.payload.OutputDto;
import uz.pdp.payload.Result;
import uz.pdp.repository.ClientRepository;
import uz.pdp.repository.CurrencyRepository;
import uz.pdp.repository.OutputRepository;
import uz.pdp.repository.WarehouseRepository;

@Service
public class OutputService {

	@Autowired
	CurrencyRepository currencyRepository;
	
	@Autowired
	WarehouseRepository warehouseRepository;
	
	@Autowired
	OutputRepository outputRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	public Result addOutput(OutputDto outputDto) {
		if(outputRepository.existsByFactureName(outputDto.getFactureName()))
			return new Result ("FacturName already exist",false);
		Optional<Currency> cOptional = currencyRepository.findById(outputDto.getCurrencyId());
		if(!cOptional.isPresent())
			return new Result ("Currency not found",false);
		Optional<Warehouse> wOptional = warehouseRepository.findById(outputDto.getWarehouseId());
		if(!wOptional.isPresent())
			return new Result ("Warehouse not found",false);
		Optional<Client> sOptional = clientRepository.findById(outputDto.getClientId());
		if(!sOptional.isPresent())
			return new Result ("Client not found",false);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Output output = new Output();
		output.setDate(timestamp);
		output.setFactureName(outputDto.getFactureName());
		output.setCurrency(cOptional.get());
		output.setClient(sOptional.get());
		output.setWarehouse(wOptional.get());
		output.setCode(code());
		outputRepository.save(output);
		return new Result ("Input added",true);
	}
	public  String code() {
		List<Output> outputs = outputRepository.findAll();
		String a;
		if(outputs.size()<=0) {
			a="1";
		}else {
			a = String.valueOf(outputs.get(outputs.size()-1).getId()+1);	
		}
		return a;
	}
	public List<Output> getOutputs(){
		return outputRepository.findAll();
	}
	public Result getOutput(Integer id) {
		Optional<Output> iOptional=outputRepository.findById(id);
		if(iOptional.isPresent()) {
			return new Result("Output exist", true, iOptional.get());
		}
		return new Result("Output not exist", false);
	}
	public Result editOutput(Integer id, OutputDto outputDto) {
		if(outputRepository.existsByFactureName(outputDto.getFactureName()))
			return new Result ("FacturName already exist",false);
		Optional<Currency> cOptional = currencyRepository.findById(outputDto.getCurrencyId());
		if(!cOptional.isPresent())
			return new Result ("Currency not found",false);
		Optional<Warehouse> wOptional = warehouseRepository.findById(outputDto.getWarehouseId());
		if(!wOptional.isPresent())
			return new Result ("Warehouse not found",false);
		Optional<Client> sOptional = clientRepository.findById(outputDto.getClientId());
		if(!sOptional.isPresent())
			return new Result ("Client not found",false);
		Optional<Output> iOptional = outputRepository.findById(id);
		if(!iOptional.isPresent())
			return new Result ("Output not found",false);
		
		Output output = iOptional.get();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		output.setDate(timestamp);
		output.setFactureName(outputDto.getFactureName());
		output.setCurrency(cOptional.get());
		output.setClient(sOptional.get());
		output.setWarehouse(wOptional.get());
		output.setCode(code());
		outputRepository.save(output);
		return new Result ("Input edited",true);
	}
	public Result deleteOutput(Integer id) {
		Optional<Output> iOptional = outputRepository.findById(id);
		if(iOptional.isPresent()) {
			outputRepository.deleteById(id);
			return new Result ("Output delete",true);
		}
		return new Result ("Output not found",false);

	}
}
