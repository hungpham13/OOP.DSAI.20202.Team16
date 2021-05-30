package cls;

import javafx.beans.property.SimpleFloatProperty;

public class Surface {
    private SimpleFloatProperty staticCoef = new SimpleFloatProperty();
    private SimpleFloatProperty kineticCoef = new SimpleFloatProperty();

    public Surface(float staticCoef, float kineticCoef) throws Exception {
        setStaticFrictionCoef(staticCoef);
        setKineticFrictionCoef(kineticCoef);
    }

    public SimpleFloatProperty getStaticCoefProperty() {
        return staticCoef;
    }

    public SimpleFloatProperty getKineticCoefProperty() {
        return kineticCoef;
    }

    public float getStaticFrictionCoef() {
        return staticCoef.getValue();
    }

    public void setStaticFrictionCoef(float staticCoef) throws Exception {
        if (0 <= staticCoef && staticCoef<=1){
            this.staticCoef.setValue(staticCoef);
        } else {
            throw new Exception("The coefficient must be between 0 and 1");
        }
    }

    public float getKineticFrictionCoef() {
        return kineticCoef.getValue();
    }

    public void setKineticFrictionCoef(float kineticCoef) throws Exception {
        if (0 <= kineticCoef && kineticCoef<=getStaticFrictionCoef()) {
            this.kineticCoef.setValue(kineticCoef);
        } else {
            throw new Exception("The coefficient must be between 0 and the value of static friction coefficient: "+getStaticFrictionCoef());
        }
    }
}
