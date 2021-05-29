package cls;

import javafx.beans.property.SimpleFloatProperty;

public class Cylinder extends Object {

    private float radius;
    private final float MAXIMUM_THRES = 1;
    SimpleFloatProperty angle = new SimpleFloatProperty(0);

    public Cylinder(float mass, float radius) throws Exception {
        super(mass);
        this.setRadius(radius);
        System.out.println("Cylinder is constructed");
    }

    public float getRadius() {
        return radius;
    }

    public float getMAXIMUM_THRES() {
        return MAXIMUM_THRES;
    }

    public float getAngle() {
        return angle.getValue();
    }

    public float getAngularVelocity() {
        return this.getVelocity() / this.radius;
    }

    public float getAngularAcceleration(Force force) {
        float a = this.getAcceleration(force);
        return a / this.radius;
    }

    public void setRadius(float radius) {
        if (radius <= this.MAXIMUM_THRES) {
            this.radius = radius;
            System.out.println("radius is now set to " + this.radius);
        }
        else {
            System.out.println("Exceed MAXIMUM_THRES");
        }
    }

    public void setAngle(float angle) {
        this.angle.setValue(angle);
        System.out.println("angle is now set to " + this.angle.getValue());
    }

    @Override
    public void applyForce(Force force, float time) {
        float oldAngularVelocity = this.getAngularVelocity();
        super.applyForce(force, time);
        float newAngularVelocity = this.getAngularVelocity();
        float a = this.getAngularAcceleration(force);
        if (a != 0) {
            this.setAngle(this.angle.getValue() + (newAngularVelocity * newAngularVelocity - oldAngularVelocity * oldAngularVelocity) / (2 * a));
        }
    }
    public SimpleFloatProperty getAngleProperty(){
        return angle;
    }

}
