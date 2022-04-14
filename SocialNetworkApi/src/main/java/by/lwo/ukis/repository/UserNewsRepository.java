package by.lwo.ukis.repository;

import by.lwo.ukis.model.UserNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNewsRepository extends JpaRepository<UserNews, Long> {
}
