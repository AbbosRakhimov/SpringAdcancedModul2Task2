package uz.pdp.payload;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SupplierDto {

	@NotNull(message = "Name is mandatory")
	private String name;
	
	@NotNull(message = "PhonNumber is mandatotry")
	private String phonNumber;
}
