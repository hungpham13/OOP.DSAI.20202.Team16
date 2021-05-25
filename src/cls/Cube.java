package cls;

public class Cube extends Object {

    private float sizeLength;
    private float MAXIMUM_THRES = 1;

    public Cube(float mass, float sizeLength) {
        super(mass);
        this.setSizeLength(sizeLength);
        System.out.println("Cube is constructed");
    }

    public float getSizeLength() {
        return sizeLength;
    }

    public float getMAXIMUM_THRES() {
        return MAXIMUM_THRES;
    }

    public void setSizeLength(float sizeLength) {
        if (sizeLength < this.MAXIMUM_THRES) {
            this.sizeLength = sizeLength;
            System.out.println("sizeLength is now set to " + this.sizeLength);
        }
        else {
            System.out.println("Exceed MAXIMUM_THRES");
        }
    }
}
