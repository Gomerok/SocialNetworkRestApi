package by.lwo.ukis.model;

import by.lwo.ukis.model.enums.MessagesStatus;
import by.lwo.ukis.model.enums.NewsStatus;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_news")
@Data
public class UserNews extends BaseEntity {

    @Column(name = "header")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "news_status")
    private NewsStatus newsStatus;
}