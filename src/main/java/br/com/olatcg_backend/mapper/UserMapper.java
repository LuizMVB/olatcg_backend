package br.com.olatcg_backend.mapper;

import br.com.olatcg_backend.entity.User;
import io.tej.SwaggerCodgen.model.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO entityToDto(User newUser);
}
