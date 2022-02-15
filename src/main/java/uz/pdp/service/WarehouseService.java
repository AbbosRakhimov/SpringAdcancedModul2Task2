package uz.pdp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uz.pdp.entity.Warehouse;
import uz.pdp.payload.Result;
import uz.pdp.repository.SupplierRepository;
import uz.pdp.repository.WarehouseRepository;

@Service
public class WarehouseService {

	@Autowired
	WarehouseRepository warehouseRepository;
	
	public Result addWarehous(Warehouse warehouse) {
		if(warehouseRepository.existsByName(warehouse.getName()))
			return new Result("Warehouse exist", false);
		Warehouse warehouse2 = new Warehouse();
		warehouse2.setName(warehouse.getName());
		warehouseRepository.save(warehouse2);
		
		return new Result("Warehouse added", true);
	}
	public List<Warehouse> getWarehouses(){
		return warehouseRepository.findAll();
	}
	public Result getWarehi(Integer id) {
		Optional<Warehouse> wOptional = warehouseRepository.findById(id);
		if(wOptional.isPresent()) {
			return new Result("Warehouse exist", true, wOptional.get());
		}
		return new Result("Warehouse not found", false);
	}
	public Result editWarehouse(Integer id, Warehouse warehouse) {
		if(warehouseRepository.existsByName(warehouse.getName()))
			return new Result("Warehouse exist", false);
		Optional<Warehouse> wOptional = warehouseRepository.findById(id);
		if(!wOptional.isPresent())
			return new Result("Warehouse not found", false);
		Warehouse warehouse2 = wOptional.get();
		warehouse2.setName(warehouse.getName());
		warehouseRepository.save(warehouse2);
		
		return new Result("Warehouse edited", true);
	}
	public Result deleteWarehouse(Integer id) {
		Optional<Warehouse> wOptional = warehouseRepository.findById(id);
		if(wOptional.isPresent()){
			warehouseRepository.deleteById(id);
			return new Result("Warehouse deleted", true);
		}
		return new Result("Warehouse not found", false);
	}
}
