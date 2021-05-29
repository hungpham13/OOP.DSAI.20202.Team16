package cls;


import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.value.ObservableFloatValue;

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
    public Surface getSurface(){
        return surface;
    }

    public Force getFrictionForce(){
        Force frictionForce = new Force();
        float normalForce = 10*obj.getMass();
        if (obj instanceof Cube ) {
            if (Math.abs(actor.getValue()) <= (normalForce*surface.getStaticFrictionCoef())) {
                if (Math.round(obj.getVelocity()) == 0) {frictionForce.setValue(- actor.getValue());
            } else {frictionForce.setValue((- Math.signum(obj.getVelocity()))*normalForce*surface.getKineticFrictionCoef());
                }
            } else {
                frictionForce.setValue((- Math.signum(obj.getVelocity()))*normalForce*surface.getKineticFrictionCoef());
            }
        } else if (obj instanceof Cylinder) {
            if (Math.abs(actor.getValue()) <= (3*normalForce*surface.getStaticFrictionCoef())) {
                if (Math.round(obj.getVelocity()) == 0) {frictionForce.setValue(- actor.getValue()/3);}
            } else {frictionForce.setValue((- Math.signum(obj.getVelocity()))*normalForce*surface.getKineticFrictionCoef());}
        } else {
            frictionForce.setValue((- Math.signum(obj.getVelocity()))*normalForce * surface.getKineticFrictionCoef());
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
    public float getObjAcceleration(){
        return obj.getAcceleration(actor.plus(getFrictionForce()));
    }
    public void appliedForceToObjInTime(float t){
        Force totalForce = actor.plus(getFrictionForce());
        obj.applyForce(totalForce, t);
    }
}
