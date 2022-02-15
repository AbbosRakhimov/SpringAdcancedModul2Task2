package uz.pdp.payload;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OutputProductDto {

	@NotNull(message = "ID for Product is mandatory")
	private Integer productId;
	
	@NotNull(message = "ID for Output is mandatory")
	private Integer outputId;
	
	@NotNull(message = "Amount is mandatory")
	private Double amount;
	
	@NotNull(message = "Price is mandatory")
	private Double price;
	
	@NotNull(message = "ExpireDate is mandatory")
	private Date expireDate;
}
