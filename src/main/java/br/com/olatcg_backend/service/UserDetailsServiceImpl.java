package br.com.olatcg_backend.service;

import br.com.olatcg_backend.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import br.com.olatcg_backend.repository.UserRepository;
import io.tej.SwaggerCodgen.model.LoginDTO;
import io.tej.SwaggerCodgen.model.TokenJwtDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Value("${auth.jwt.token.secret}")
    private String secretKey;

    @Value("${auth.jwt.token.expiration}")
    private Integer tokenExpirationTime;

    @Value("${auth.jwt.refresh-token.expiration}")
    private Integer refreshTokenExpirationTime;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(RuntimeException::new);
    }

    public TokenJwtDTO getToken(LoginDTO loginDTO) {
        var user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(RuntimeException::new);

        var tokenJwtDTO = new TokenJwtDTO();
        tokenJwtDTO.setToken(generateTokenJwt(user, tokenExpirationTime));
        tokenJwtDTO.setRefreshToken(generateTokenJwt(user, refreshTokenExpirationTime));

        return tokenJwtDTO;
    }

    public  String generateTokenJwt(User user, Integer expiration) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(generateExpirationDate(expiration))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("An error occurred in token generation! " +exception.getMessage());
        }
    }

    public String getTokenJWTSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    public TokenJwtDTO getRefreshToken(String refreshToken) {

        var username = getTokenJWTSubject(refreshToken);
        var userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("UnauthorizedException");
        }

        var user = userOpt.get();

        var autentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(autentication);

        var response = new TokenJwtDTO();
        response.setToken(generateTokenJwt(user, tokenExpirationTime));
        response.setRefreshToken(refreshToken);

        return response;
    }

    private Instant generateExpirationDate(Integer expiration) {
        return LocalDateTime.now()
                .plusHours(expiration)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
