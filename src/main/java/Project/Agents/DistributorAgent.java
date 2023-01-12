package Project.Agents;


import Project.AutorunnableAgent;
import Project.DfHelper;
import Project.DistributerBehaviour.AcceptRequest;
import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;

@AutorunnableAgent(name = "D", starIndex = 1, count = 3)
@Slf4j
public class DistributorAgent extends Agent {

    @Override
    protected void setup() {
        DfHelper.registerAgent(this, "distributor:C" + getLocalName().substring(1));
        log.info("Initialize " + getLocalName());
        addBehaviour(new AcceptRequest());
    }
}
