package com.ptit.service;

import com.ptit.model.Purchasing;

import java.util.List;

public interface PurchasingService {
    int save(Purchasing  purchasingOrder);

    int get();
    List<Purchasing> getAllOrder();
}
