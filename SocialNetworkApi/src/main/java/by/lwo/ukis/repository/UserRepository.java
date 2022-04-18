package by.lwo.ukis.repository;

import by.lwo.ukis.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {
    User findByUsername(String name);

    @Query("SELECT u FROM User as u WHERE u.firstName LIKE %:param% or u.lastName LIKE %:param% ORDER BY u.id")
    Page<User> findAllWhitSearchParamPagination(@Param("param") String param, Pageable pageable);

//    @Query("SELECT u FROM User u, Friends f WHERE (u.id=f.friendId OR u.id=f.user.id) AND u.id=:userId")
//    @Query("SELECT DISTINCT u FROM User u  Inner JOIN u.friends f1 ON f1.user.id = u.id And u.id <>:userId ")
//    @Query("SELECT u FROM User u inner JOIN u.friends f ON (f.user.id =: userId OR f.friendId =: userId) WHERE u.id<>:userId")

//    @Query("SELECT u FROM User u, Friends f WHERE (u.id=f.friendId OR u.id=f.user.id) AND (f.friendId=:userId OR f.user.id=:userId) AND u.id<>:userId")

//    @Query(value = "SELECT u FROM User u INNER JOIN Friends f ON ( u.id = f.user.id OR u.id = f.friendId) WHERE u.id <> :userId AND (f.user.id = 2 OR f.friendId= :userId)",nativeQuery=true)
//    Page<User> getAllFriendsByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query(value = " SELECT u.id, u.username, u.first_name, u.last_name, u.email FROM USERS u INNER JOIN FRIENDS F ON ( u.id = f.user_id  or u.id = f.friend_id)  WHERE u.id <>:userId AND (f.user_id =:userId OR f.friend_id=:userId)",nativeQuery=true)
    List<Object[]> getAllFriendsByUserId(@Param("userId") Long userId);

}
