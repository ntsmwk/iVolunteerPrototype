package at.jku.cis.iVolunteer.core.file;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import at.jku.cis.iVolunteer.model._httprequests.Base64ImageUploadRequest;
import at.jku.cis.iVolunteer.model._httpresponses.StringResponse;

@RestController
public class FileController {

	@Autowired private StorageService storageService;

	@GetMapping(value = "/file/{filename:.+}", produces = "image/*")
	public ResponseEntity<byte[]> serveFile(@PathVariable String filename) throws IOException {
		Resource file = storageService.loadAsResource(filename);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(IOUtils.toByteArray(file.getInputStream()));
	}

	@PostMapping(value = "/file", produces = "application/json; charset=utf-8")
	public ResponseEntity<StringResponse> handleFileUpload(@RequestParam("file") MultipartFile file) {
		try {
			String fileUrl = storageService.store(file);
			return ResponseEntity.ok(new StringResponse(fileUrl));
		} catch (StorageException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping("/file/image-base64")
	public ResponseEntity<Object> handleBase64ImageUpload(@RequestBody Base64ImageUploadRequest body) {
		try {
			if (body.getFilename() == null || body.getFilename() == "") {
				body.setFilename(UUID.randomUUID().toString());
			}
			String fileUrl = storageService.store(body.getFilename(), body.getImage());
			return ResponseEntity.ok(new StringResponse(fileUrl));
		} catch (StorageException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
