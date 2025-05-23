package LR4;

import jade.core.ProfileImpl;
import jade.core.behaviours.Behaviour;
import jade.util.leap.Properties;
import jade.core.Runtime;
import jade.wrapper.*;


import java.util.ArrayList;
import java.util.List;

public class JadeTestingKit {
    private AgentContainer mainContainer;
    private List<AgentController> agents = new ArrayList<>();


    public void startJade(){
        Properties props = new Properties();
        props.setProperty("gui", "true");
        props.setProperty("services", "jade.core.event.NotificationService;jade.core.messaging.TopicManagementService");
        ProfileImpl p = new ProfileImpl(props);
        Runtime.instance().setCloseVM(true);
        mainContainer = Runtime.instance().createMainContainer(p);

    }


    public void createAgent(String agentName, Behaviour ... behs) throws StaleProxyException {
        AgentController newAgent = mainContainer.createNewAgent(agentName, MockAgent.class.getName(), behs);
        agents.add(newAgent);
        newAgent.start();
    }


    public void deadAllAgent() throws StaleProxyException {
        if (!agents.isEmpty()) {
            for (AgentController agent: agents) {
                agent.kill();
            }
            agents = new ArrayList<>();
        }
    }


}
