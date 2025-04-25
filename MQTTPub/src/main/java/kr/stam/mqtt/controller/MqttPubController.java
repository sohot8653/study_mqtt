package kr.stam.mqtt.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.stam.mqtt.dto.MqttDto;
import kr.stam.mqtt.service.MqttPubService;

@RestController
public class MqttPubController {

    MqttPubService mqttPublisherService;

    public MqttPubController(MqttPubService mqttPublisherService) {
        this.mqttPublisherService = mqttPublisherService;
    }

    @PostMapping("/mqtt/publish")
    public void SendTopicAndMessage(@RequestBody MqttDto mqttRequestDto) {
        mqttPublisherService.sendMessage(mqttRequestDto.getTopic(), mqttRequestDto.getMessage());
    }
}
