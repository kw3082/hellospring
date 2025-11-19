package imoong.hellospring;

import imoong.hellospring.order.Order;
import imoong.hellospring.order.OrderService;
import imoong.hellospring.order.OrderServiceImpl;
import java.math.BigDecimal;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderClient {

    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(
            OrderConfig.class);
        OrderService orderService = beanFactory.getBean(OrderService.class);

        Order order = orderService.createOrder("123456789", BigDecimal.valueOf(10000));
        System.out.println(order);

    }

}
