import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

public class CountBolt implements IRichBolt {

    Map<String,Integer> counters;
    static int i=0;
    OutputCollector collector;
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
    this.collector=collector;
    this.counters=new HashMap<String,Integer>();
    }

    @Override
    public void execute(Tuple input) {
       // String str = input.getString(0);
    String str=input.getStringByField("value");
        if(!counters.containsKey(str)){
            counters.put(str, 1);
        }else {
            Integer c = counters.get(str) +1;
            counters.put(str, c);
        }
        System.out.println(i+" "+ " "+str+" "+counters.get(str));
        collector.emit(new Values(i++,str,counters.get(str)));
        collector.ack(input);
    }

    @Override
    public void cleanup() {
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("counter","word","count"));

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
