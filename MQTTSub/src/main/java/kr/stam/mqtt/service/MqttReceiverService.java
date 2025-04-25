package kr.stam.mqtt.service;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import kr.stam.mqtt.dto.MqttDto;

@Component
public class MqttReceiverService implements MqttCallback {

    private final MqttClient mqttClient;

    public MqttReceiverService(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @PostConstruct
    public void init() {
        mqttClient.setCallback(this);
    }

    @Override
    public void connectionLost(Throwable cause) {
        // 연결이 끊어졌을 때 처리 로직
        System.out.println("연결이 끊어졌을 때 처리 로직");
        System.out.println(cause.toString());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // 메시지가 도착했을 때 처리 로직
    	MqttDto mqtt = new MqttDto();
    	mqtt.setTopic(topic);
    	mqtt.setMessage(message.toString());
        System.out.println("topic: " + topic + " message: " + message);
        
        // 메시지를 TimescaleDB에 저장
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // 메시지 전송이 완료되었을 때 처리 로직
        System.out.println("메시지 전송이 완료되었을 때 처리 로직");
    }
    
}