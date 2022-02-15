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
import uz.pdp.entity.Output;
import uz.pdp.entity.OutputProduct;
import uz.pdp.entity.Product;
import uz.pdp.payload.InputProductDto;
import uz.pdp.payload.OutputProductDto;
import uz.pdp.payload.Result;
import uz.pdp.repository.OutputProductRepository;
import uz.pdp.repository.OutputRepository;
import uz.pdp.repository.ProductRepository;

@Service
public class OutputProductService {

	@Autowired
	OutputProductRepository outputProductRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	OutputRepository outputRepository;
	
	@SneakyThrows
	public Result addOutputProduct(OutputProductDto outputProductDto) {
		if(outputProductRepository.existsByProductIdAndOutputId(outputProductDto.getProductId(), outputProductDto.getOutputId()))
			return new Result("Product already exist", false);
		Optional<Output> iOptional = outputRepository.findById(outputProductDto.getOutputId());
		if(!iOptional.isPresent())
			return new Result("Output not found", false);
		Optional<Product> pOptional = productRepository.findById(outputProductDto.getProductId());
		if(!pOptional.isPresent())
			return new Result("Product not found", false);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = (Date) simpleDateFormat.parse( (simpleDateFormat.format(outputProductDto.getExpireDate())));
		OutputProduct outputProduct = new OutputProduct(pOptional.get(), outputProductDto.getAmount(), outputProductDto.getPrice(), iOptional.get(), date);
		
		outputProductRepository.save(outputProduct);
		return new Result("OutputProduct added", true);

	}
	public List<OutputProduct> getOutputProducts(){
		return outputProductRepository.findAll();
	}
	public Result getOutputProduct(Integer id) {
		Optional<OutputProduct> iOptional = outputProductRepository.findById(id);
		if(iOptional.isPresent()) {
			return new Result("OutputProduct exist", true, iOptional.get());
		}
		return new Result("OutputProduct not found", false);

	}
	@SneakyThrows
	public Result editOutputProduct(Integer id, OutputProductDto outputProductDto) {
		if(outputProductRepository.existsByProductIdAndOutputId(outputProductDto.getProductId(), outputProductDto.getOutputId()))
			return new Result("Product already exist", false);
		Optional<Output> iOptional = outputRepository.findById(outputProductDto.getOutputId());
		if(!iOptional.isPresent())
			return new Result("Output not found", false);
		Optional<Product> pOptional = productRepository.findById(outputProductDto.getProductId());
		if(!pOptional.isPresent())
			return new Result("Product not found", false);
		Optional<OutputProduct> inOptional = outputProductRepository.findById(id);
		if(inOptional.isPresent())
			return new Result("OutputProduct not found", false);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = (Date) simpleDateFormat.parse( (simpleDateFormat.format(outputProductDto.getExpireDate())));
		OutputProduct outputProduct = inOptional.get();
		outputProduct.setAmount(outputProductDto.getAmount());
		outputProduct.setExpireDate(date);
		outputProduct.setOutput(iOptional.get());
		outputProduct.setPrice(outputProductDto.getPrice());
		outputProduct.setProduct(pOptional.get());
		outputProductRepository.save(outputProduct);
		
		return new Result("OutputProduct edited", true);
	}
	public Result deletOutputProduct(Integer id) {
		Optional<OutputProduct> iOptional = outputProductRepository.findById(id);
		if(iOptional.isPresent()) {
			outputProductRepository.deleteById(id);
			return new Result("OutputProduct deleted", true);
		}
		return new Result("OutputProduct not found", false);
	}
	
}
