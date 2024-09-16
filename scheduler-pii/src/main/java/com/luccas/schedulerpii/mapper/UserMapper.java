package com.luccas.schedulerpii.mapper;


import com.luccas.schedulerpii.model.dto.EncryptedData;
import com.luccas.schedulerpii.model.dto.User;
import com.luccas.schedulerpii.model.dto.UserDTO;
import com.luccas.schedulerpii.service.EncryptionService;
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

    public abstract List<UserDTO> mapToUserDTO(List<User> users);
    public abstract List<User> mapToUser(List<UserDTO> users);

    @Mapping(source = "firstname", target = "firstname", qualifiedByName = "encryptData")
    @Mapping(source = "lastname", target = "lastname", qualifiedByName = "encryptData")
    @Mapping(source = "email", target = "email", qualifiedByName = "encryptData")
    @Mapping(source = "cellphone", target = "cellphone", qualifiedByName = "encryptData")
    @Mapping(source = "city", target = "city", qualifiedByName = "encryptData")
    @Mapping(source = "zipcode", target = "zipcode", qualifiedByName = "encryptData")
    @Mapping(target = "encrypted", expression = "java(mapToEncryptedData())")
    public abstract UserDTO mapToUserPII(User user);

    @Mapping(target = "firstname", expression = "java(decryptData(user.getFirstname(), user.getEncrypted()))")
    @Mapping(target = "lastname", expression = "java(decryptData(user.getLastname(), user.getEncrypted()))")
    @Mapping(target = "email", expression = "java(decryptData(user.getEmail(), user.getEncrypted()))")
    @Mapping(target = "cellphone", expression = "java(decryptData(user.getCellphone(), user.getEncrypted()))")
    @Mapping(target = "city", expression = "java(decryptData(user.getCity(), user.getEncrypted()))")
    @Mapping(target = "zipcode", expression = "java(decryptData(user.getZipcode(), user.getEncrypted()))")
    public abstract User mapToUserPII(UserDTO user);



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
