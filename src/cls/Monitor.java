package cls;

public class Monitor {
    private Object obj = null;
    private boolean playing;
    private Surface surface;
    private Force actor;

    public boolean isEmpty(){
        return obj == null;
    }
    public void setObj(Object newObj){
        obj = newObj;
    }
    public Object getObj(){
        return obj;
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
