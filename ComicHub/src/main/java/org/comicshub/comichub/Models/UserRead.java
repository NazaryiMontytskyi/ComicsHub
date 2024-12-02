package org.comicshub.comichub.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.comicshub.comichub.Security.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_read")
public class UserRead {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="comic_id", nullable=false)
    private Comic comic;
}
