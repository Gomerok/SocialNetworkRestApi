package by.lwo.ukis.repository;

import by.lwo.ukis.model.UserMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersMessagesRepository extends JpaRepository<UserMessages, Long>, PagingAndSortingRepository<UserMessages, Long> {

    @Query("SELECT m FROM UserMessages m WHERE (m.user.id=:senderId AND m.recipientId=:recipientId) OR (m.user.id=:recipientId AND m.recipientId=:senderId) ORDER BY m.created")
    Page<UserMessages> findAllBySenderAndRecipientIdPagination(@Param("senderId") Long senderId, @Param("recipientId") Long recipientId, Pageable pageable);
}
