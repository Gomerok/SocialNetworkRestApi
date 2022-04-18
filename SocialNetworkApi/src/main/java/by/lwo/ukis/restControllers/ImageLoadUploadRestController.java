package by.lwo.ukis.restControllers;

import by.lwo.ukis.model.User;
import by.lwo.ukis.model.UserImages;
import by.lwo.ukis.repository.ImageRepository;
import by.lwo.ukis.repository.UserRepository;
import by.lwo.ukis.service.ImageService;
import by.lwo.ukis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class ImageLoadUploadRestController {

    private final ImageService imageService;
    private final UserService userService;

   @Autowired
    public ImageLoadUploadRestController(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
    }


    @PostMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Object> uploadImage(@RequestParam MultipartFile multipartImage,
                                              Authentication authentication) throws Exception {
        try {
            String bearerName = authentication.getName();
            User bearer = userService.findByUsername(bearerName);

            UserImages image = imageService.saveImage(multipartImage, bearer);
            return new ResponseEntity<Object>(HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Object> downloadImage(@PathVariable Long id) {
        try {
            UserImages image = imageService.getImageById(id);

            if (image == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            byte[] imageBytes = image.getContent();
            ByteArrayResource imageRes = new ByteArrayResource(imageBytes);

            return new ResponseEntity<Object>(imageRes, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/image/{id}")
    public ResponseEntity<HttpStatus> deleteUserImage(@PathVariable("id") Long id) {
        try {
            UserImages image = imageService.getImageById(id);
            if (image != null) {
                imageService.deleteImage(image);
                return new ResponseEntity<HttpStatus>(HttpStatus.OK);
            } else {
                return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
        }
    }

}