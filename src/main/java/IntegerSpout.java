import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import java.util.Map;

public class IntegerSpout extends BaseRichSpout {
    SpoutOutputCollector collector;
    private  Integer i=0;
    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    this.collector= collector;
    }

    @Override
    public void nextTuple() {
        System.out.println(i+"=integer value");
        collector.emit(new Values(i));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("field"));
    }
}
