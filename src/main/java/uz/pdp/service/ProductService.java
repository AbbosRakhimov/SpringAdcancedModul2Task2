package uz.pdp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uz.pdp.entity.Attachment;
import uz.pdp.entity.Category;
import uz.pdp.entity.Product;
import uz.pdp.payload.ProductDto;
import uz.pdp.payload.Result;
import uz.pdp.repository.AttachmentRepository;
import uz.pdp.repository.CategoryRepository;
import uz.pdp.repository.ProductRepository;

@Service
public class ProductService {

	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	AttachmentRepository attachmentRepository;
	
	
	public Result addProduct(ProductDto productDto) {
		if(productRepository.existsByNameAndCategoryId(productDto.getName(), productDto.getCategoryId()))
			return new Result("Product already exist in Category", false);
		Optional<Category> cOptional = categoryRepository.findById(productDto.getCategoryId());
		if(!cOptional.isPresent())
			return new Result("Category not found", false);
		Optional<Attachment> aOptional = attachmentRepository.findById(productDto.getPhotoId());
		if(!aOptional.isPresent())
			return new Result("Attachment not found", false);
		Product product = new Product();
		product.setCategory(cOptional.get());
		product.setCode(code());
		product.setName(productDto.getName());
		product.setPhoto(aOptional.get());
		productRepository.save(product);
		
		return new Result("Product added", true);
	}
	public  String code() {
		List<Product> products = productRepository.findAll();
		String a;
		if(products.size()<=0) {
			a="1";
		}else {
			a = String.valueOf(products.get(products.size()-1).getId()+1);
		}
		return a;
	}
	public List<Product> getProducts(){
		return productRepository.findAll();
	}
	public Result getProduct(Integer id) {
		Optional<Product> pOptional = productRepository.findById(id);
		if(pOptional.isPresent()) {
			new Result("Product exist", true, pOptional.get());
		}
		return new Result("Product not found", false);
	}
	public Result editProduct(Integer id, ProductDto productDto) {
		if(productRepository.existsByNameAndCategoryId(productDto.getName(), productDto.getCategoryId()))
			return new Result("Product already exist in Category", false);
		Optional<Category> cOptional = categoryRepository.findById(productDto.getCategoryId());
		if(!cOptional.isPresent())
			return new Result("Category not found", false);
		Optional<Attachment> aOptional = attachmentRepository.findById(productDto.getPhotoId());
		if(!aOptional.isPresent())
			return new Result("Attachment not found", false);
		Optional<Product> pOptional = productRepository.findById(id);
		if(!pOptional.isPresent())
			return new Result("Product not found", false);
		Product product = pOptional.get();
		product.setCategory(cOptional.get());
		product.setCode(code());
		product.setName(productDto.getName());
		product.setPhoto(aOptional.get());
		productRepository.save(product);
		return new Result("Product edited", true);
	}
	public Result deletePrduct(Integer id) {
		Optional<Product> pOptional = productRepository.findById(id);
		if(pOptional.isPresent()) {
			productRepository.deleteById(id);
			return new Result("Product deleted", true);
		}
		return new Result("Product not found", false);

	}
}
