package com.swagger.swager.repository;
import com.swagger.swager.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {


    User findByEmailContainingAndSoftDeleteIsFalse(String email);


    User findByIdAndSoftDeleteIsFalse(String id);

    Page<User> findBySoftDeleteIsFalse(Pageable pageable);

    List<User> findBySoftDeleteIsFalseOrderByAgeAsc();

//    List<User> findBySoftDeleteIsFalseAndGradesIn(String grade);

//    User findOneByEmailIgnoreCaseAndPasswordAndSoftDeleteIsFalse(String email, String password);


    List<User> findBySoftDeleteIsFalseAndFnameIgnoreCaseOrderByFnameAsc(String fname );


    List<User> findBySoftDeleteIsFalseAndLnameIgnoreCaseOrderByLnameAsc( String lname );

    List<User> findBySoftDeleteIsFalseOrderByEmailAsc( );
}
