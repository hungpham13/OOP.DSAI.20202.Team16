package cls;

public class Surface {
    private float staticCoef;
    private float kineticCoef;

    public Surface(float staticCoef, float kineticCoef) {
        this.staticCoef = staticCoef;
        this.kineticCoef = kineticCoef;
    }


    public float getStaticFrictionCoef() {
        return staticCoef;
    }

    public void setStaticFrictionCoef(float staticCoef) throws Exception {
        if (0 <= staticCoef && staticCoef<=1){
            this.staticCoef = staticCoef;
        } else {
            throw new Exception("The coefficient must be between 0 and 1");
        }
    }

    public float getKineticFrictionCoef() {
        return kineticCoef;
    }

    public void setKineticFrictionCoef(float kineticCoef) throws Exception {
        if (0 <= kineticCoef && kineticCoef<=1) {
            this.kineticCoef = kineticCoef;
        } else {
            throw new Exception("The coefficient must be between 0 and 1");
        }
    }
}
