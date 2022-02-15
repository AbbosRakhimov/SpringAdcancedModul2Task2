package uz.pdp.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.SneakyThrows;
import uz.pdp.entity.Input;
import uz.pdp.entity.InputProduct;
import uz.pdp.entity.Product;
import uz.pdp.payload.InputProductDto;
import uz.pdp.payload.Result;
import uz.pdp.repository.InputProductRepository;
import uz.pdp.repository.InputRepository;
import uz.pdp.repository.ProductRepository;

@Service
public class InputProductSercive {

	@Autowired
	InputProductRepository inputProductRepository;
	
	@Autowired
	InputRepository inputRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@SneakyThrows
	public Result addInputProduct(InputProductDto inputProductDto) {
		if(inputProductRepository.existsByProductId(inputProductDto.getProductId()))
			return new Result("Product already exist", false);
		Optional<Input> iOptional = inputRepository.findById(inputProductDto.getInputId());
		if(!iOptional.isPresent())
			return new Result("Input not found", false);
		Optional<Product> pOptional = productRepository.findById(inputProductDto.getProductId());
		if(!pOptional.isPresent())
			return new Result("Product not found", false);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = (Date) simpleDateFormat.parse( (simpleDateFormat.format(inputProductDto.getExpireDate())));
		InputProduct inputProduct = new InputProduct(pOptional.get(), inputProductDto.getAmount(), inputProductDto.getPrice(), iOptional.get(), date);
		
		inputProductRepository.save(inputProduct);
		return new Result("InputProduct added", true);

	}
	public List<InputProduct> getInputProducts(){
		return inputProductRepository.findAll();
	}
	public Result getInputProduct(Integer id) {
		Optional<InputProduct> iOptional = inputProductRepository.findById(id);
		if(iOptional.isPresent()) {
			return new Result("InputProduct exist", true, iOptional.get());
		}
		return new Result("InputProduct not found", false);

	}
	@SneakyThrows
	public Result editInputProduct(Integer id, InputProductDto inputProductDto) {
		if(inputProductRepository.existsByProductId(inputProductDto.getProductId()))
			return new Result("Product already exist", false);
		Optional<Input> iOptional = inputRepository.findById(inputProductDto.getInputId());
		if(!iOptional.isPresent())
			return new Result("Input not found", false);
		Optional<Product> pOptional = productRepository.findById(inputProductDto.getProductId());
		if(!pOptional.isPresent())
			return new Result("Product not found", false);
		Optional<InputProduct> inOptional = inputProductRepository.findById(id);
		if(inOptional.isPresent())
			return new Result("InputProduct not found", false);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = (Date) simpleDateFormat.parse( (simpleDateFormat.format(inputProductDto.getExpireDate())));
		InputProduct inputProduct = inOptional.get();
		inputProduct.setAmount(inputProductDto.getAmount());
		inputProduct.setExpireDate(date);
		inputProduct.setInput(iOptional.get());
		inputProduct.setPrice(inputProductDto.getPrice());
		inputProduct.setProduct(pOptional.get());
		inputProductRepository.save(inputProduct);
		
		return new Result("InputProduct edited", true);
	}
	public Result deletInputProduct(Integer id) {
		Optional<InputProduct> iOptional = inputProductRepository.findById(id);
		if(iOptional.isPresent()) {
			inputProductRepository.deleteById(id);
			return new Result("InputProduct deleted", true);
		}
		return new Result("InputProduct not found", false);
	}
}
