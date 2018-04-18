package zwh.talkonline_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@EnableFeignClients(basePackages = "zwh.**")
@EnableDiscoveryClient
@SpringBootApplication
@ServletComponentScan
public class TalkonlineWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalkonlineWebApplication.class, args);
	}
}
