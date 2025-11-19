package imoong.hellospring;

import imoong.hellospring.data.JdbcOrderRepository;
import imoong.hellospring.order.OrderRepository;
import imoong.hellospring.order.OrderService;
import imoong.hellospring.order.OrderServiceImpl;
import imoong.hellospring.order.OrderServiceTxProxy;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Import(DataConfig.class)
public class OrderConfig {
    @Bean
    public OrderRepository orderRepository(DataSource dataSource) {
        return new JdbcOrderRepository(dataSource);
    }

    @Bean
    public OrderService orderService(PlatformTransactionManager transactionManager, OrderRepository orderRepository) {
        return new OrderServiceTxProxy(new OrderServiceImpl(orderRepository), transactionManager);
    }

}
