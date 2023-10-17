package com.ptit.repository;

import com.ptit.model.Purchasing;
import com.ptit.model.PurchasingOrder;
import com.ptit.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchasingDAO extends JpaRepository<Purchasing, Long>,JpaSpecificationExecutor<Purchasing>{

    @Query(value = "SELECT max(id_purchasing) FROM bookstore.purchasing;",nativeQuery = true)
    int getLastId();

    @Query(value = "SELECT pu.id_purchasing,pu.status , pu.id_supplier, su.supplier_name, su.phonenumber,su.email, sum(po.price * po.quantity) as totalprice\n" +
            "FROM bookstore.purchasing pu \n" +
            "join bookstore.supplier su on su.id_supplier = pu.id_supplier\n" +
            "join bookstore.purchasing_order po on po.id_purchasing = pu.id_purchasing\n" +
            "join bookstore.book p on p.id_book = po.id_book\n" +
            "group by  pu.id_purchasing, pu.id_supplier, su.supplier_name, su.phonenumber,su.email;\n",nativeQuery = true)
    List<Purchasing> getAllOrder();



}
