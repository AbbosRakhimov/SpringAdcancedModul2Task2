package uz.pdp.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lombok.SneakyThrows;
import uz.pdp.entity.Attachment;
import uz.pdp.entity.AttachmentContent;
import uz.pdp.payload.Result;
import uz.pdp.repository.AttachmentContentRepository;
import uz.pdp.repository.AttachmentRepository;

@Service
public class AttachmentService {

	@Autowired
	AttachmentRepository attachmentRepository;
	
	@Autowired
	AttachmentContentRepository attachmentContentRepository;
	
	@SneakyThrows
	public Result uploadFile(MultipartHttpServletRequest request) {
		Iterator<String> fileNames = request.getFileNames();
		boolean save = false;
		while (fileNames.hasNext()) {
			MultipartFile file = request.getFile(fileNames.next());
			if (file != null) {
				Attachment attachment = new Attachment(file.getOriginalFilename(), file.getSize(),
						file.getContentType());
				Attachment attachment2 = attachmentRepository.save(attachment);

				AttachmentContent attachmentContent = new AttachmentContent(file.getBytes(), attachment2);
				attachmentContentRepository.save(attachmentContent);
				save = true;
			}
		}
		if (save)
			return new Result("Saccussfuly saved", true);

		return new Result("Not Saved", false);
	}
	
	public List<Attachment> getAlResult() {
		return attachmentRepository.findAll();
	}
	public Result getAttachment(Integer id) {
		Optional<Attachment> aOptional = attachmentRepository.findById(id);
		if(aOptional.isPresent()) {
			return new Result("Attachment exist", true, aOptional.get());
		}
		return new Result("Attachment not found", false);
	}
	@SneakyThrows
	public Result editAttachment(Integer id, MultipartHttpServletRequest request) {
		Optional<Attachment> aOptional = attachmentRepository.findById(id);
		Optional<AttachmentContent> cOptional = attachmentContentRepository.findById(id);
		if(!cOptional.isPresent())
			return new Result("AttachmentContent not found", false);
		if(!aOptional.isPresent())
			return new Result("Attachment not found", false);
		Iterator<String> fileNames = request.getFileNames();
		MultipartFile file = request.getFile(fileNames.next());
		Attachment attachment = aOptional.get();
		AttachmentContent attachmentContent = cOptional.get();
		if(file!=null) {
			attachment.setFileOriginalName(file.getOriginalFilename());
			attachment.setSize(file.getSize());
			attachment.setContenType(file.getContentType());
			Attachment attachment2 = attachmentRepository.save(attachment);
			attachmentContent.setBytes(file.getBytes());
			attachmentContent.setAttachment(attachment2);
			attachmentContentRepository.save(attachmentContent);
			
			return new Result("Attachment edited", true);
		}
		return new Result("File is null", false);

	}
	@SneakyThrows
	public Result downloadFile(Integer id, HttpServletResponse response) {
		Optional<Attachment> aOptional = attachmentRepository.findById(id);
		if(aOptional.isPresent()) {
			Attachment attachment = aOptional.get();
			
			Optional<AttachmentContent> cOptional = attachmentContentRepository.findById(id);
			if(cOptional.isPresent()) {
				AttachmentContent attachmentContent = cOptional.get();
				
				response.setHeader("Content-Disposition", "attachment; filename=\""+attachment.getFileOriginalName()+"\"");
				response.setContentType(attachment.getContenType());
				
				FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());
			}
		}
		return new Result("Attachment not found", false);
	}
	public Result deleteAtaResult(Integer id) {
		Optional< Attachment> aOptional = attachmentRepository.findById(id);
		if(aOptional.isPresent()) {
			attachmentRepository.deletAllByAttachmentId(id);
			return new Result("Attachment successfuly deleted", true);
		}
	 return new Result("Attachment not found", false);

	}
}
