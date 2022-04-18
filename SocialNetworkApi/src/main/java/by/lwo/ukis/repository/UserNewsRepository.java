package by.lwo.ukis.repository;

import by.lwo.ukis.model.UserMessages;
import by.lwo.ukis.model.UserNews;
import by.lwo.ukis.model.enums.NewsStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNewsRepository extends JpaRepository<UserNews, Long> {

    NewsStatus status = by.lwo.ukis.model.enums.NewsStatus.CREATED;

    @Query("SELECT n FROM UserNews n WHERE n.user.id=:userId AND n.newsStatus =:newsStatus")
    Page<UserNews> findAllNewsByUserId(@Param("userId") Long userId, Pageable pageable,@Param("newsStatus") NewsStatus newsStatus );

    @Modifying
    @Query("DELETE FROM UserNews n where n.id=:id")
    void deleteNews(@Param("id") Long id);
}
