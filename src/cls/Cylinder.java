package cls;

public class Cylinder extends Object {

    private float radius;
    private final float MAXIMUM_THRES = 1;
    float angle;

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
        return angle;
    }

    public float getAngularVelocity() {
        return this.getVelocity() / this.radius;
    }

    public float getAngularAcceleration(Force force) {
        float a = this.getAcceleration(force);
        return a / this.radius;
    }

    public void setRadius(float radius) {
        if (radius < this.MAXIMUM_THRES) {
            this.radius = radius;
            System.out.println("radius is now set to " + this.radius);
        }
        else {
            System.out.println("Exceed MAXIMUM_THRES");
        }
    }

    public void setAngle(float angle) {
        this.angle = angle;
        System.out.println("angle is now set to " + this.angle);
    }

    public void applyForce(Force force, float time) {
        float oldAngularVelocity = this.getAngularVelocity();
        super.applyForce(force, time);
        float newAngularVelocity = this.getAngularVelocity();
        float a = this.getAngularAcceleration(force);
        this.setAngle(this.angle + (newAngularVelocity * newAngularVelocity - oldAngularVelocity * oldAngularVelocity) / (2 * a));
    }

}
