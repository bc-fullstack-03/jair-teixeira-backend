package com.sysmap.parrot.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sysmap.parrot.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends MongoRepository<User, UUID> {

	Optional<User> findFirstUserByEmail(String email);

}
