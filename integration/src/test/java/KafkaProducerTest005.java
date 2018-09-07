import com.chedaojunan.report.model.FixedFrequencyGpsData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaProducerTest005 {

  private static final Logger logger = LoggerFactory.getLogger(KafkaProducerTest005.class);
  private static final String BOOTSTRAP_SERVERS = "47.95.10.165:9092,47.93.24.115:9092,39.106.170.188:9092";

  private Producer producer;

  public void runProducer(String inputTopic) {

    Properties configProperties = new Properties();
    configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        Serdes.String().serializer().getClass());
    configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        Serdes.String().serializer().getClass());

    producer = new KafkaProducer(configProperties);

    String serverTime = System.currentTimeMillis() + "";

    for (int j = 1; j <= 200; j++) {
      FixedFrequencyGpsData gpsData = new FixedFrequencyGpsData();
      gpsData.setDeviceImei("05test000"+j);
      gpsData.setDeviceId("05test000"+j);
      gpsData.setLocalTime("1521478861000");
      gpsData.setTripId("05test000");
      gpsData.setServerTime(serverTime);
      gpsData.setLatitude(29.999921798706055+0.00001*j);
      gpsData.setLongitude(121.2059555053711+0.00001*j);
      gpsData.setAltitude(12.899999618530273);
      gpsData.setDirection(111.4);
      gpsData.setGpsSpeed(77.1626205444336);
      try {
        System.out.println(new ObjectMapper().writeValueAsString(gpsData));
        producer.send(new ProducerRecord<String, String>(inputTopic, new ObjectMapper().writeValueAsString(gpsData)));
      } catch (Exception ex) {
        ex.printStackTrace();//handle exception here
      }
    }
  }

  public void close() {
    if (producer != null) {
      producer.close();
      logger.info("Kafka producer is closed.");
    }
  }

  public static void main(String[] args) {
    KafkaProducerTest005 producerTest = new KafkaProducerTest005();
    String inputTopic = "device_gps_test";
    try {
//      while(true){
      long startTime = System.currentTimeMillis();
      for (int i=0;i<60;i++) {
        producerTest.runProducer(inputTopic);
        Thread.sleep(800);
      }
      long totalTime = (System.currentTimeMillis()-startTime)/1000;
      System.out.println("!!!!!!!!!!!!!!!totalTime=:"+totalTime);
//        Thread.sleep(800);
//      }
    } catch (Exception e) {
    }
    producerTest.close();
  }

}