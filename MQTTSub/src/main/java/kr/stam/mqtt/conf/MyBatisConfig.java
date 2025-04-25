package kr.stam.mqtt.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("kr.stam.mqtt.mapper")  // 매퍼가 위치한 패키지
public class MyBatisConfig {
}