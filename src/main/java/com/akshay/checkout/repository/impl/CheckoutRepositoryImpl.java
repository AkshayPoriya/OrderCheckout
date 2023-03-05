package com.akshay.checkout.repository.impl;

import com.akshay.checkout.Constants.ApplicationConstants;
import com.akshay.checkout.Models.CheckoutModel;
import com.akshay.checkout.repository.CheckoutRepository;
import com.akshay.common.utils.CommonLogger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Objects;

@Repository
public class CheckoutRepositoryImpl implements CheckoutRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommonLogger logger;


    @Override
    public CheckoutModel getCheckoutModel(String checkoutId) {
        Query query = new Query();
        query.addCriteria(
                Criteria.where("checkout_id").is(checkoutId));
        return mongoTemplate.findOne(query, CheckoutModel.class);
    }

    @Override
    public void setCheckoutComplete(String checkoutId) {
        Query query = new Query();
        query.addCriteria(
                Criteria.where("checkout_id").is(checkoutId));
        query.addCriteria(Criteria.where("is_checkout_completed").is(false));
        query.with((Sort.by(Sort.Direction.DESC, "_id")));
        Update update = new Update();
        update.set("is_checkout_completed", true).set("next_notification_time", null).set("updated_at", LocalDateTime.now());
        FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions();
        findAndModifyOptions.returnNew(true);
        Object updateResult = mongoTemplate.findAndModify(query, update, findAndModifyOptions, CheckoutModel.class);
        // check on update result -->
        if (Objects.nonNull(updateResult)) {
            logger.logInfo(String.format("Checkout successfully completed for id: %s", checkoutId));
        } else {
            logger.logInfo(String.format("No checkout object found for id: %s", checkoutId));
        }
    }

    @Override
    public void saveCheckoutObj(CheckoutModel checkoutModel) {
        if(isCheckoutExist(checkoutModel)){
            logger.logInfo(String.format("Duplicate checkout received for checkout-id %s", checkoutModel.getCheckoutId()));
            return;
        }
        mongoTemplate.save(checkoutModel);
    }

    private boolean isCheckoutExist(CheckoutModel checkoutModel){
        Query query = Query.query(Criteria.where("checkout_id").is(checkoutModel.getCheckoutId()));
        return mongoTemplate.exists(query, ApplicationConstants.CHECKOUT_COLLECTION_NAME);
    }
}
