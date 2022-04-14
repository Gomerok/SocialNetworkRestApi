package by.lwo.ukis.repository;

import by.lwo.ukis.model.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendsRepository extends JpaRepository<Friends, Long> {

    @Query("SELECT f FROM Friends f WHERE f.friendId=: userId or f.user.id=:userId ")
    List<Friends> getAllFriendsByUserId(@Param("userId") Long userId);

    @Query("SELECT f FROM Friends f WHERE (f.friendId=: userId and f.user.id=:friendId) or (f.friendId=: friendId and f.user.id=:userId) ")
    List<Friends> getFriendsByUserAndFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);
}
