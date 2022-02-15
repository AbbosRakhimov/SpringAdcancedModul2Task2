package uz.pdp.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import uz.pdp.entity.Attachment;
import uz.pdp.payload.Result;
import uz.pdp.repository.AttachmentContentRepository;
import uz.pdp.repository.AttachmentRepository;
import uz.pdp.service.AttachmentService;

@RestController
@RequestMapping(value = "/attachment")
public class AttachmentController {

	@Autowired
	AttachmentService attachmentService;
	
	@PostMapping(value = "/upload")
	public HttpEntity<?> upload(MultipartHttpServletRequest request) {
		Result result = attachmentService.uploadFile(request);
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(result);
	}
	@GetMapping("/info")
	public HttpEntity<?> getAllAttachment() {
		List<Attachment> attachments = attachmentService.getAlResult();
		return ResponseEntity.status(attachments.isEmpty()?HttpStatus.OK:HttpStatus.CONFLICT).body(attachments);
	}
	@GetMapping("info/{id}")
	public HttpEntity<?> getAttachment(@PathVariable Integer id) {
		Result result = attachmentService.getAttachment(id); 
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.NOT_FOUND).body(result);
	}
	@PutMapping("/{id}")
	public HttpEntity<?> saveAttachment(@PathVariable Integer id, MultipartHttpServletRequest request) {
		Result result = attachmentService.editAttachment(id, request); 
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(result);
	}
	@GetMapping("/download/{id}")
	public HttpEntity<?> getDownloadFile(@PathVariable Integer id, HttpServletResponse response) {
		Result result = attachmentService.downloadFile(id, response);
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.CONFLICT).body(result);
	}
	@DeleteMapping("/{id}")
	public HttpEntity<?> deleteAttachment(@PathVariable Integer id) {
		Result result = attachmentService.deleteAtaResult(id);
		return ResponseEntity.status(result.isSuccess()?HttpStatus.OK:HttpStatus.NOT_FOUND).body(result);	}
		
}
