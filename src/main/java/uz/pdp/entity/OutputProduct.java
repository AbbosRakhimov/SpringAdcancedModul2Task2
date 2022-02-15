package uz.pdp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OutputProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Product product;
	
	@Column(nullable = false)
	private Double amount;
	
	private Double price;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Output output;
	
	private Date expireDate;

	public OutputProduct(Product product, Double amount, Double price, Output output, Date expireDate) {
		super();
		this.product = product;
		this.amount = amount;
		this.price = price;
		this.output = output;
		this.expireDate = expireDate;
	}
	
	
	
	
}
