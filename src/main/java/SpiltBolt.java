import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class SpiltBolt implements IRichBolt {
    OutputCollector collector;
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
     this.collector=collector;
    }

    @Override
    public void execute(Tuple input) {
        //String sentence=" This story is a never ending story about a story ";
        String sentence=input.getStringByField("value");
        String[] words = sentence.split(" ");

        for(String word: words) {
            //System.out.println(word);
            word = word.trim();

            if(!word.isEmpty()) {
                word = word.toLowerCase();
                collector.emit(new Values(word));
            }

        }

        collector.ack(input);
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("value"));

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
