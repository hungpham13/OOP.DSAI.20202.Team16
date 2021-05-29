package cls;


import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableFloatValue;
import javafx.beans.value.ObservableValue;

public class Monitor {
    private Object obj = null;
    private boolean playing = true;
    private final Surface surface;
    private final Force actor;
    private final Force frictionForce = new Force(0);
    private final Force totalForce;

    public Monitor(Object obj, Surface surface, Force actor) {
        this.obj = obj;
        this.surface = surface;
        this.actor = actor;
        this.totalForce = actor.plus(frictionForce);

        //add listener for friction and total force
        this.obj.getMassProperty().addListener(observable -> updateFrictionForce(actor));
        this.obj.getVelocityProperty().addListener(observable ->updateFrictionForce(actor));
        this.surface.getStaticCoefProperty().addListener(observable ->updateFrictionForce(actor));
        this.surface.getKineticCoefProperty().addListener(observable ->updateFrictionForce(actor));
        this.actor.getValueProperty().addListener(observable -> {
            updateFrictionForce(actor);
            this.totalForce.setValue(this.actor.getValue()+this.frictionForce.getValue());
        });
        this.frictionForce.getValueProperty().addListener(observable -> this.totalForce.setValue(this.actor.getValue()+this.frictionForce.getValue()));
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

    public void updateFrictionForce(Force actor) {
        float normalForce = 10 * obj.getMass();
        if (obj instanceof Cube) {
            if (Math.abs(actor.getValue()) <= (normalForce * surface.getStaticFrictionCoef())) {
                if (Math.round(obj.getVelocity()) == 0) {
                    frictionForce.setValue(-actor.getValue());
                } else {
                    frictionForce.setValue((-Math.signum(obj.getVelocity())) * normalForce * surface.getKineticFrictionCoef());
                }
            } else {
                frictionForce.setValue((-Math.signum(obj.getVelocity())) * normalForce * surface.getKineticFrictionCoef());
            }
        } else if (obj instanceof Cylinder) {
            if (Math.abs(actor.getValue()) <= (3 * normalForce * surface.getStaticFrictionCoef())) {
                if (Math.round(obj.getVelocity()) == 0) {
                    frictionForce.setValue(-actor.getValue() / 3);
                }
            } else {
                frictionForce.setValue((-Math.signum(obj.getVelocity())) * normalForce * surface.getKineticFrictionCoef());
            }
        } else {
            frictionForce.setValue((-Math.signum(obj.getVelocity())) * normalForce * surface.getKineticFrictionCoef());
        }
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

    public Force getFrictionForce() {
        return frictionForce;
    }

    public void appliedForceToObjInTime(float t){
        Force totalForce = actor.plus(getFrictionForce());
        obj.applyForce(totalForce, t);
    }

    public Force getTotalForce() {
        return this.totalForce;
    }
}
