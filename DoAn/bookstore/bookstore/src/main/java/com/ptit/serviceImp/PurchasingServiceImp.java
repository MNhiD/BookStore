package com.ptit.serviceImp;

import com.ptit.model.Purchasing;
import com.ptit.repository.PurchasingDAO;
import com.ptit.service.PurchasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchasingServiceImp implements PurchasingService {

    @Autowired
    PurchasingDAO purchasingDAO;

    @Override
    public int save(Purchasing purchasingOrder) {
        purchasingDAO.save(purchasingOrder);
        return 1;
    }

    @Override
    public int get() {
        int lastId = purchasingDAO.getLastId();
        return lastId;
    }

    @Override
    public List<Purchasing> getAllOrder() {
        return purchasingDAO.getAllOrder();
    }


}
