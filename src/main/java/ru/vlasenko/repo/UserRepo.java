package ru.vlasenko.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.vlasenko.model.User;

/**
 * Created by andreyvlasenko on 04/12/16.
 */
public interface UserRepo extends MongoRepository<User, String>{
}
