package kr.stam.mqtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MqttPubApplication {

	public static void main(String[] args) {
		SpringApplication.run(MqttPubApplication.class, args);
	}

}
