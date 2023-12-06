package com.swagger.swager.repository;

import com.swagger.swager.decorator.getUserBySearchandField;
import com.swagger.swager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCriteriaRepositoryImpl implements UserCriteriaRepository{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoOperations mongoOperations;


    @Override
    public List<User> getUserByUsernameStartsWith(String c) {
        Criteria criteria = new Criteria();
        criteria.orOperator(
                Criteria.where("fname").regex("^"+c).andOperator(Criteria.where("lname").regex("^"+c)),
                Criteria.where("city").regex("^"+c).orOperator(Criteria.where("city").regex(c+"$"))
                );
        Query query = new Query();

        query.addCriteria(criteria);

        return mongoTemplate.find(query, User.class);
    }

    @Override
    public Page<User> getUserByAgedBetween(String start_age, String end_age, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Query query = new Query().with(pageable);
        query.addCriteria(Criteria.where("age").gte(start_age).lte(end_age));
        query.with(Sort.by(Sort.Direction.ASC, "age"));
        List<User> res = mongoTemplate.find(query, User.class);

        Page<User> result = new PageImpl<>(res, pageable, mongoOperations.count(query, User.class));

        return result;
    }

    @Override
    public List<User> getUserSortedByAge() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.ASC, "age"));
        return mongoTemplate.find(query, User.class);
    }

    @Override
    public List<User> getUserBySoftDeleted() {
        Query query = new Query();
        query.addCriteria(Criteria.where("softDelete").is(true));
        return mongoTemplate.find(query, User.class);
    }

    @Override
    public List<User> getUserSelectedFields() {
        Query query = new Query();
        query.fields().include("fname").include("lname").include("email");
        return mongoTemplate.find(query,User.class);
    }

    @Override
    public List<User> getUseronSearchCriteria(String char1, String char2) {
        Query query = new Query();


        if (char1 == null && char2 == null) {
            return null;
        }else{
            if(char1 == null){
                query.addCriteria(Criteria.where("city").regex("^"+char2));
                return mongoTemplate.find(query, User.class);

            }
            if(char2 == null) {
                Criteria criteria = new Criteria();
                criteria.orOperator(
                        Criteria.where("fname").regex("^"+char1),
                        Criteria.where("lname").regex("^"+char1)
                );
                query.addCriteria(criteria);
                return mongoTemplate.find(query, User.class);

            }
        }


        Criteria criteria = new Criteria();




        criteria.andOperator(
                new Criteria().orOperator(
                        Criteria.where("fname").regex("/.*"+char1+".*/i"),
                        Criteria.where("lname").regex("^"+char1)
                ),
                Criteria.where("city").regex("^"+char2)
        );

        query.addCriteria(criteria);
        return mongoTemplate.find(query, User.class);

    }

    @Override
    public User getUserByEmail(String email) {
        Query query = new Query();

        query.addCriteria(Criteria.where("email").is(email).and("softDelete").ne(true));

        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public List<User> getUserByGrade(String grade) {
        Query query = new Query();

        query.addCriteria(Criteria.where("grades").in(grade));


        return mongoTemplate.find(query,User.class);
    }

    @Override
    public List<User> getUserOrderByFname(String order, String field) {
        Criteria criteria = new Criteria();
        Query query = new Query();
        criteria.andOperator(
                Criteria.where("fname").regex("^([a-z])*$","i"),
                Criteria.where("softDelete").is(false)
        );
        query.addCriteria(criteria)
                .with(Sort.by(Sort.Direction.valueOf(order), field));
//        query.with(new Sort.Order(Sort.Direction.ASC, "fname").ignoreCase());
        return mongoTemplate.find(query,User.class);
    }

    @Override
    public List<User> getUserBySearchInObject(String str){

        Criteria criteria = new Criteria();
        criteria.andOperator(
                Criteria.where("softDelete").is(false),
                new Criteria().orOperator(
                        Criteria.where("fname").regex(".*" + str + ".*", "i"),
                        Criteria.where("lname").regex(".*" + str + ".*", "i"),
                        Criteria.where("city").regex(".*" + str + ".*", "i"),
                        Criteria.where("contact").regex(".*" + str + ".*", "i"),
                        Criteria.where("email").regex(".*" + str + ".*", "i")

                ));
        Query query = new Query();
        query.addCriteria(criteria);
        return mongoTemplate.find(query, User.class);
    }

    @Override
    public Page<User> getUserBySearchAndSort(getUserBySearchandField data) {
        Pageable pageable = PageRequest.of(data.getPage(), data.getSize());
        Criteria criteria = new Criteria();
        criteria.andOperator(
                Criteria.where("softDelete").is(false),
                new Criteria().orOperator(
                        Criteria.where("fname").regex(".*" + data.getSearch() + ".*", "i"),
                        Criteria.where("lname").regex(".*" + data.getSearch() + ".*", "i"),
                        Criteria.where("city").regex(".*" + data.getSearch() + ".*", "i"),
                        Criteria.where("contact").regex(".*" + data.getSearch() + ".*", "i"),
                        Criteria.where("email").regex(".*" + data.getSearch() + ".*", "i")

                ));
        Query query = new Query().with(pageable);
        query.addCriteria(criteria).with(Sort.by(Sort.Direction.valueOf(data.getOrder()), data.getField()));

        List<User> res = mongoTemplate.find(query, User.class);

//        Page<User> result =


        return new PageImpl<>(res, pageable, mongoOperations.count(query, User.class));
    }


}
