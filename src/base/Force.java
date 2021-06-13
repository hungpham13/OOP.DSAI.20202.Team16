package base;

import javafx.beans.property.SimpleFloatProperty;

public class Force {
    private final SimpleFloatProperty value = new SimpleFloatProperty();
    //Constructor
    public Force (float value) {
        setValue(value);
    }
    public Force() {
    }
    public float getValue() {
        return value.getValue();
    }
    public SimpleFloatProperty getValueProperty(){
        return value;
    }

    public void setValue(float value) {
        this.value.setValue(value);
    }
    public Force plus(Force other) {
        return new Force(getValue() + other.getValue());
    }

}
