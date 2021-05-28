package cls;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.value.ObservableFloatValue;

public abstract class Object {

    private SimpleFloatProperty mass = new SimpleFloatProperty();
    private SimpleFloatProperty velocity = new SimpleFloatProperty();

    public float getMass() {
        return mass.getValue();
    }

    public float getVelocity() {
        return velocity.getValue();
    }

    public void setMass(float mass) throws Exception {
        if (mass > 0) {
            this.mass.setValue(mass);
            System.out.println("mass is now set to " + getMass());
        } else {
            throw new Exception("Object's mass must > 0");
        }
    }

    public void setVelocity(float velocity) {
        this.velocity.setValue(velocity);
        System.out.println("velocity is now set to " + getVelocity());
    }

    public void applyForce(Force force, float time) {
        System.out.println("Applying force " + force.getValue() + " in time " + time);
        float a = this.getAcceleration(force);
        this.setVelocity(getVelocity() + a * time);
    }

    public float getAcceleration(Force force) {
        return force.getValue() / getMass();
    }

    public SimpleFloatProperty getMassProperty() {
        return mass;
    }

    public SimpleFloatProperty getVelocityProperty() {
        return velocity;
    }

    public Object(float mass) throws Exception {
        this.setMass(mass);
        this.setVelocity(0);
        System.out.println("Object is constructed");
    }
}
