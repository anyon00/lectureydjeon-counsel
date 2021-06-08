package lectureydjeon.external;

import org.springframework.stereotype.Component;

@Component
public class PaymentServiceFallback implements PaymentService {

    @Override
    public boolean pay(Payment payment) {
        // TODO Auto-generated method stub

        System.out.println("Circuit breaker has been opened. Fallback returned instead.");

        return false;
    }
    
}
