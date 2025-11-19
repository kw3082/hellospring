package imoong.hellospring.data;

import imoong.hellospring.order.Order;
import imoong.hellospring.order.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


public class JpaOrderRepository implements OrderRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Order order) {
        em.persist(order);
    }

}
