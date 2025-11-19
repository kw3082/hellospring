package imoong.hellospring.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import imoong.hellospring.OrderConfig;
import java.math.BigDecimal;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderConfig.class)
class OrderServiceSpringTest {

    @Autowired
    OrderService orderService;

    @Autowired
    DataSource dataSource;

    @Test
    void createOrder() {
        Order order = orderService.createOrder("0100", BigDecimal.valueOf(10000));

        assertThat(order.getId()).isGreaterThan(0);
    }

    @Test
    void createOrders() {
        List<OrderReq> orderReqs = List.of(new OrderReq("0200", BigDecimal.TEN),
            new OrderReq("0300", BigDecimal.TEN));

        List<Order> orders = orderService.createOrders(orderReqs);
        assertThat(orders).hasSize(2);

        orders.forEach(order -> assertThat(order.getId()).isGreaterThan(0));

    }

    @Test
    void createDuplicatedOrders() {
        List<OrderReq> orderReqs = List.of(
            new OrderReq("0300", BigDecimal.TEN),
            new OrderReq("0300", BigDecimal.TEN)
        );

        assertThatThrownBy(() -> orderService.createOrders(orderReqs))
            .isInstanceOf(DataIntegrityViolationException.class);

        JdbcClient client = JdbcClient.create(dataSource);
        Long count = client.sql("select count(*) from orders where no = '0300'").query(Long.class)
            .single();
        assertThat(count).isEqualTo(0);

    }

}