package imoong.hellospring.payment;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import imoong.hellospring.exrate.SimpleExRateProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;

class PaymentTest {
    
    @Test
    void createPrepared() throws IOException {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        ExRateProvider exRateProvider = new SimpleExRateProvider();
        Payment payment = Payment.createPrepared(1L, "USD", BigDecimal.TEN, exRateProvider,
            clock);

        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(10_000));
        assertThat(payment.getValidUntil()).isEqualTo(LocalDateTime.now(clock).plusMinutes(30));

    }
    
    @Test
    void isValid() throws IOException {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        ExRateProvider exRateProvider = new SimpleExRateProvider();
        Payment payment = Payment.createPrepared(1L, "USD", BigDecimal.TEN, exRateProvider,
            clock);

        assertThat(payment.isValid(clock)).isTrue();
        assertThat(
            payment.isValid(Clock.offset(clock, Duration.ofMinutes(30)))).isFalse();
        
    }

}