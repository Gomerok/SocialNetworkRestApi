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

//    @Query("SELECT u FROM User u, Friends f WHERE (u.id=f.friendId OR u.id=f.user.id) AND u.id=:userId")
    @Query("SELECT DISTINCT u FROM User u  Inner JOIN u.friends f1 ON f1.user.id = u.id And u.id <>:userId ")
    Page<User> getAllFriendsByUserId(@Param("userId") Long userId, Pageable pageable);

}
