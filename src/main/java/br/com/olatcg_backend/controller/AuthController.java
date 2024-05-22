package br.com.olatcg_backend.controller;

import br.com.olatcg_backend.service.UserDetailsServiceImpl;
import io.tej.SwaggerCodgen.api.AuthApi;
import io.tej.SwaggerCodgen.model.LoginDTO;
import io.tej.SwaggerCodgen.model.RefreshTokenDTO;
import io.tej.SwaggerCodgen.model.TokenJwtDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class AuthController implements AuthApi {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public ResponseEntity<TokenJwtDTO> authenticate(LoginDTO authRequestDTO) {
        var authUser = new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword());
        authenticationManager.authenticate(authUser);
        var response = userDetailsService.getToken(authRequestDTO);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TokenJwtDTO> authRefreshToken(RefreshTokenDTO refreshTokenDTO) {
        TokenJwtDTO refreshToken = userDetailsService
                .getRefreshToken(refreshTokenDTO.getRefreshToken());
        return ResponseEntity.ok(refreshToken);
    }
}
