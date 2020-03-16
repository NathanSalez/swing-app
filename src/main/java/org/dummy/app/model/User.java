package org.dummy.app.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @EqualsAndHashCode.Include
    @NonNull
    int id;

    @NonNull
    String login;

    char[] password;

    public User(@NonNull String login, char[] password) {
        this.login = login;
        this.password = password;
    }
}
