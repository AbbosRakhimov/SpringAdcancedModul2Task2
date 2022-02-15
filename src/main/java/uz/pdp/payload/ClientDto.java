package uz.pdp.payload;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ClientDto {
	
	@NotNull(message = "PhonNumber is mandatory")
	private String phonNumber;
	
	@NotNull(message = "Name is mandatory")
	private String name;

}
