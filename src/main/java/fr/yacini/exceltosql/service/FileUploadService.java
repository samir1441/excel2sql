package fr.yacini.exceltosql.service;

import java.io.IOException;
import java.io.InputStream;

import org.primefaces.event.FileUploadEvent;
import org.springframework.stereotype.Service;

import lombok.Data;

/**
 * @author Samir
 *
 */
@Service
final @Data public class FileUploadService {

	/**
	 *
	 * @param event
	 * @return
	 * @throws IOException
	 */
	public InputStream handleFileUpload(final FileUploadEvent event) throws IOException {
		return event.getFile().getInputstream();
	}

}
