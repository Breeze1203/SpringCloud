import org.junit.jupiter.api.Test;
import org.pt.LoadBalanceApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

/**
 * @ClassName LoadBalanceTest
 * @Author pt
 * @Description
 * @Date 2025/2/18 15:22
 **/
@SpringBootTest(classes = {LoadBalanceApplication.class})
public class LoadBalanceTest {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Test
    void Test(){
        ServiceInstance provider = loadBalancerClient.choose("provider");
        System.out.println("chose :"+provider.getHost()+":"+provider.getPort());
        ServiceInstance provider1 = loadBalancerClient.choose("provider");
        System.out.println("chose :"+provider1.getHost()+":"+provider1.getPort());
        ServiceInstance provider2 = loadBalancerClient.choose("provider");
        System.out.println("chose :"+provider2.getHost()+":"+provider2.getPort());
        ServiceInstance provider3 = loadBalancerClient.choose("provider");
        System.out.println("chose :"+provider3.getHost()+":"+provider3.getPort());
    }
}
