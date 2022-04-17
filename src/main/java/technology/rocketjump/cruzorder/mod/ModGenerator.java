package technology.rocketjump.cruzorder.mod;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class ModGenerator {

	public List<ModFileGenerator> fileGeneratorList = new ArrayList<>();

	@Autowired
	public ModGenerator() {

	}

	public byte[] generateMod() throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
		ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

		for (ModFileGenerator generator : fileGeneratorList) {
			byte[] contentBytes = generator.getFileContents().getBytes();
			zipOutputStream.putNextEntry(new ZipEntry(generator.getFilename()));
			zipOutputStream.write(contentBytes, 0, contentBytes.length);
		}

		zipOutputStream.finish();
		zipOutputStream.flush();
		IOUtils.closeQuietly(zipOutputStream);
		IOUtils.closeQuietly(bufferedOutputStream);
		IOUtils.closeQuietly(byteArrayOutputStream);
		return byteArrayOutputStream.toByteArray();
	}
}
