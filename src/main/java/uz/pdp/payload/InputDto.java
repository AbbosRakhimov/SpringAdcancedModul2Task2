package uz.pdp.payload;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class InputDto {
	
	@NotNull(message = "ID for Warehouse is mandatory")
	private Integer warehouseId;
	
	@NotNull(message = "ID for Supplier is mandatory")
	private Integer supplierId;
	
	@NotNull(message = "ID for Currency is mandatory")
	private Integer currencyId;
	
	@NotNull(message = "FactureName is mandatory")
	private String factureName;
}
