package com.example.healthy_first_ver1;

import com.example.healthy_first_ver1.entity.Role;
import com.example.healthy_first_ver1.entity.User;
import com.example.healthy_first_ver1.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

@SpringBootApplication
public class HealthyFirstVer1Application {

    public static void main(String[] args) {
        SpringApplication.run(HealthyFirstVer1Application.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    CommandLineRunner run(UserService userService) {
//        return args -> {
//            userService.saveRole(new Role(null, "ROLE_USER"));
//            userService.saveRole(new Role(null, "ROLE_MANAGER"));
//            userService.saveRole(new Role(null, "ROLE_ADMIN"));
//            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
//
//            userService.addNewCandidate(new User(null, "john", "1234", new ArrayList<>()));
//            userService.addNewCandidate(new User(null, "will", "1234", new ArrayList<>()));
//            userService.addNewCandidate(new User(null, "jim", "1234", new ArrayList<>()));
//            userService.addNewCandidate(new User(null, "arnold", "1234", new ArrayList<>()));
//
//            userService.addNewCompany(new User(null, "cty1", "1234", new ArrayList<>()));
//            userService.addNewCompany(new User(null, "cty2", "1234", new ArrayList<>()));
//            userService.addNewCompany(new User(null, "cty3", "1234", new ArrayList<>()));
//            userService.addNewCompany(new User(null, "cty4", "1234", new ArrayList<>()));
//
//            userService.addRoleToUser("john", "ROLE_USER");
//            userService.addRoleToUser("will", "ROLE_USER");
//            userService.addRoleToUser("jim", "ROLE_USER");
//            userService.addRoleToUser("arnold", "ROLE_USER");
//
//            userService.addRoleToUser("cty1", "ROLE_MANAGER");
//            userService.addRoleToUser("cty2", "ROLE_MANAGER");
//            userService.addRoleToUser("cty3", "ROLE_MANAGER");
//            userService.addRoleToUser("cty4", "ROLE_MANAGER");
//
//        };
//    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
//        configuration.setExposedHeaders(Arrays.asList("Authorization", "content-type"));
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "content-type"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/facilities").allowedOrigins("http://localhost:8080");
            }
        };
    }
}
