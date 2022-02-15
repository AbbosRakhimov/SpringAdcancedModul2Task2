package uz.pdp.payload;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OutputDto {

	@NotNull(message = "ID for WareHouse is mandatory")
	private Integer warehouseId;
	
	@NotNull(message = "ID for Client is mandatory")
	private Integer clientId;
	
	@NotNull(message = "ID for Currency is mandatory")
	private Integer currencyId;
	
	@NotNull(message = "FactureName is mandatory")
	private String factureName;
}
