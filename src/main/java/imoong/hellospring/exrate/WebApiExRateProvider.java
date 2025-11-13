package imoong.hellospring.exrate;

import imoong.hellospring.api.ApiTemplate;
import imoong.hellospring.payment.ExRateProvider;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class WebApiExRateProvider implements ExRateProvider {

    private final ApiTemplate apiTemplate;

    public WebApiExRateProvider(ApiTemplate apiTemplate) {
        this.apiTemplate = apiTemplate;
    }


    @Override
    public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        return apiTemplate.getForExRate(url);
    }

}
