package com.ptit.service;

import com.ptit.model.PurchasingOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PurchasingOrderService {


    int save(PurchasingOrder purchasingOrder);

    Page<PurchasingOrder> getAll();

    List<PurchasingOrder> getOrderById(long id);


}
