package by.lwo.ukis.model;

import by.lwo.ukis.model.enums.FriendsStatus;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "friends")
@Data
public class Friends extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "friend_id")
    private Long friendId;

    @Enumerated(EnumType.STRING)
    @Column(name = "friend_status")
    private FriendsStatus friendsStatus;

}
