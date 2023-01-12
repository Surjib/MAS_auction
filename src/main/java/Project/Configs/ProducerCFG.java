package Project.Configs;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Random;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "cfg")
@Getter
@Setter
public class ProducerCFG {

    @XmlElement
    private String type;
    @XmlElement
    private double A0;
    @XmlElement
    private double A1;
    @XmlElement
    private double A2;
    @XmlElement
    private double A3;

    private Random random = new Random();

    public double getPower(int time){
        double power = 0;
        switch (type) {
            case "Thermal":
                power = A0;
                break;
            case "Solar":
                if (time <= 5 || time >= 19) {
                    power = 0;
                } else {
                    power = A0 + A1 * time + A2 * Math.pow(time, 2) + A3 * Math.pow(time, 3);
                }
                break;
            case "Wind":
                power = Math.abs(random.nextGaussian())* A2 + A1;
                break;
        }

        if (power < 0) {
            return 0;
        }

        return power;
    }

}
