package uz.pdp.entity;

import java.sql.Timestamp;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Output {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Timestamp date;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Warehouse warehouse;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Client client;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Currency currency;
	
	private String factureName;
	
	@Column(nullable = false, unique = true)
	private String code;
	
	
	
	
}
