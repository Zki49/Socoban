package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;

import java.util.Arrays;

public class RulesHandling {
    private  BooleanBinding notContainsPlayer;
    private  BooleanBinding notContainsGoal;
    private  BooleanBinding notContainsBox;
    private  BooleanBinding containsWall;
    private  BooleanBinding containsError;
    private  CellDesign[][] cellDesigns;
    private  BooleanBinding boxIsNotEqualToGoal;

    public RulesHandling(CellDesign[][] cellDesigns) {
        this.cellDesigns = cellDesigns;
        createBidings();

    }
    public void createBidings() {
        /*notContainsPlayer.invalidate();
        notContainsGoal.invalidate();
        notContainsBox.invalidate();
        containsWall.invalidate();
        boxIsNotEqualToGoal.invalidate();
        cellWithObject.invalidate();
        containsError.invalidate();*/
        notContainsPlayer = Bindings.createBooleanBinding(() -> Arrays.stream(cellDesigns)
                .flatMap(Arrays::stream).filter(CellDesign::containsPlayer).count() == 0);
        notContainsBox = Bindings.createBooleanBinding(() -> Arrays.stream(cellDesigns)
                .flatMap(Arrays::stream).filter(CellDesign::containsBox).count() == 0);
        notContainsGoal = Bindings.createBooleanBinding(() -> Arrays.stream(cellDesigns)
                .flatMap(Arrays::stream).filter(CellDesign::containsGoal).count() == 0);
        containsWall = Bindings.createBooleanBinding(() -> Arrays.stream(cellDesigns)
                .flatMap(Arrays::stream).filter(CellDesign::containsWall).count() > 0);
        boxIsNotEqualToGoal = Bindings.createBooleanBinding(() -> Arrays.stream(cellDesigns)
                .flatMap(Arrays::stream).filter(CellDesign::containsGoal).count() != Arrays.stream(cellDesigns)
                .flatMap(Arrays::stream).filter(CellDesign::containsBox).count());

        containsError = Bindings.createBooleanBinding(() -> {
            if (notContainsPlayer.getValue() || notContainsGoal.getValue() || notContainsBox.getValue() || boxIsNotEqualToGoal.getValue()) {
                return true;
            }
            return false;
        });




    }
    public void invalidateBidings() {
         notContainsPlayer.invalidate();
        notContainsGoal.invalidate();
        notContainsBox.invalidate();
        containsWall.invalidate();
        boxIsNotEqualToGoal.invalidate();

        containsError.invalidate();
    }
    public Boolean getNotContainsPlayer() {
        return notContainsPlayer.get();
    }

    public BooleanBinding notContainsPlayerProperty() {
        return notContainsPlayer;
    }

    public Boolean getNotContainsGoal() {
        return notContainsGoal.get();
    }

    public BooleanBinding notContainsGoalProperty() {
        return notContainsGoal;
    }

    public Boolean getNotContainsBox() {
        return notContainsBox.get();
    }

    public BooleanBinding notContainsBoxProperty() {
        return notContainsBox;
    }

    public Boolean getContainsWall() {
        return containsWall.get();
    }

    public BooleanBinding containsWallProperty() {
        return containsWall;
    }

    public Boolean getBoxIsNotEqualToGoal() {
        return boxIsNotEqualToGoal.get();
    }

    public BooleanBinding boxIsNotEqualToGoalProperty() {
        return boxIsNotEqualToGoal;
    }

    public BooleanBinding getContaintErrorProperty() {
        return containsError;
    }
    public void changeMap(CellDesign[][] cellDesigns){
        this.cellDesigns = cellDesigns;
        createBidings();
    }
}
