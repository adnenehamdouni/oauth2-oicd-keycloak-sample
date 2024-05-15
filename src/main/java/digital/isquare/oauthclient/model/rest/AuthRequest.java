package digital.isquare.oauthclient.model.rest;

import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AuthRequest implements Serializable {

    private String username;
    private String password;

}
