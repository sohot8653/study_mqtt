package kr.stam.mqtt.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MqttPubService {

	private final MqttClient mqttClient;
	
	private static final Random random = new Random();
    private static final double NORMAL_TEMP = 36.5;
    private static final double NORMAL_RANGE = 5.0;
    private static final double OUTLIER_RANGE = 20.0;
    private static double previousTemperature = NORMAL_TEMP; // 이전 온도 초기화

	public MqttPubService(MqttClient mqttClient) {
		this.mqttClient = mqttClient;
	}

	public void sendMessage(String topic, String message) {
		try {
			mqttClient.publish(topic, new MqttMessage(message.getBytes()));
		} catch (MqttException e) {
			throw new RuntimeException(e);
		}
	}

	@Scheduled(fixedDelay = 100) // 0.01초마다 실행    
    public void run() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(new Date());
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("data", String.valueOf(getRandTemperature()));
        returnMap.put("date", now);
        String jsonStr = null;
        try {
			jsonStr = new ObjectMapper().writeValueAsString(returnMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		sendMessage("sensor/data", jsonStr);
	}
	
	private double getRandTemperature() {
		double temperature;

        if (random.nextDouble() < 0.999) { // 99.9% 확률
        	// 이전 온도에서 ±0.2 범위 내에서 생성
            temperature = previousTemperature + (random.nextDouble() * 0.4) - 0.2;
            
            // 정상 범위 유지
            if (temperature < NORMAL_TEMP - NORMAL_RANGE) {
                temperature = NORMAL_TEMP - NORMAL_RANGE;
            } else if (temperature > NORMAL_TEMP + NORMAL_RANGE) {
                temperature = NORMAL_TEMP + NORMAL_RANGE;
            }
            
            // 이전 온도를 현재 온도로 업데이트
            previousTemperature = temperature;
        } else { // 0.1% 확률, 이상치 데이터 이므로 이전 데이터에 반영되진 않는다.
            // 이상치 범위 온도
            temperature = NORMAL_TEMP + (random.nextDouble() * 2 * OUTLIER_RANGE) - OUTLIER_RANGE;
        }

        // 소수점 1자리까지 반올림
        temperature = Math.round(temperature * 10.0) / 10.0;
        
        return temperature;
	}
}
