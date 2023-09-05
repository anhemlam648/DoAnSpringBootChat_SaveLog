package com.example._VuTrungNghia_SQL.entity;

import com.example._VuTrungNghia_SQL.validator.annotiton.ValidUsername;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.*;


@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username",length = 50,nullable = false,unique = true)
    @NotBlank(message = "Username is required")
    @Size(max = 50,message = "Username must be less than 50 characters")
    @ValidUsername
    private String username;
    @Column(name = "password",length = 250,nullable = false)
    @NotBlank(message = "Password is required")
    private String password;
    @Column(name = "email",length = 50)
    @Size(max = 50, message = "Email must be less than 50 characters")
    private String email;
    @Column(name = "name", length = 50,nullable = false)
    @Size(max = 50,message = "Your name must be less than 50 characters")
    @NotBlank(message = "Your name is required")
    private String name;
    @Column(name = "Ngaysinh", length = 10) // Đảm bảo rằng chiều dài phù hợp với định dạng ngày (vd: "yyyy-MM-dd")
    private String Ngaysinh;
    @Column(name = "Gioitinh", length = 10)
    private String Gioitinh;
    public AuthenticationProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthenticationProvider authProvider) {
        this.authProvider = authProvider;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider")
    private AuthenticationProvider authProvider;

//    @ManyToMany
//    @JoinTable(name = "user_role",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();

    private boolean emailVerified;
    private String emailVerificationCode;

    public String getEmailVerificationCode() {
        return emailVerificationCode;
    }

    public void setEmailVerificationCode(String emailVerificationCode) {
        this.emailVerificationCode = emailVerificationCode;
    }
    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

}
