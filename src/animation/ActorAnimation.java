package animation;

import base.Monitor;
import javafx.scene.image.ImageView;
import screen.Main;

public class ActorAnimation {
    private final ImageView standActor;
    private final ImageView leftActor;
    private final ImageView rightActor;
    private final Monitor monitor;
    private final ImageView imageOnRoad;
    private final int[] leftActorPara;
    private final int[] rightActorPara;

    public ActorAnimation(ImageView standActor, ImageView leftActor, ImageView rightActor, Monitor monitor, ImageView imageOnRoad,
                          int[] leftActorPara, int[] rightActorPara) {
        this.standActor = standActor;
        this.leftActor = leftActor;
        this.rightActor = rightActor;
        this.monitor = monitor;
        this.leftActorPara = leftActorPara;
        this.rightActorPara = rightActorPara;
        this.imageOnRoad = imageOnRoad;
        updateX((float) imageOnRoad.getScaleX());

        monitor.getActorForce().getValueProperty().addListener((observableValue, number, t1) -> update(t1.floatValue()));
        imageOnRoad.scaleXProperty().addListener((observableValue, number, t1) -> updateX(t1.floatValue()));
    }

    private void updateX(float scale){
        rightActor.setLayoutX(imageOnRoad.getLayoutX()+imageOnRoad.getFitWidth()*(1+(scale-1)/2));
        leftActor.setLayoutX(imageOnRoad.getLayoutX()-imageOnRoad.getFitWidth()*(scale-1)/2-leftActorPara[1]);
    }

    public void update(float forceValue){
        //call this method when the actor force value in monitor is changed
        float duration = 10*(100-Math.abs(forceValue)) + 200;
        if (forceValue == 0 || !monitor.isPlaying()){
            standActor.setVisible(true);
            leftActor.setVisible(false);
            rightActor.setVisible(false);
        } else if (forceValue > 0){
            leftActor.setVisible(true);
            SpriteTransition leftActorTransition = new SpriteTransition(leftActor, (int) duration,leftActorPara,Main.monitor);
            leftActorTransition.play();
            standActor.setVisible(false);
            rightActor.setVisible(false);
        } else if (forceValue < 0){
            rightActor.setVisible(true);
            SpriteTransition rightActorTransition = new SpriteTransition(rightActor, (int) duration,rightActorPara,Main.monitor);
            rightActorTransition.play();
            leftActor.setVisible(false);
            standActor.setVisible(false);
        }
    }
}
