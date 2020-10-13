package at.jku.cis.iVolunteer.core.file;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

	private String folderName = "upload-dir";

	private final Path rootLocation;

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

	private String store(String filename, InputStream inputStream) {
		try {
			if (filename.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory " + filename);
			}
			System.out.println(this.rootLocation);
			if (!Files.exists(this.rootLocation)) {
				Files.createDirectory(this.rootLocation);
			}
			Files.copy(inputStream, this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}
		return filename;
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
