package edu.icet.ecom.repository;

import edu.icet.ecom.model.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders,String> {

    Orders findTopByOrderByOrderIdDesc();
}
