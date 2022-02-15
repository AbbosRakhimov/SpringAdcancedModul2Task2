package uz.pdp.payload;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CategoryDto {
	
	@NotNull(message = "Name is mandatory")
	private String name;
	
	@NotNull(message = "Name is mandatory")
	private Integer parentCategoryId;
	
}
