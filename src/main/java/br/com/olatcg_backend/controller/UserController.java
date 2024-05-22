package br.com.olatcg_backend.controller;

import br.com.olatcg_backend.service.UserRegistrationService;
import io.tej.SwaggerCodgen.api.UserApi;
import io.tej.SwaggerCodgen.model.LoginDTO;
import io.tej.SwaggerCodgen.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class UserController implements UserApi {

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Override
    public ResponseEntity<UserDTO> create(LoginDTO loginDTO) {
        var userDTO = userRegistrationService.register(loginDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

}
