package cls;

public abstract class Object {

    private float mass;
    private float velocity;

    public float getMass() {
        return mass;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setMass(float mass) throws Exception {
        if (mass > 0) {
            this.mass = mass;
            System.out.println("mass is now set to " + this.mass);
        } else {
            throw new Exception("Object's mass must > 0");
        }
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
        System.out.println("velocity is now set to " + this.velocity);
    }

    public void applyForce(Force force, float time) {
        System.out.println("Applying force " + force.getValue() + " in time " + time);
        float a = this.getAcceleration(force);
        this.setVelocity(this.velocity + a * time);
    }

    public float getAcceleration(Force force) {
        return force.getValue() / mass;
    }

    public Object(float mass) throws Exception {
        this.setMass(mass);
        this.setVelocity(0);
        System.out.println("Object is constructed");
    }
}
