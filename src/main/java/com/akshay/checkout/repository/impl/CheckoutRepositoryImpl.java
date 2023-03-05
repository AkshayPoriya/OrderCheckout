package com.akshay.checkout.repository.impl;

import com.akshay.checkout.Constants.ApplicationConstants;
import com.akshay.checkout.Constants.MongoKeyConstants;
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
import java.util.List;
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
                Criteria.where(MongoKeyConstants.CHECKOUT.CHECKOUT_ID_KEY).is(checkoutId));
        return mongoTemplate.findOne(query, CheckoutModel.class);
    }

    @Override
    public void setCheckoutComplete(String checkoutId) {
        Query query = new Query();
        query.addCriteria(
                Criteria.where(MongoKeyConstants.CHECKOUT.CHECKOUT_ID_KEY).is(checkoutId));
        query.addCriteria(Criteria.where(MongoKeyConstants.CHECKOUT.IS_CHECKOUT_COMPLETE_KEY).is(false));
        query.with((Sort.by(Sort.Direction.DESC, "_id")));
        Update update = new Update();
        update.set(MongoKeyConstants.CHECKOUT.IS_CHECKOUT_COMPLETE_KEY, true)
                .set(MongoKeyConstants.CHECKOUT.NEXT_NOTIFICATION_TIME_KEY, null)
                .set(MongoKeyConstants.CHECKOUT.UPDATED_AT_KEY, LocalDateTime.now());
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

    @Override
    public List<CheckoutModel> getAbandonCheckoutOrdersForNotifications() {
        Query query = new Query();
        query.addCriteria(
                Criteria.where(MongoKeyConstants.CHECKOUT.NEXT_NOTIFICATION_TIME_KEY).lte(LocalDateTime.now()));
        query.addCriteria(Criteria.where(MongoKeyConstants.CHECKOUT.IS_CHECKOUT_COMPLETE_KEY).is(false));
        List<CheckoutModel> checkoutModelList = mongoTemplate.find(query, CheckoutModel.class);
        return checkoutModelList;
    }

    @Override
    public void updateAbandonCheckoutNotificationDetails(CheckoutModel checkoutModel) {
        Query query = new Query();
        query.addCriteria(
                Criteria.where(MongoKeyConstants.CHECKOUT.CHECKOUT_ID_KEY).is(checkoutModel.getCheckoutId()));
        query.with((Sort.by(Sort.Direction.DESC, "_id")));
        Update update = new Update();
        update.set(MongoKeyConstants.CHECKOUT.NEXT_NOTIFICATION_TIME_KEY, checkoutModel.getNextNotificationTime())
                .set(MongoKeyConstants.CHECKOUT.NEXT_NOTIFICATION_INDEX_KEY, checkoutModel.getNextNotificationIndex())
                .set(MongoKeyConstants.CHECKOUT.UPDATED_AT_KEY, LocalDateTime.now());
        FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions();
        findAndModifyOptions.returnNew(true);
        Object updateResult = mongoTemplate.findAndModify(query, update, findAndModifyOptions, CheckoutModel.class);
        logger.logInfo(String.format("Updated checkout notification details for id: %s", checkoutModel.getCheckoutId()));
    }

    private boolean isCheckoutExist(CheckoutModel checkoutModel){
        Query query = Query.query(Criteria.where(MongoKeyConstants.CHECKOUT.CHECKOUT_ID_KEY).is(checkoutModel.getCheckoutId()));
        return mongoTemplate.exists(query, ApplicationConstants.CHECKOUT_COLLECTION_NAME);
    }
}
