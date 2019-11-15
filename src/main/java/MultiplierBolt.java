import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import scala.reflect.api.Internals;

public class MultiplierBolt extends BaseBasicBolt {
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
    Integer value = Integer.parseInt(input.getStringByField("value"));
        value=value*value;
    System.out.println(value);
    collector.emit(String.valueOf(input),new Values(value));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("value"));
    }
}
