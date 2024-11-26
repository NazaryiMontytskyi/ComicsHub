package org.comicshub.comichub.Security;


import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.comicshub.comichub.Models.ImageContent;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private long id;
    private String username;
    private String password;
    private String email;
    private ImageContent avatar;
    private boolean active;
    private LocalDateTime creationTime;
    private Set<Role> roles = new HashSet<>();

    @PrePersist
    private void init(){
        creationTime = LocalDateTime.now();
    }

}
