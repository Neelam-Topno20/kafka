import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.cassandra.bolt.CassandraWriterBolt;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;

import static org.apache.storm.cassandra.DynamicStatementBuilder.*;


public class KafkaStormDemo {


    public static void main(String args[]) throws Exception {
            int i=1;
            Config config = new Config();
            config.setDebug(true);
            config.put("cassandra.keyspace", "capgemini"); //Key_space name
            config.put("cassandra.port",9042);

            config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
            String kafkaBroker = "localhost:9092";
            String topic = "my-last-topic";
            KafkaSpoutConfig spoutConfig = KafkaSpoutConfig.builder(kafkaBroker,topic)
                    .setProp(ConsumerConfig.GROUP_ID_CONFIG, "kafka_spout")
                    .build();

            TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("KafkaSpout", new KafkaSpout<>(spoutConfig), 1);
       // builder.setBolt("MultiplierBolt", new MultiplierBolt()).shuffleGrouping("kafka_spout");

        builder.setBolt("SpiltBolt", new SpiltBolt()).shuffleGrouping("KafkaSpout");
        builder.setBolt("CountBolt", new CountBolt()).shuffleGrouping("SpiltBolt");
        builder.setBolt("CassandraBolt", new CassandraWriterBolt(
                async(

                        simpleQuery(
                                "INSERT INTO wordcount(word_id,word,count) VALUES(?,?,?);"
                        )
                        .with(
                                fields("counter","word","count")
                        )
                )
        ),1).globalGrouping("CountBolt");

          /*  TopologyBuilder builder = new TopologyBuilder();
            Config conf = new Config();
            conf.put("cassandra.keyspace", "Storm_Output"); //Key_space name
            conf.put("cassandra.nodes","ip-address-of-cassandra-machine");
            conf.put("cassandra.port",9042);*/
            //port on which cassandra is running (Default:9042)

  /*          builder.setSpout("generator", new RandomSentenceSpout(), 1);

            builder.setBolt("counter", new CassandraInsertionBolt(), 1).shuffleGrouping("generator");

            builder.setBolt("CassandraBolt",new CassandraWriterBolt(
                    async(
                            simpleQuery("INSERT INTO Storm_Output.tanle_name  (field1,field2 ) VALUES (?,?);")
                                    .with(
                                            fields("field1","field2 ")
                                    )
                    )
            ), 1).globalGrouping("counter");

            // Config conf = new Config();
            conf.setDebug(true);
            conf.setNumWorkers(1);

            StormSubmitter.submitTopologyWithProgressBar("Cassnadra-Insertion", conf, builder.createTopology());*/


            LocalCluster cluster = new LocalCluster();
            try {
                cluster.submitTopology("KafkaStormSample", config, builder.createTopology());
                //Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
    }}




