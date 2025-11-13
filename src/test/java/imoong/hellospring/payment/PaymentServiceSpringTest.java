package imoong.hellospring.payment;

import static org.assertj.core.api.Assertions.assertThat;

import imoong.hellospring.TestPaymentConfig;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestPaymentConfig.class)
class PaymentServiceSpringTest {

    @Autowired PaymentService paymentService;
    @Autowired ExRateProviderStub exRateProviderStub;
    @Autowired Clock clock;

    @Test
    void convertedAmount()  {
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.TEN);

        assertThat(payment.getExRate()).isEqualByComparingTo(BigDecimal.valueOf(1_000));
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(10_000));

        exRateProviderStub.setExRate(BigDecimal.valueOf(500));
        Payment payment2 = paymentService.prepare(100L, "USD", BigDecimal.TEN);

        assertThat(payment2.getExRate()).isEqualByComparingTo(BigDecimal.valueOf(500));
        assertThat(payment2.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(5_000));

    }

    @Test
    void validUntil()  {
        PaymentService paymentService = new PaymentService(
            new ExRateProviderStub(BigDecimal.valueOf(1_000)), this.clock);

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expected = now.plusMinutes(30);

        assertThat(now.plusMinutes(30)).isEqualTo(expected);
        assertThat(payment.getValidUntil()).isEqualTo(expected);
    }

}