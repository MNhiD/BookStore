package com.ptit.repository;

import com.ptit.model.PurchasingOrder;
import com.ptit.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasingOrderDAO extends JpaRepository<PurchasingOrder, Long>,JpaSpecificationExecutor<PurchasingOrder>{

    @Query(value = "SELECT * FROM bookstore.purchasing_order", nativeQuery = true)
    List<PurchasingOrder> getAll();


}
