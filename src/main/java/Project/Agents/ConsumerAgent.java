package Project.Agents;


import Project.AutorunnableAgent;
import Project.ConsumerBehaviour.SendRequest;
import Project.Configs.ConsumerCFG;
import Project.DfHelper;
import Project.XMLParser;
import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@AutorunnableAgent(name = "C", starIndex = 1, count = 3)
@Slf4j
public class ConsumerAgent extends Agent {
    @Override
    protected void setup() {
        DfHelper.registerAgent(this, "consumer:" + getLocalName());
        log.info("Initialize " + getLocalName());

        String cfgName = null;
        switch (getLocalName()) {
            case "C1":
                cfgName = "C1";
                break;
            case "C2":
                cfgName = "C2";
                break;
            case "C3":
                cfgName = "C3";
                break;
            default:
                cfgName = "C1";
        }
        ConsumerCFG cfg = XMLParser.unmarshal(
                new File("src/main/resources/Consumer/"  + cfgName + ".xml"),
                ConsumerCFG.class
        );


        addBehaviour(new SendRequest(cfg));
    }
}
