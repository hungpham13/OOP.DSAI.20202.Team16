package cls;

public class Surface {
    private float staticCoef;
    private float kineticCoef;


    public float getStaticFrictionCoef() {
        return staticCoef;
    }

    public void setStaticFrictionCoef(float staticCoef) {
        this.staticCoef = staticCoef;
    }

    public float getKineticFrictionCoef() {
        return kineticCoef;
    }

    public void setKineticFrictionCoef(float kineticCoef) {
        this.kineticCoef = kineticCoef;
    }
}
