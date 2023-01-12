package Project.DistributerBehaviour;

import Project.JsonParser;
import Project.Model.ConsumerData;
import Project.Model.DistributorData;
import Project.Model.TopicData;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;


public class SendFirstData extends WakerBehaviour {
    private TopicData topicData;
    private ConsumerData consumerData;


    public SendFirstData(Agent a, long timeout, TopicData topicData, ConsumerData consumerData) {
        super(a, timeout);
        this.topicData = topicData;
        this.consumerData = consumerData;
    }

    @Override
    protected void onWake() {
        ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
        msg.setContent(JsonParser.FromJson(new DistributorData(
                consumerData.getTime(),
                consumerData.getPower())));
        msg.setProtocol("time, power");
        msg.addReceiver(topicData.getTopic());
        getAgent().send(msg);
    }
}
