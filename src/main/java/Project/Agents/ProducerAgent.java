package Project.Agents;


import Project.AutorunnableAgent;
import Project.DfHelper;
import Project.Model.ProducerData;
import Project.ProducerBehaviour.ReceiveTopicName;
import Project.Configs.ProducerCFG;
import Project.XMLParser;
import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@AutorunnableAgent(name = "P", starIndex = 1, count = 3)
@Slf4j
public class ProducerAgent extends Agent {

    @Override
    protected void setup() {
        DfHelper.registerAgent(this, "producer");
        log.info("Initialize" + this.getLocalName());

        String cfgName = null;
        switch (getLocalName()) {
            case "P1":
                cfgName = "P1";
                break;
            case "P2":
                cfgName = "P2";
                break;
            case "P3":
                cfgName = "P3";
                break;
            default:
                cfgName = "P1";
        }
        ProducerCFG cfg = XMLParser.unmarshal(
                new File("src/main/resources/Producer/" + cfgName + ".xml"),
                ProducerCFG.class
        );

        addBehaviour(new ReceiveTopicName(new ProducerData(cfg)));

    }
}


