package com.example.healthy_first_ver1.service.Impl;

import com.example.healthy_first_ver1.api.form.UserForm;
import com.example.healthy_first_ver1.entity.Candidate;
import com.example.healthy_first_ver1.entity.Company;
import com.example.healthy_first_ver1.entity.Role;
import com.example.healthy_first_ver1.entity.User;
import com.example.healthy_first_ver1.repository.CandidateRepository;
import com.example.healthy_first_ver1.repository.CompanyRepository;
import com.example.healthy_first_ver1.repository.RoleRepository;
import com.example.healthy_first_ver1.repository.UserRepository;
import com.example.healthy_first_ver1.repository.result.UserResult;
import com.example.healthy_first_ver1.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResult getUserByLogin(String username, String password) {
        UserResult user = userRepository.getUserByLogin(username, password);
        return user;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User addNewCandidate(User user) {
        Candidate candidate = new Candidate();
        candidate.setUsername(user.getUsername());

        User new_user = new User();
        new_user.setPassword(passwordEncoder.encode(user.getPassword()));
        new_user.setUsername(user.getUsername());
        new_user.setId(user.getId());

        candidateRepository.save(candidate);
        return saveUser(user);
    }

    @Override
    public User addNewCompany(User user) {
        Company company = new Company();
        company.setUsername(user.getUsername());

        User new_user = new User();
        new_user.setPassword(passwordEncoder.encode(user.getPassword()));
        new_user.setUsername(user.getUsername());
        new_user.setId(user.getId());

        companyRepository.save(company);
        return saveUser(user);
    }

    @Override
    public User addNewAdmin(User user) {
        User new_user = new User();
        new_user.setPassword(passwordEncoder.encode(user.getPassword()));
        new_user.setUsername(user.getUsername());
        return userRepository.save(new_user);
    }

    @Override
    public User saveUser(User user) {
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public User getUserByName(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("Not found user");
        } else {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
    }
}
