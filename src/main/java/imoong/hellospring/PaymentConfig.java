package imoong.hellospring;

import imoong.hellospring.api.ApiTemplate;
import imoong.hellospring.api.ErApiExRateExtractor;
import imoong.hellospring.api.SimpleApiExecutor;
import imoong.hellospring.exrate.RestTemplateExRateProvider;
import imoong.hellospring.exrate.WebApiExRateProvider;
import imoong.hellospring.payment.ExRateProvider;
import imoong.hellospring.payment.PaymentService;
import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PaymentConfig {

    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider(), clock());
    }

    @Bean
    public ApiTemplate apiTemplate(){
        return new ApiTemplate(new SimpleApiExecutor(), new ErApiExRateExtractor());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new RestTemplateExRateProvider(restTemplate());
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }




}
