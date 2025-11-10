package imoong.hellospring.payment;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import imoong.hellospring.exrate.CachedExRateProvider;
import imoong.hellospring.exrate.WebApiExRateProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentServiceTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void convertedAmount() throws IOException {
        testAmount(BigDecimal.valueOf(10), BigDecimal.valueOf(10000));
        testAmount(BigDecimal.valueOf(20), BigDecimal.valueOf(20000));
        testAmount(BigDecimal.valueOf(30), BigDecimal.valueOf(30000));

    }

    private void testAmount(BigDecimal exRate, BigDecimal convertedAmount) throws IOException {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate));

        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(1000));

        assertThat(payment.getExRate()).isEqualByComparingTo(exRate);

        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(convertedAmount);
    }
}