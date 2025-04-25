// src/main/java/com/example/crudPI/service/UserService.java
package com.example.crudPI.service;

import com.example.crudPI.controller.CreatUserDto;
import com.example.crudPI.entity.User;
import com.example.crudPI.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository; // Use final for injected dependencies

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreatUserDto creatUserDto) {
        // DTO -> ENTITY
        // REMOVA a geração manual de UUID daqui
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
}