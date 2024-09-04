package com.luccas.poc_rotation_key.mapper;

import com.luccas.poc_rotation_key.model.UserPII;
import com.luccas.poc_rotation_key.model.UserPIIInsertRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserPII mapToUserPII(UserPIIInsertRequest user) ;
}
