import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;

public class KafkaStormDemo {

        public static void main(String args[]) throws Exception {
            Config config = new Config();
            config.setDebug(true);
            config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
            String zkConnString = "localhost:9092";
            String topic = "my-last-topic";
            KafkaSpoutConfig spoutConfig = KafkaSpoutConfig.builder(zkConnString,topic)
                    .setProp(ConsumerConfig.GROUP_ID_CONFIG, "kafka_spout")
                    .build();
            System.out.println("0");

            TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("KafkaSpout", new KafkaSpout<>(spoutConfig), 1);
       // builder.setBolt("MultiplierBolt", new MultiplierBolt()).shuffleGrouping("kafka_spout");

        builder.setBolt("SpiltBolt", new SpiltBolt()).shuffleGrouping("KafkaSpout");
        builder.setBolt("CountBolt", new CountBolt()).shuffleGrouping("SpiltBolt");

            LocalCluster cluster = new LocalCluster();
            try {
                cluster.submitTopology("KafkaStormSample", config, builder.createTopology());
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
    }}




