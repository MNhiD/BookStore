package com.ptit.repository;

import com.ptit.model.PurchasingOrder;
import com.ptit.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasingOrderDAO extends JpaRepository<PurchasingOrder, Long>,JpaSpecificationExecutor<PurchasingOrder>{

    @Query(value = "SELECT * FROM bookstore.purchasing_order", nativeQuery = true)
    List<PurchasingOrder> getAll();

    @Query(value = "SELECT po.id_purchasing_order, po.id_book, b.book_name , po.price , po.quantity, po.id_purchasing\n" +
            " FROM bookstore.purchasing_order po\n" +
            "join bookstore.book b on b.id_book = po.id_book \n" +
            "where id_purchasing = :id_purchasing",nativeQuery = true)
    List<PurchasingOrder> getOrDerById(@Param("id_purchasing") long id_purchasing);
}
