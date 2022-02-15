package uz.pdp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class AttachmentContent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private byte[] bytes;
	
	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Attachment attachment;

	public AttachmentContent(byte[] bytes, Attachment attachment) {
		super();
		this.bytes = bytes;
		this.attachment = attachment;
	}
	
	
}
