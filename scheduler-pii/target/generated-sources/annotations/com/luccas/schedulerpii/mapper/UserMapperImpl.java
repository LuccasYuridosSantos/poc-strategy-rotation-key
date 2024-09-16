package com.luccas.schedulerpii.mapper;

import com.luccas.schedulerpii.model.dto.User;
import com.luccas.schedulerpii.model.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-12T17:24:25-0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 17.0.7 (Azul Systems, Inc.)"
)
@Component
public class UserMapperImpl extends UserMapper {

    @Override
    public List<UserDTO> mapToUserDTO(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( users.size() );
        for ( User user : users ) {
            list.add( mapToUserPII( user ) );
        }

        return list;
    }

    @Override
    public List<User> mapToUser(List<UserDTO> users) {
        if ( users == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( users.size() );
        for ( UserDTO userDTO : users ) {
            list.add( mapToUserPII( userDTO ) );
        }

        return list;
    }

    @Override
    public UserDTO mapToUserPII(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setFirstname( encryptData( user.getFirstname() ) );
        userDTO.setLastname( encryptData( user.getLastname() ) );
        userDTO.setEmail( encryptData( user.getEmail() ) );
        userDTO.setCellphone( encryptData( user.getCellphone() ) );
        userDTO.setCity( encryptData( user.getCity() ) );
        userDTO.setZipcode( encryptData( user.getZipcode() ) );
        userDTO.setId( user.getId() );
        userDTO.setUserId( user.getUserId() );
        userDTO.setCountry( user.getCountry() );

        userDTO.setEncrypted( mapToEncryptedData() );

        return userDTO;
    }

    @Override
    public User mapToUserPII(UserDTO user) {
        if ( user == null ) {
            return null;
        }

        User user1 = new User();

        user1.setId( user.getId() );
        user1.setUserId( user.getUserId() );
        user1.setCountry( user.getCountry() );
        user1.setEncrypted( user.getEncrypted() );

        user1.setFirstname( decryptData(user.getFirstname(), user.getEncrypted()) );
        user1.setLastname( decryptData(user.getLastname(), user.getEncrypted()) );
        user1.setEmail( decryptData(user.getEmail(), user.getEncrypted()) );
        user1.setCellphone( decryptData(user.getCellphone(), user.getEncrypted()) );
        user1.setCity( decryptData(user.getCity(), user.getEncrypted()) );
        user1.setZipcode( decryptData(user.getZipcode(), user.getEncrypted()) );

        return user1;
    }
}
