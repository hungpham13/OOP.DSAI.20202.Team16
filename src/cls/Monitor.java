package cls;

public class Monitor {
    private Object obj = null;
    private boolean playing = true;
    private final Surface surface;
    private final Force actor;


    public Monitor(Object obj, Surface surface, Force actor) {
        this.obj = obj;
        this.surface = surface;
        this.actor = actor;
    }

    public boolean isPlaying() {
        if (!isEmpty()) {
            return playing;
        } else {
            return false;
        }
    }

    public boolean isEmpty(){
        return obj == null;
    }
    public void setObj(Object newObj) {
        obj = newObj;
    }
    public Object getObj(){
        return obj;
    }
    public Force getActorForce(){
        return actor;
    }
    public Force getFrictionForce(){
        Force frictionForce = new Force();
        float normalForce = 10*obj.getMass();
        if (obj instanceof Cube ) {
            if (actor.getValue() <= (normalForce*surface.getStaticFrictionCoef())) {
                if (obj.getVelocity() == 0) {frictionForce.setValue(- actor.getValue());
            } else {frictionForce.setValue(- normalForce*surface.getKineticFrictionCoef());
                }
            } else {
                frictionForce.setValue(- normalForce*surface.getKineticFrictionCoef());
            }
        } else if (obj instanceof Cylinder) {
            if (actor.getValue() <= (3*normalForce*surface.getStaticFrictionCoef())) {
                if (obj.getVelocity() == 0) {frictionForce.setValue(- actor.getValue()/3);}
            } else {frictionForce.setValue(- normalForce*surface.getKineticFrictionCoef());}
        } else {
            frictionForce.setValue(-normalForce * surface.getKineticFrictionCoef());
        }
        return frictionForce;
    }
    public void pause(){
        playing = false;
    }
    public void cont(){
        playing = true;
    }
    public void reset(){
        actor.setValue(0);
        setObj(null);
        playing = true;
    }
}
