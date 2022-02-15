package uz.pdp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
public class Attachment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String fileOriginalName;
	
	private long size;
	
	private String contenType;

	public Attachment(String fileOriginalName, long size, String contenType) {
		super();
		this.fileOriginalName = fileOriginalName;
		this.size = size;
		this.contenType = contenType;
	}
	
	
}
