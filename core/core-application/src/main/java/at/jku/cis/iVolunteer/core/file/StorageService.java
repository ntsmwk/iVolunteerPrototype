package at.jku.cis.iVolunteer.core.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.spi.FileTypeDetector;
import java.util.Base64;
import java.util.stream.Stream;

import javax.activation.FileTypeMap;
import javax.activation.MimetypesFileTypeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

	private String folderName = "upload-dir";

	private final Path rootLocation;

	@Value("${spring.data.server.uri}") private String serverUrl;

	@Autowired
	public StorageService() {
		this.rootLocation = Paths.get(folderName);
	}

	public String store(ClassPathResource resource) {
		String filename = StringUtils.cleanPath(resource.getFilename());
		if (!resource.exists()) {
			throw new StorageException("Failed to store empty file " + filename);
		}
		try (InputStream inputStream = resource.getInputStream()) {
			return store(filename, inputStream);
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}
	}

	public String store(MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		if (file.isEmpty()) {
			throw new StorageException("Failed to store empty file " + filename);
		}
		try (InputStream inputStream = file.getInputStream()) {
			return store(filename, inputStream);
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}
	}

	public String store(String filename, String image) {
		try {

			String[] splitImage = StringUtils.split(image, ",");

			if (splitImage.length != 2) {
				throw new IllegalArgumentException("invalid image string");
			}

			String extension = "";
			if (splitImage[0].equals("data:image/gif;base64")) {
				extension = "gif";
			} else if (splitImage[0].equals("data:image/png;base64")) {
				extension = "png";
			} else if (splitImage[0].equals("data:image/jpg;base64") || splitImage[0].equals("data:image/jpeg;base64")) {
				extension = "jpg";
			} else {
				throw new IllegalArgumentException("Unsupported Media Type: " + splitImage[0]
						+ "\nSupported: data:image/png;base64, data:image/png;base64, data:image/jpg;base64, data:image/jpeg;base64");
			}

			byte[] imageByte = Base64.getDecoder().decode(splitImage[1]);

			filename = StringUtils.cleanPath(filename + "." + extension);

			return store(filename, new ByteArrayInputStream(imageByte));
		} catch (Exception e) {
			throw new StorageException("Failed to store file " + filename, e);
		}
	}

	private String store(String filename, InputStream inputStream) {
		try {
			if (filename.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory " + filename);
			}
			if (!Files.exists(this.rootLocation)) {
				Files.createDirectory(this.rootLocation);
			}
			Files.copy(inputStream, this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}
		return this.serverUrl + "/file/" + filename;
	}

	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	public void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}
