package cls;

import javafx.beans.property.FloatProperty;

public class Force {
    private float value;
    //Constructor
    public Force (float value) {
        this.value = value;
    }
    public Force() {
    }
    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value= value;
    }
    public Force plus(Force other) {
        return new Force(this.value + other.getValue());
    }

}
