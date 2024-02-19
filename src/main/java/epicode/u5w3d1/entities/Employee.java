package epicode.u5w3d1.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "employee")

public class Employee {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue
    private UUID id;
    @Column(name = "profile_img")
    private String profileImg;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;

    public Employee(String profileImg, String username, String name, String surname, String email, String password) {
        this.profileImg = profileImg;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }
}
