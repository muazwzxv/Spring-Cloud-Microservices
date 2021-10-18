package com.muazwzxv.userservice.repositories;

import com.muazwzxv.userservice.models.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users, Long> {

}
