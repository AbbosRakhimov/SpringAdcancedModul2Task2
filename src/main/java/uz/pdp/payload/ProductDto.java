package uz.pdp.payload;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProductDto {

	@NotNull(message = " Name is mandatory")
	private String name;
	
	@NotNull(message = "ID for Category is mandatory")
	private Integer categoryId;
	
	@NotNull(message = "PhotoId is for Photo necessary")
	private Integer photoId;
	
	@NotNull(message = "MesaurementId is not be Empty")
	private Integer measurementId;
}
