package br.com.olatcg_backend.service;

import br.com.olatcg_backend.entity.User;
import br.com.olatcg_backend.mapper.UserMapper;
import br.com.olatcg_backend.repository.UserRepository;
import io.tej.SwaggerCodgen.model.LoginDTO;
import io.tej.SwaggerCodgen.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    public UserDTO register(LoginDTO loginDTO) {
        var hasUser = userRepository
                .findByUsername(loginDTO.getUsername())
                .isPresent();

        if(hasUser) {
            throw new RuntimeException("User already exists");
        }

        var passwordHash = passwordEncoder.encode(loginDTO.getPassword());
        var entity = new User(loginDTO.getUsername(), passwordHash);
        var newUser = userRepository.save(entity);

        return userMapper.entityToDto(newUser);
    }
}
