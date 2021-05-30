package cls;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Monitor {
    private ObservableList<Object> objects = FXCollections.observableArrayList();
    private boolean playing = true;
    private final Surface surface;
    private final Force actor;
    private final Force frictionForce = new Force(0);
    private final Force totalForce;

    public Monitor(Object obj, Surface surface, Force actor) {
        this.surface = surface;
        this.actor = actor;
        this.totalForce = actor.plus(frictionForce);
        if (obj != null) {
            this.objects.add(obj);
        }

        //add listener for friction and total force
        try {
            if (!isEmpty()) {
                getObj().getMassProperty().addListener(observable -> {
                    try {
                        updateFrictionForce();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                getObj().getVelocityProperty().addListener(observable -> {
                    try {
                        updateFrictionForce();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            this.surface.getStaticCoefProperty().addListener(observable -> {
                try {
                    updateFrictionForce();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            this.surface.getKineticCoefProperty().addListener(observable -> {
                try {
                    updateFrictionForce();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            this.actor.getValueProperty().addListener(observable -> {
                try {
                    updateFrictionForce();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.totalForce.setValue(this.actor.getValue() + this.frictionForce.getValue());
            });
            this.frictionForce.getValueProperty().addListener(observable -> this.totalForce.setValue(this.actor.getValue() + this.frictionForce.getValue()));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isPlaying() {
        if (!isEmpty()) {
            return playing;
        } else {
            return false;
        }
    }

    public boolean isEmpty(){
        return objects.isEmpty();
    }

    public void setObj(Object newObj) throws Exception {
        if (isEmpty() && newObj != null) {
            objects.add(newObj);
        } else if (newObj != null) {
            objects.remove(0);
            objects.add(newObj);
        } else {
            objects.remove(0);
        }
        if (!isEmpty()) {
            getObj().getMassProperty().addListener(observable -> {
                try {
                    updateFrictionForce();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            getObj().getVelocityProperty().addListener(observable -> {
                try {
                    updateFrictionForce();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
    public Object getObj() throws Exception {
        if (!isEmpty()) {
            return objects.get(0);
        }else {
            throw new Exception("Empty road");
        }
    }
    public ObservableList<Object> getObjList(){
        return objects;
    }
    public Force getActorForce(){
        return actor;
    }
    public Surface getSurface(){
        return surface;
    }

    public void updateFrictionForce() throws Exception {
        float normalForce = 10 * getObj().getMass();
        if (getObj() instanceof Cube) {
            if (Math.abs(actor.getValue()) <= (normalForce * surface.getStaticFrictionCoef())) {
                if (Math.abs(getObj().getVelocity()) <= 0.1) {
                    getObj().setVelocity(0);
                    frictionForce.setValue(-actor.getValue());
                } else {
                    frictionForce.setValue((-Math.signum(getObj().getVelocity())) * normalForce * surface.getKineticFrictionCoef());
                }
            } else {
                frictionForce.setValue((-Math.signum(getObj().getVelocity())) * normalForce * surface.getKineticFrictionCoef());
            }
        } else if (getObj() instanceof Cylinder) {
            if (Math.abs(actor.getValue()) <= (3 * normalForce * surface.getStaticFrictionCoef())) {
                if (Math.abs(getObj().getVelocity()) <= 0.1) {
                    getObj().setVelocity(0);
                    frictionForce.setValue(-actor.getValue() / 3);
                } else {
                    frictionForce.setValue((-Math.signum(getObj().getVelocity())) * normalForce * surface.getKineticFrictionCoef());
                }
            } else {
                frictionForce.setValue((-Math.signum(getObj().getVelocity())) * normalForce * surface.getKineticFrictionCoef());
            }
        }
    }

    public void pause(){
        playing = false;
    }
    public void cont(){
        playing = true;
    }
    public void reset() throws Exception {
        actor.setValue(0);
        frictionForce.setValue(0);
        totalForce.setValue(0);
        setObj(null);
        playing = true;
    }

    public float getObjAcceleration() throws Exception {
        return getObj().getAcceleration(totalForce);
    }

    public Force getFrictionForce() {
        return frictionForce;
    }

    public void appliedForceToObjInTime(float t) throws Exception {
        getObj().applyForce(totalForce, t);
    }

    public Force getTotalForce() {
        return this.totalForce;
    }
}
