package kr.stam.mqtt.service;

import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqttReceiver implements MqttCallback {

    @Autowired
    private MqttClient mqttClient;

    @PostConstruct
    public void init() {
        mqttClient.setCallback(this);
    }

    @Override
    public void connectionLost(Throwable cause) {
        // 연결이 끊어졌을 때 처리 로직
        System.out.println("연결이 끊어졌을 때 처리 로직");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // 메시지가 도착했을 때 처리 로직
        System.out.printf("메세지 도착 : topic: %s / message : %s", topic, message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // 메시지 전송이 완료되었을 때 처리 로직
        //System.out.println("메시지 전송이 완료되었을 때 처리 로직");
    }
}