package nz.webshop.repositories;

import nz.webshop.models.Order.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrdersRepository extends JpaRepository<Order, Integer> {
   // void save(OrderMini order1);
    //List <Order> getByCustomerId(Integer customerId);
   // List <Order> getOrdersByCustomerId(Integer id);
    //List <Order> getByCustomerId(Integer customerId);
   // List <Order> getOrdersByCustomerId(Integer id);

}
