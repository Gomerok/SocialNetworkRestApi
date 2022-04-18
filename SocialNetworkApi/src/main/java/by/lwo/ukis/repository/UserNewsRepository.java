package by.lwo.ukis.repository;

import by.lwo.ukis.model.UserMessages;
import by.lwo.ukis.model.UserNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNewsRepository extends JpaRepository<UserNews, Long> {

    @Query("SELECT n FROM UserNews n WHERE n.user.id=:userId")
    Page<UserNews> findAllNewsByUserId(@Param("userId") Long userId, Pageable pageable);
}
