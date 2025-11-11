package imoong.hellospring.payment;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentServiceTest {
    Clock clock;
    @BeforeEach
    void setUp() {
        this.clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @Test
    void convertedAmount() throws IOException {


        testAmount(BigDecimal.valueOf(10), BigDecimal.valueOf(10000), this.clock);
        testAmount(BigDecimal.valueOf(20), BigDecimal.valueOf(20000), this.clock);
        testAmount(BigDecimal.valueOf(30), BigDecimal.valueOf(30000), this.clock);

    }

    @Test
    void validUntil() throws IOException {
        PaymentService paymentService = new PaymentService(
            new ExRateProviderStub(BigDecimal.valueOf(1_000)), clock);

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        LocalDateTime now = LocalDateTime.now(clock);
        LocalDateTime expected = now.plusMinutes(30);

        assertThat(now.plusMinutes(30)).isEqualTo(expected);
        assertThat(payment.getValidUntil()).isEqualTo(expected);
    }

    private void testAmount(BigDecimal exRate, BigDecimal convertedAmount, Clock clock) throws IOException {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate), clock);

        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(1000));

        assertThat(payment.getExRate()).isEqualByComparingTo(exRate);

        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(convertedAmount);
    }
}