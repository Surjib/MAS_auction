package Project.DistributerBehaviour;

import Project.JsonParser;
import Project.Model.ConsumerData;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AcceptRequest extends Behaviour {

    private MessageTemplate mt = MessageTemplate.and(
            MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
            MessageTemplate.MatchProtocol("price, power, time"));

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            ConsumerData consumerData = JsonParser.ToJson(msg.getContent(), ConsumerData.class);
            myAgent.addBehaviour(new DistributerFSM(consumerData));
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
