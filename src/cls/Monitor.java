package cls;

public class Monitor {
    private Object obj = null;
    private boolean playing = true;
    private Surface surface;
    private Force actor;


    public Monitor(Object obj, Surface surface, Force actor) {
        this.obj = obj;
        this.surface = surface;
        this.actor = actor;
    }

    public boolean isEmpty(){
        return obj == null;
    }
    public void setObj(Object newObj) throws Exception {
        if (isEmpty()){
            obj = newObj;
        } else {
            throw new Exception("Object has been already exist");
        }
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
        if ((Cube) obj instanceof Cube ) {
            if (actor.getValue() <= (normalForce*surface.getStaticFrictionCoef())) {
                frictionForce.setValue(actor.getValue());
            } else {
                frictionForce.setValue(normalForce*surface.getKineticFrictionCoef());
            }
        } else if ((Cylinder) obj instanceof Cylinder) {
            if (actor.getValue() <= (3*normalForce*surface.getStaticFrictionCoef())) {
                frictionForce.setValue(actor.getValue()/3);
            } else {
                frictionForce.setValue(normalForce*surface.getKineticFrictionCoef());
            }
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

    }
}
