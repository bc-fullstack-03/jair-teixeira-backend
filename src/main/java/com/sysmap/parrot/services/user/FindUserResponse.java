package com.sysmap.parrot.services.user;

import java.util.UUID;

import com.sysmap.parrot.services.enumeration.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FindUserResponse {
	private UUID id;
    private String name;
    private String email;
    private String photoUri;
}
