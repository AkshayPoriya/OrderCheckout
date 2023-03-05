package com.akshay.checkout.repository.impl;

import com.akshay.checkout.Constants.ApplicationConstants;
import com.akshay.checkout.Constants.MongoKeyConstants;
import com.akshay.checkout.Models.CheckoutModel;
import com.akshay.checkout.Models.OrderModel;
import com.akshay.checkout.repository.OrderRepository;
import com.akshay.common.utils.CommonLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommonLogger logger;

    @Override
    public boolean saveOrderDetails(OrderModel orderModel) {
        if(isOrderExist(orderModel)){
            logger.logInfo(String.format("Duplicate order received for order-id %s", orderModel.getOrderId()));
            return false;
        }
        mongoTemplate.save(orderModel);
        return true;
    }

    private boolean isOrderExist(OrderModel orderModel){
        Query query = Query.query(Criteria.where(MongoKeyConstants.ORDER.ORDER_ID_KEY).is(orderModel.getOrderId()));
        return mongoTemplate.exists(query, ApplicationConstants.ORDER_COLLECTION_NAME);
    }
}
