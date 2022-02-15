package uz.pdp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uz.pdp.entity.Category;
import uz.pdp.payload.CategoryDto;
import uz.pdp.payload.Result;
import uz.pdp.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired 
	CategoryRepository categoryRepository;
	
	public Result saveCategory(CategoryDto categoryDto) {
		if(categoryRepository.existsByName(categoryDto.getName()))
			return new Result("Category already exist", false);
		Category category = new Category();
		category.setName(categoryDto.getName());
		if(categoryDto.getParentCategoryId()!=null) {
			Optional<Category> cOptional = categoryRepository.findById(categoryDto.getParentCategoryId());
			if(!cOptional.isPresent())
				return new Result("Category not found", false);
			category.setCategory(cOptional.get());
		}
		categoryRepository.save(category);
		return new Result("Category successfuly saved", true);
	}
	
	public List< Category> getCategoryes(){
		return categoryRepository.findAll();
	}
	public Result getCategory(Integer id) {
		Optional<Category> cOptional = categoryRepository.findById(id);
		if(cOptional.isPresent()) {
			return new Result("Category exists", true, cOptional.get());
		}
		return new Result("Category not exists", false);
	}
	public Result editCategory(Integer id, CategoryDto categoryDto) {
		if(categoryRepository.existsByName(categoryDto.getName()))
			return new Result("Category already exist", false);
		Optional<Category> cOptional = categoryRepository.findById(id);
		if(!cOptional.isPresent())
			return new Result("Category not found", false);
		Category category = cOptional.get();
		category.setName(categoryDto.getName());
		if(categoryDto.getParentCategoryId()!=null) {
			Optional<Category> dOptional = categoryRepository.findById(categoryDto.getParentCategoryId());
			if(!dOptional.isPresent())
				return new Result("Category not found", false);
			category.setCategory(dOptional.get());
		}
		categoryRepository.save(category);
		return new Result("Category edited", true);
	}
	public Result delteCategory(Integer id) {
		Optional<Category> cOptional = categoryRepository.findById(id);
		if(cOptional.isPresent()) {
			cOptional.get().setAvtive(false);
			categoryRepository.deleteById(id);
			return new Result("Category deleted", true);
		}
		return new Result("Category not found", false);
	}
}
