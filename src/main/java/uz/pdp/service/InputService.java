package uz.pdp.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uz.pdp.entity.Currency;
import uz.pdp.entity.Input;
import uz.pdp.entity.Supplier;
import uz.pdp.entity.Warehouse;
import uz.pdp.payload.InputDto;
import uz.pdp.payload.Result;
import uz.pdp.repository.CurrencyRepository;
import uz.pdp.repository.InputRepository;
import uz.pdp.repository.SupplierRepository;
import uz.pdp.repository.WarehouseRepository;

@Service
public class InputService {

	@Autowired
	InputRepository inputRepository;
	
	@Autowired
	CurrencyRepository currencyRepository;
	
	@Autowired
	WarehouseRepository warehouseRepository;
	
	@Autowired
	SupplierRepository supplierRepository;
	
	public Result addInput(InputDto inputDto) {
		if(inputRepository.existsByFactureName(inputDto.getFactureName()))
			return new Result ("FacturName already exist",false);
		Optional<Currency> cOptional = currencyRepository.findById(inputDto.getCurrencyId());
		if(!cOptional.isPresent())
			return new Result ("Currency not found",false);
		Optional<Warehouse> wOptional = warehouseRepository.findById(inputDto.getWarehouseId());
		if(!wOptional.isPresent())
			return new Result ("Warehouse not found",false);
		Optional<Supplier> sOptional = supplierRepository.findById(inputDto.getSupplierId());
		if(!sOptional.isPresent())
			return new Result ("Supplier not found",false);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Input input = new Input();
		input.setDate(timestamp);
		input.setFactureName(inputDto.getFactureName());
		input.setCurrency(cOptional.get());
		input.setSupplier(sOptional.get());
		input.setWarehouse(wOptional.get());
		input.setCode(code());
		inputRepository.save(input);
		return new Result ("Input added",true);

	}
	public  String code() {
		List<Input> inputs = inputRepository.findAll();
		String a;
		if(inputs.size()<=0) {
			a="1";
		}else {
			 a = String.valueOf(inputs.get(inputs.size()-1).getId()+1);
		}
		return a;
	}
	public List<Input> geInputs(){
		return inputRepository.findAll();
	}
	public Result getInput(Integer id) {
		Optional<Input> iOptional=inputRepository.findById(id);
		if(iOptional.isPresent()) {
			return new Result("Input exist", true, iOptional.get());
		}
		return new Result("Input not exist", false);
	}
	public Result editInput(Integer id, InputDto inputDto) {
		if(inputRepository.existsByFactureName(inputDto.getFactureName()))
			return new Result ("FacturName already exist",false);
		Optional<Currency> cOptional = currencyRepository.findById(inputDto.getCurrencyId());
		if(!cOptional.isPresent())
			return new Result ("Currency not found",false);
		Optional<Warehouse> wOptional = warehouseRepository.findById(inputDto.getWarehouseId());
		if(!wOptional.isPresent())
			return new Result ("Warehouse not found",false);
		Optional<Supplier> sOptional = supplierRepository.findById(inputDto.getSupplierId());
		if(!sOptional.isPresent())
			return new Result ("Supplier not found",false);
		Optional<Input> iOptional = inputRepository.findById(id);
		if(!iOptional.isPresent())
			return new Result ("Input not found",false);
		
		Input input = iOptional.get();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		input.setDate(timestamp);
		input.setFactureName(inputDto.getFactureName());
		input.setCurrency(cOptional.get());
		input.setSupplier(sOptional.get());
		input.setWarehouse(wOptional.get());
		input.setCode(code());
		inputRepository.save(input);
		return new Result ("Input edited",true);
	}
	public Result deleteInput(Integer id) {
		Optional<Input> iOptional = inputRepository.findById(id);
		if(iOptional.isPresent()) {
			inputRepository.deleteById(id);
			return new Result ("Input delete",true);
		}
		return new Result ("Input not found",false);

	}
}
