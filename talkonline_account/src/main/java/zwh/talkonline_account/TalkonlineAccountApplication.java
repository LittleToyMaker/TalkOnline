package zwh.talkonline_account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableFeignClients("zwh.**")
@EnableDiscoveryClient
@SpringBootApplication
public class TalkonlineAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalkonlineAccountApplication.class, args);
	}
}
