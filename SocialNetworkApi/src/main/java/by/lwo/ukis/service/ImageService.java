package by.lwo.ukis.service;

import by.lwo.ukis.model.User;
import by.lwo.ukis.model.UserImages;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    UserImages saveImage(MultipartFile multipartFile, User user) throws IOException;

    UserImages getImageById(Long id);

    void deleteImage(UserImages image);

}
