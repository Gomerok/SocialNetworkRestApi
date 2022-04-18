package by.lwo.ukis.repository;

import by.lwo.ukis.model.UserImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<UserImages, Long> {

    @Modifying
    @Query("DELETE FROM UserImages i where i.id=:id")
    void deleteImage(@Param("id") Long id);
}
