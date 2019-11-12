package bingo.repository;

import bingo.entity.TUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bingo
 * @title: UserRepository
 * @projectName java-hub
 * @description: TODO
 * @date 2019/11/12  23:35
 */
@Repository
public interface UserRepository extends CrudRepository<TUser,Integer> {
}
