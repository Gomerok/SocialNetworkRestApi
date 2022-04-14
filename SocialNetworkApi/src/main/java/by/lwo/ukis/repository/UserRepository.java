package by.lwo.ukis.repository;

import by.lwo.ukis.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {
    User findByUsername(String name);

    @Query("SELECT u FROM User as u WHERE u.firstName LIKE %:param% or u.lastName LIKE %:param% ORDER BY u.id")
    Page<User> findAllWhitSearchParamPagination(@Param("param") String param, Pageable pageable);

}
