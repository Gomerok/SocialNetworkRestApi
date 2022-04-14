package by.lwo.ukis.restControllers;

import by.lwo.ukis.model.User;
import by.lwo.ukis.repository.UserRepository;
import com.oracle.webservices.internal.api.message.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/api/v1/users")
public class FileLoadUploadRestController {

    private final UserRepository userRepository;

    private static final String DIR_TO_UPLOAD = "D:\\";

    public FileLoadUploadRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/upload/file/database")
    public String uploadToDatabase(Authentication authentication, @RequestParam MultipartFile file)
            throws IOException {

        // Set the form data into entity
        User user = userRepository.findByUsername(authentication.getName());
        user.setPhoto(file.getBytes());

        // Save the records into the database
        userRepository.save(user);

        return "Records saved into database.";
    }

    @PostMapping("/upload/file/directory")
    public String uploadToDirectory(@RequestParam MultipartFile file) throws IOException {

        byte[] bytes = file.getBytes();
        Path path = Paths.get(DIR_TO_UPLOAD + file.getOriginalFilename());
        Files.write(path, bytes);

        return "File uploaded";
    }

//    @GetMapping("{userId}/load/file/database")
//    public ResponseEntity<Object> getUserById(Authentication authentication, @PathVariable(name = "userId") Long userId) {
//        User user = userRepository.findByUsername(authentication.getName());
//
//
//        byte[] bytes = user.getPhoto();
//
//        InputStream inputStream = new ByteArrayInputStream(bytes);
//        MultipartFile filelo = new MultipartFile(inputStream);
//        return new ResponseEntity<Object>(filelo, HttpStatus.OK);
//    }
}