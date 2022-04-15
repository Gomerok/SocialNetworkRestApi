package by.lwo.ukis.repository;

import by.lwo.ukis.model.UserImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<UserImages, Long> {
}
