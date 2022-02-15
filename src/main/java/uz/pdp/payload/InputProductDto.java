package uz.pdp.payload;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class InputProductDto {

	@NotNull(message = "ID for Product is mandatory")
	private Integer productId;
	
	@NotNull(message = "ID for Input is mandatory")
	private Integer inputId;
	
	@NotNull(message = "Amount is mandatory")
	private Double amount;
	
	@NotNull(message = "Price is mandatory")
	private Double price;
	
	@NotNull(message = "ID for Product is mandatory")
	private Date expireDate;


}
