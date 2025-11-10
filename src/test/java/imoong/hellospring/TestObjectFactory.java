package imoong.hellospring;

import imoong.hellospring.exrate.WebApiExRateProvider;
import imoong.hellospring.payment.ExRateProvider;
import imoong.hellospring.payment.ExRateProviderStub;
import imoong.hellospring.payment.PaymentService;
import java.math.BigDecimal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class TestObjectFactory {

    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new ExRateProviderStub(BigDecimal.valueOf(1_000));
    }


}
