package com.ptit.repository;

import java.util.List;

import com.ptit.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierDAO extends JpaRepository<Supplier, Long>,JpaSpecificationExecutor<Supplier>{

    @Query(value = "SELECT * FROM bookstore.supplier", nativeQuery = true)
    List<Supplier> getAll();
}
