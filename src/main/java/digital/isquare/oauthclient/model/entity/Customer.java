package digital.isquare.oauthclient.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String serviceRendered;
    private String address;
    private String phone;
    @Column(unique = true)
    private String email;
    private String position;
    @Column(length = 1000)
    private String bio;

}
