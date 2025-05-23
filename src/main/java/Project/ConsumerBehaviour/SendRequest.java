package Project.ConsumerBehaviour;

import Project.DfHelper;
import Project.JsonParser;
import Project.Model.ConsumerData;
import Project.Configs.ConsumerCFG;
import Project.TimeModel;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SendRequest extends OneShotBehaviour {

    private int time;
    private boolean failure = false;

    private ConsumerCFG cfg;

    private MessageTemplate mt = MessageTemplate.and(
            MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
            MessageTemplate.MatchProtocol("price, power, time"));

    public SendRequest(ConsumerCFG cfg) {
        this.cfg = cfg;
        time = TimeModel.getCurrentTime();
    }

    @Override
    public void action() {

        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.setContent(JsonParser.FromJson(new ConsumerData(
                cfg.getMaxPrice() * cfg.getPower(time), cfg.getPower(time), time)));

        msg.setProtocol("price, power, time");
        List<AID> distributors = DfHelper.findAgents(myAgent, "distributor:" + myAgent.getLocalName());

        if (distributors.isEmpty()) {
            log.warn("no distributor");
            failure = true;
//            throw  new RuntimeException("no distributor");
        } else {
            for (AID distributor : distributors) {
                msg.addReceiver(distributor);
            }
            getAgent().send(msg);
            log.info("send msg {} to {} distributors", msg.getContent(), distributors.size());
        }
    }

    @Getter
    private Behaviour beh = null;

    @Override
    public int onEnd() {
        if (failure) {
            myAgent.addBehaviour(new WakerBehaviour(myAgent, TimeModel.getRestOfTime()) {
                @Override
                protected void onWake() {
                    log.info("restart");
                    myAgent.addBehaviour(new SendRequest(cfg));
                }
            });

            return -1;
        } else {
            myAgent.addBehaviour(new WakerBehaviour(myAgent, TimeModel.getRestOfTimeAuction()) {
                @Override
                protected void onWake() {
                    myAgent.addBehaviour(beh = new RequestPrice(cfg, time));
                    log.info("stop auction");
                }
            });
            return 1;
        }
    }


}
