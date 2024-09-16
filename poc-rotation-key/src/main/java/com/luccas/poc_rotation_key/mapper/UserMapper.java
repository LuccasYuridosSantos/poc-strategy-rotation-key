package com.luccas.poc_rotation_key.mapper;

import com.luccas.poc_rotation_key.model.EncryptedData;
import com.luccas.poc_rotation_key.model.UserDTO;
import com.luccas.poc_rotation_key.model.UserRequest;
import com.luccas.poc_rotation_key.model.UserResponse;
import com.luccas.poc_rotation_key.service.EncryptionService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {

    @Autowired
    private EncryptionService encryptionService;

    public abstract List<UserResponse> mapToUser(List<UserDTO> users);
    public abstract List<UserDTO> mapToUserDTO(List<UserResponse> users);

    @Mapping(source = "firstname", target = "firstname", qualifiedByName = "encryptData")
    @Mapping(source = "lastname", target = "lastname", qualifiedByName = "encryptData")
    @Mapping(source = "email", target = "email", qualifiedByName = "encryptData")
    @Mapping(source = "cellphone", target = "cellphone", qualifiedByName = "encryptData")
    @Mapping(source = "city", target = "city", qualifiedByName = "encryptData")
    @Mapping(source = "zipcode", target = "zipcode", qualifiedByName = "encryptData")
    @Mapping(target = "encrypted", expression = "java(mapToEncryptedData())")
    public abstract UserDTO mapToUser(UserRequest user);

    @Mapping(target = "firstname", expression = "java(decryptData(userDTO.getFirstname(), userDTO.getEncrypted()))")
    @Mapping(target = "lastname", expression = "java(decryptData(userDTO.getLastname(), userDTO.getEncrypted()))")
    @Mapping(target = "email", expression = "java(decryptData(userDTO.getEmail(), userDTO.getEncrypted()))")
    @Mapping(target = "cellphone", expression = "java(decryptData(userDTO.getCellphone(), userDTO.getEncrypted()))")
    @Mapping(target = "city", expression = "java(decryptData(userDTO.getCity(), userDTO.getEncrypted()))")
    @Mapping(target = "zipcode", expression = "java(decryptData(userDTO.getZipcode(), userDTO.getEncrypted()))")
    public abstract UserResponse mapToUser(UserDTO userDTO);

    @Mapping(source = "firstname", target = "firstname", qualifiedByName = "encryptData")
    @Mapping(source = "lastname", target = "lastname", qualifiedByName = "encryptData")
    @Mapping(source = "email", target = "email", qualifiedByName = "encryptData")
    @Mapping(source = "cellphone", target = "cellphone", qualifiedByName = "encryptData")
    @Mapping(source = "city", target = "city", qualifiedByName = "encryptData")
    @Mapping(source = "zipcode", target = "zipcode", qualifiedByName = "encryptData")
    @Mapping(target = "encrypted", expression = "java(mapToEncryptedData())")
    public abstract UserDTO mapToUserDTO(UserResponse user);

    @Named("encryptData")
    protected String encryptData(String data) {
        try {
            return encryptionService.encrypt(data);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Named("decryptData")
    protected String decryptData(String encryptedData, EncryptedData encrypted){
        try{
            return encryptionService.specificDecrypt(encryptedData, encrypted.getVersion());
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Named("mapToEncryptedData")
    protected EncryptedData mapToEncryptedData() {
        return new EncryptedData(this.encryptionService.getCurrentKeyVersion(), Instant.now().toEpochMilli());
    }
}
