package base;

public class FrictionForce extends Force {
    private Monitor monitor;

    public void addObjListener() {
        try {
            if (!monitor.isEmpty()) {
                monitor.getObj().getMassProperty().addListener(observable -> {
                    try {
                        updateValue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                monitor.getObj().getVelocityProperty().addListener(observable -> {
                    try {
                        updateValue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FrictionForce(float value, Monitor monitor) {
        super(value);
        this.monitor = monitor;

        try {
            addObjListener();
            monitor.getSurface().getStaticCoefProperty().addListener(observable -> {
                try {
                    updateValue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            monitor.getSurface().getKineticCoefProperty().addListener(observable -> {
                try {
                    updateValue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            monitor.getActorForce().getValueProperty().addListener(observable -> {
                try {
                    updateValue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void updateValue() throws Exception {
        float normalForce = 10 * monitor.getObj().getMass();
        if (monitor.getObj() instanceof Cube) {
            if (Math.abs(monitor.getActorForce().getValue()) <= (normalForce * monitor.getSurface().getStaticFrictionCoef())) {
                if (Math.abs(monitor.getObj().getVelocity()) <= 0.1) {
                    monitor.getObj().setVelocity(0);
                    setValue(-monitor.getActorForce().getValue());
                } else {
                    setValue((-Math.signum(monitor.getObj().getVelocity())) * normalForce * monitor.getSurface().getKineticFrictionCoef());
                }
            } else {
                setValue((-Math.signum(monitor.getObj().getVelocity())) * normalForce * monitor.getSurface().getKineticFrictionCoef());
            }
        } else if (monitor.getObj() instanceof Cylinder) {
            if (Math.abs(monitor.getActorForce().getValue()) <= (3 * normalForce * monitor.getSurface().getStaticFrictionCoef())) {
                if (Math.abs(monitor.getObj().getVelocity()) <= 0.1) {
                    monitor.getObj().setVelocity(0);
                    setValue(-monitor.getActorForce().getValue() / 3);
                } else {
                    setValue((-Math.signum(monitor.getObj().getVelocity())) * normalForce * monitor.getSurface().getKineticFrictionCoef());
                }
            } else {
                setValue((-Math.signum(monitor.getObj().getVelocity())) * normalForce * monitor.getSurface().getKineticFrictionCoef());
            }
        }
    }
}
