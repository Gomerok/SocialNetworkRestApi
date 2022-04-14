package by.lwo.ukis.model;

import by.lwo.ukis.model.enums.MessagesStatus;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users_messages")
@Data
public class UserMessages extends BaseEntity {

    @Column(name = "recipient_id")
    private Long recipientId;

    @Column(name = "value_text")
    private String value;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "messages_status")
    private MessagesStatus messagesStatus;
}