package uz.pdp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import uz.pdp.entity.Supplier;
import uz.pdp.payload.Result;
import uz.pdp.payload.SupplierDto;
import uz.pdp.repository.SupplierRepository;

@Service
public class SupplierService {

	@Autowired
	SupplierRepository supplierRepository;
	
	public Result addSupplier(SupplierDto supplierDto) {
		if(supplierRepository.existsByNameOrPhonNumber(supplierDto.getName(), supplierDto.getPhonNumber()))
			return new Result("Supplier exist", false);
		Supplier supplier =new Supplier();
		supplier.setName(supplierDto.getName());
		supplier.setPhonNumber(supplierDto.getPhonNumber());
		supplierRepository.save(supplier);
		
		return new Result("Supplier added", true);
	}
	public List<Supplier> getSuppliers(){
		return supplierRepository.findAll();
	}
	public Result getSupplier(Integer id) {
		Optional<Supplier> sOptional = supplierRepository.findById(id);
		if(sOptional.isPresent()) {
			return new Result("Supplier exist", true, sOptional.get());
		}
		return new Result("Supplier  not exist", false);
	}
	public Result editSupplier(Integer id, SupplierDto supplierDto) {
		if(supplierRepository.existsByNameOrPhonNumber(supplierDto.getName(), supplierDto.getPhonNumber()))
			return new Result("Supplier  not exist", false);
		Optional<Supplier> sOptional = supplierRepository.findById(id);
		if(!sOptional.isPresent())
			return new Result("Supplier  not exist", false);
		Supplier supplier = sOptional.get();
		supplier.setName(supplierDto.getName());
		supplier.setPhonNumber(supplierDto.getPhonNumber());
		supplierRepository.save(supplier);
		return new Result("Supplier edited", true);

	}
	public Result deleSupplier(Integer id) {
		Optional<Supplier> spOptional = supplierRepository.findById(id);
		if(spOptional.isPresent()) {
			supplierRepository.deleteById(id);
			return new Result("Supplier  deleted", true);
		}
		return new Result("Supplier  not found", false);
	}
}
