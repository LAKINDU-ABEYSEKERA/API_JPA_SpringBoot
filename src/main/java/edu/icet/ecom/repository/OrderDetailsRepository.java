package edu.icet.ecom.repository;

import edu.icet.ecom.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetail, Long> {
    // We don't need the String ID generators anymore
    // JPA will handle the Long ID automatically
}