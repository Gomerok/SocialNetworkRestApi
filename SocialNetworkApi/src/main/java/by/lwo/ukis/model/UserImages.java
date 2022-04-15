package by.lwo.ukis.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_image")
@Data
public class UserImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Lob
    private byte[] content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
