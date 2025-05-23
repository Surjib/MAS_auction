package Project.DistributerBehaviour;

import Project.DfHelper;
import Project.JsonParser;
import Project.Model.WinnerProducerData;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SendAgreePrice extends OneShotBehaviour {

    private WinnerProducerData winner;
    public SendAgreePrice(WinnerProducerData winner) {
        this.winner = winner;
    }

    @Override
    public void action() {


        ACLMessage msg;
        if (winner.getWinner().size() > 0) {
            msg = new ACLMessage(ACLMessage.AGREE);
            msg.setContent(JsonParser.FromJson(winner));
        } else {
            msg = new ACLMessage(ACLMessage.REFUSE);
            msg.setContent("refuse");
        }

        msg.setProtocol("result price");

        List<AID> consumers = DfHelper.findAgents(myAgent, "consumer:C" + myAgent.getLocalName().substring(1));
        if (consumers.isEmpty()) {
            throw new RuntimeException("no consumer");
        }

        for (AID consumer : consumers) {
            msg.addReceiver(consumer);
        }

        myAgent.send(msg);
        log.debug("send {}", msg.getContent());

    }
}
