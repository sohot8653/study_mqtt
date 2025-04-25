package kr.stam.mqtt.conf;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {

    @Value("tcp://localhost:1883")
    private String brokerUrl;

    @Value("subscriber")
    private String clientId;

    @Value("sensor/data")
    private String topic;

    @Bean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{brokerUrl});
        options.setCleanSession(true);
        return options;
    }


    @Bean
    public MqttClient mqttClient() {
        try {
            MqttClient mqttClient = new MqttClient(brokerUrl, clientId, new MemoryPersistence());

            mqttClient.connect(mqttConnectOptions());
            mqttClient.subscribe(topic);
            return mqttClient;
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}