package com.ptit.serviceImp;

import com.ptit.model.*;
import com.ptit.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ptit.service.PurchasingOrderService;

@Service
public class PurchasingOrderServiceImp implements PurchasingOrderService {

    @Autowired
    PurchasingOrderDAO purchasingOrderDAO;


    @Override
    public  int save(PurchasingOrder purchasingOrder){
        purchasingOrderDAO.save(purchasingOrder);
        return 1;
    }

    @Override
    public Page<PurchasingOrder> getAll() {
        return null;
    }
}
