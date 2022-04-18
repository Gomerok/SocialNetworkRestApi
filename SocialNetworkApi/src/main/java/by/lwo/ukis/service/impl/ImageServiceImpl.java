package by.lwo.ukis.service.impl;

import by.lwo.ukis.model.User;
import by.lwo.ukis.model.UserImages;
import by.lwo.ukis.repository.ImageRepository;
import by.lwo.ukis.repository.UserRepository;
import by.lwo.ukis.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    public ImageServiceImpl(UserRepository userRepository, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public UserImages saveImage(MultipartFile multipartFile, User user) throws IOException {

        UserImages savedImage = new UserImages();
        savedImage.setName(multipartFile.getOriginalFilename());
        savedImage.setContent(multipartFile.getBytes());
        savedImage.setUser(user);

        imageRepository.save(savedImage);

        log.info("IN saveImage - image: {} successfully saved", savedImage.getName());
        return savedImage;
    }

    @Override
    public UserImages getImageById(Long imageId) {
        UserImages image = imageRepository.findById(imageId).orElse(null);
        if (image == null) {
            log.info("IN getImageById - image not found by id {}", imageId);
            return null;
        }
        log.info("IN getImageById - image with id {} found", imageId);
        return image;
    }

    @Override
    public void deleteImage(UserImages image, User bearer) {
        imageRepository.deleteImage(image.getId());
        log.info("IN deleteImage - image with id: {} successfully deleted", image.getId());
    }

}
