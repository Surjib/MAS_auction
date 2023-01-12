package LR4;

import Project.DfHelper;
import Project.ConsumerBehaviour.SendRequest;
import Project.DistributerBehaviour.AcceptRequest;
import Project.Model.ProducerData;
import Project.ProducerBehaviour.ReceiveTopicName;
import Project.Configs.ConsumerCFG;
import Project.Configs.ProducerCFG;
import Project.TimeModel;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.StaleProxyException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AgentTest {

    private JadeTestingKit kit = new JadeTestingKit();
    private SendRequest inner = null;
    private Behaviour resBeh;

    private long time = TimeModel.getTime() + 100;

    /*
    условия:    избыток мощности у одного производителя, 1 производитель
    результат:  0 (запрос отклонен - завышенная цена)
    */

    @Test

    void scene1Test() throws InterruptedException, StaleProxyException {

        createDistributor("D1");
        createProducer("P1", 12);

        Thread.sleep(100);

        createConsumer("L1", 10, 250);

        Thread.sleep(500);


        Thread.sleep(time);
        resBeh = inner.getBeh();

        Thread.sleep(500);

        Assertions.assertEquals(0, resBeh.onEnd());

        }

    /*
    условия:    избыток мощности у одного производителя, 2 производителя
    результат:  1 (запрос принят от одного производителя, после аукциона)
    */

    @Test

    void scene2Test() throws InterruptedException, StaleProxyException {

        createDistributor("D1");
        createProducer("P1", 10);
        createProducer("P2", 12);

        Thread.sleep(100);

        createConsumer("L1", 10, 250);

        Thread.sleep(500);


        Thread.sleep(time);
        resBeh = inner.getBeh();

        Thread.sleep(500);

        Assertions.assertEquals(1, resBeh.onEnd());

    }

    /*
    условия:    избыток мощности у двух производителей в сумме, 3 производителя
    результат:  2 (запрос принят от двух производителей, после аукциона и разбиения контракта)
    */

    @Test

    void scene3Test() throws InterruptedException, StaleProxyException {

        createDistributor("D1");
        createProducer("P1", 15);
        createProducer("P2", 18);
        createProducer("P3", 5);

        Thread.sleep(100);

        createConsumer("L1", 20, 300);

        Thread.sleep(500);


        Thread.sleep(time);
        resBeh = inner.getBeh();

        Thread.sleep(500);

        Assertions.assertEquals(2, resBeh.onEnd());

    }

    /*
    условия:    у двух производителей не хватает мощности, 3 производителя
    результат:  3 (запрос принят от трех производителей, после аукциона и разбиения контракта)
    */

    @Test

    void scene4Test() throws InterruptedException, StaleProxyException {

        createDistributor("D1");
        createProducer("P1", 10);
        createProducer("P2", 8);
        createProducer("P3", 9);

        Thread.sleep(100);

        createConsumer("L1", 20, 300);

        Thread.sleep(500);


        Thread.sleep(time);
        resBeh = inner.getBeh();

        Thread.sleep(500);

        Assertions.assertEquals(3, resBeh.onEnd());

    }

    /*
    условия:    у трех производителей не хватает мощности, 4 производителя
    результат:  4 (запрос принят от 4 производителей, после аукциона и разбиения контракта)
    */

    @Test

    void scene5Test() throws InterruptedException, StaleProxyException {

        createDistributor("D1");
        createProducer("P1", 10);
        createProducer("P2", 8);
        createProducer("P3", 9);
        createProducer("P4", 9);

        Thread.sleep(100);

        createConsumer("L1", 30, 300);

        Thread.sleep(500);


        Thread.sleep(time);
        resBeh = inner.getBeh();

        Thread.sleep(500);

        Assertions.assertEquals(4, resBeh.onEnd());
    }

    /*
    условия:    избыток мощности у двух производителей в сумме, итоговая цена
                после спилита больше макисмальной, 2 производителя
    результат:  0 (запрос отклонен - 0 производителей)
    */

    @Test

    void scene6Test() throws InterruptedException, StaleProxyException {

        createDistributor("D1");
        createProducer("P1", 10);
        createProducer("P2", 11);

        Thread.sleep(100);

        createConsumer("L1", 20, 199.85);

        Thread.sleep(500);


        Thread.sleep(time);
        resBeh = inner.getBeh();

        Thread.sleep(500);

        Assertions.assertEquals(0, resBeh.onEnd());
    }

     /*
    условия:    в системе не хватает мощности, 3 производителя
    результат:  0 (запрос отклонен - 0 производителей)
    */

    @Test

    void scene7Test() throws InterruptedException, StaleProxyException {

        createDistributor("D1");
        createProducer("P1", 5);
        createProducer("P2", 6);
        createProducer("P3", 7);

        Thread.sleep(100);

        createConsumer("L1", 20, 1000);

        Thread.sleep(500);


        Thread.sleep(time);
        resBeh = inner.getBeh();

        Thread.sleep(500);

        Assertions.assertEquals(0, resBeh.onEnd());
    }

    /*
    условия:    в системе не хватает мощности, 4 производителя
    результат:  0 (запрос отклонен - 0 производителей)
    */

    @Test

    void scene8Test() throws InterruptedException, StaleProxyException {

        createDistributor("D1");
        createProducer("P1", 2);
        createProducer("P2", 3);
        createProducer("P3", 4);
        createProducer("P4", 5);

        Thread.sleep(100);

        createConsumer("L1", 20, 1000);

        Thread.sleep(500);


        Thread.sleep(time);
        resBeh = inner.getBeh();

        Thread.sleep(500);

        Assertions.assertEquals(0, resBeh.onEnd());
    }

    /*
    условия:    нет дистрибьютера
    результат:  -1 (no distributor)
    */

    @Test

    void scene9Test() throws InterruptedException, StaleProxyException {

        createProducer("P1", 2);

        Thread.sleep(100);

        createConsumer("L1", 20, 1000);

        Thread.sleep(500);

        Assertions.assertEquals(-1, inner.onEnd());

    }

       /*
    условия:    нет производителя
    результат:  0 (запрос отклонен - 0 производителей)
    */

    @Test

    void scene10Test() throws InterruptedException, StaleProxyException {
        createDistributor("D1");

        Thread.sleep(100);

        createConsumer("L1", 20, 1000);

        Thread.sleep(500);

        Thread.sleep(time);
        resBeh = inner.getBeh();

        Thread.sleep(500);

        Assertions.assertEquals(0, resBeh.onEnd());

    }

    @BeforeEach
    void beforeEach(){
        kit.startJade();
        TimeModel.resetTime();
    }

    @AfterEach
    void afterEach() throws StaleProxyException {
        kit.deadAllAgent();
    }

    void createDistributor(String name) throws StaleProxyException {
        kit.createAgent(name, new OneShotBehaviour() {
            @Override
            public void action() {
                DfHelper.registerAgent(myAgent, "distributor:L" +myAgent.getLocalName().substring(1));
                myAgent.addBehaviour(new AcceptRequest());
            }

        });
    }

    /*
    producer price = 1000/power + 100
    */
    void createProducer(String name, double power) throws StaleProxyException {
        kit.createAgent(name, new OneShotBehaviour() {
            @Override
            public void action() {
                DfHelper.registerAgent(myAgent, "producer");
                ProducerCFG cfg = new ProducerCFG();
                cfg.setA0(power);
                cfg.setType("TPS");
                myAgent.addBehaviour(new ReceiveTopicName(new ProducerData(cfg)));
            }

        });
    }


    void createConsumer(String name, double power, double maxPrice) throws StaleProxyException {
        kit.createAgent(name, new OneShotBehaviour() {
            @Override
            public void action() {
                DfHelper.registerAgent(myAgent, "consumer:" + myAgent.getLocalName());
                ConsumerCFG cfg = new ConsumerCFG();
                for (int i = 0; i < 24; i++) {
                    cfg.getLoad().add(100.0);
                }
                cfg.setPnom(power);
                cfg.setMaxPrice(maxPrice);
                myAgent.addBehaviour(inner = new SendRequest(cfg ));
            }

        });
    }
}
