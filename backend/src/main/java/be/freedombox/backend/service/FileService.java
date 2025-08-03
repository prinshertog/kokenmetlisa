package be.freedombox.backend.service;

import be.freedombox.backend.exception.FileException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    private final Path fileLocation = Paths.get("Files").toAbsolutePath();
    public void saveFile(MultipartFile multipartFile, String fileName) {
        try {
            File directory = new File(fileLocation.toUri());
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (created) {
                    System.out.println("Files folder was created because it did not already exist.");
                } else {
                    System.out.println("Files folder was not created, something went wrong.");
                }
            }
            File file = new File(fileLocation + "/" + fileName);
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write(multipartFile.getBytes());
                System.out.println("File was created at " + fileLocation);
            } catch (Exception e) {
                System.out.println("File was not created" + e.getMessage());
            }

        } catch (Exception e) {
            throw new FileException(e.getMessage());
        }
    }

    public void deleteFile(String fileName) {
        File file = new File(fileLocation + "/" + fileName);
        boolean success = file.delete();
        if (!success) throw new FileException("File could not be deleted");
        else System.out.println("File was deleted at " + fileLocation);
    }

    public byte[] getFile(String fileName) throws IOException {
        Path file = Paths.get(fileLocation + "/" + fileName);
        return Files.readAllBytes(file);
    }
}
