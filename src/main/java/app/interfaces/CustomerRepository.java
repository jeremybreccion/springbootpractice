package app.interfaces;

import app.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * even though @Query is used, avoid it too much and use JPA provided queries
 */


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT email FROM customers WHERE is_subscribed = 1", nativeQuery = true)
    List<String> findSubscribedEmails();

    @Query(value = "SELECT * FROM customers WHERE is_subscribed = 1", nativeQuery = true)
    List<Customer> findSubscribedCustomers();
}
