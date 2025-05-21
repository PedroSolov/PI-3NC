// src/main/java/com/example/crudPI/service/UserService.java
package com.example.crudPI.service;

import com.example.crudPI.controller.CreatUserDto;
import com.example.crudPI.controller.UpdateUserDto;
import com.example.crudPI.entity.User;
import com.example.crudPI.repository.UserRepository;
import org.hibernate.sql.ast.tree.predicate.ExistsPredicate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository; // Use final for injected dependencies

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreatUserDto creatUserDto) {
        // DTO -> ENTITY
        var entity = new User(); // Use o construtor padrão ou um específico sem ID
        entity.setName(creatUserDto.name());
        entity.setEmail(creatUserDto.email());
        entity.setPassword(creatUserDto.password()); // Considere criptografar a senha!
        entity.setCep(creatUserDto.cep());
        entity.setPhone(creatUserDto.phone());
        entity.setCpf(creatUserDto.cpf());
        // As anotações @CreationTimestamp cuidarão disso, remova a configuração manual
        // entity.setCreationTimestamp(Instant.now());
        // entity.setUpdateTimestamp(null); // Não precisa definir como null

        var userSaved = userRepository.save(entity);
        return userSaved.getUserId(); // Retorna o ID gerado pelo JPA/Hibernate
    }
    
    public Optional<User> getUserById(String userId) {

        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void updateUserById(String userId, UpdateUserDto updateUserDto) {
        var id = UUID.fromString(userId);

        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            var user = userEntity.get();

            if (updateUserDto.name() != null) {
                user.setName(updateUserDto.name());
            }

            if (updateUserDto.email() != null) {
                user.setEmail(updateUserDto.email());
            }

            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }

            if (updateUserDto.cep() != null) {
                user.setCep(updateUserDto.cep());
            }

            if (updateUserDto.phone() != null) {
                user.setPhone(updateUserDto.phone());
            }

            userRepository.save(user);
        }
    }

    public void deleteById(String userId) {
        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }
    }
}