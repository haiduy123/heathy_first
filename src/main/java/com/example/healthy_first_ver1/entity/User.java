package com.example.healthy_first_ver1.entity;
import com.example.healthy_first_ver1.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String position;

    @Column
    private String location;


    public UserDto toDto() {
        return UserDto.builder()
                .id(id)
                .username(username)
                .password(password)
                .position(position)
                .location(location)
                .build();
    }
}
