package imoong.hellospring.learningtest;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;

public class ClockTest {

    @Test
    void clock() throws InterruptedException {
        Clock clock = Clock.systemDefaultZone();

        LocalDateTime dt1 = LocalDateTime.now(clock);
        Thread.sleep(1);
        LocalDateTime dt2 = LocalDateTime.now(clock);

        System.out.println(dt1);
        System.out.println(dt2);

        assertThat(dt2).isAfter(dt1);

    }


    @Test
    void fixedClock() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        LocalDateTime dt1 = LocalDateTime.now(clock);
        LocalDateTime dt2 = LocalDateTime.now(clock);
        LocalDateTime dt3 = LocalDateTime.now(clock).plusHours(1);

        assertThat(dt1.plusHours(1)).isEqualTo(dt3);
        assertThat(dt2).isEqualTo(dt1);
        assertThat(dt2.plusHours(1)).isEqualTo(dt3);
    }
}
