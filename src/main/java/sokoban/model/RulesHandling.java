package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;

import java.util.Arrays;

class RulesHandling {
    private  BooleanBinding notContainsPlayer;
    private  BooleanBinding notContainsGoal;
    private  BooleanBinding notContainsBox;
    private  BooleanBinding containsWall;
    private  BooleanBinding containsError;
    private  CellDesign[][] cellDesigns;
    private  BooleanBinding boxIsNotEqualToGoal;

    RulesHandling(CellDesign[][] cellDesigns) {
        this.cellDesigns = cellDesigns;
        createBidings();

    }
     void createBidings() {
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
     void invalidateBidings() {
         notContainsPlayer.invalidate();
        notContainsGoal.invalidate();
        notContainsBox.invalidate();
        containsWall.invalidate();
        boxIsNotEqualToGoal.invalidate();

        containsError.invalidate();
    }
     Boolean getNotContainsPlayer() {
        return notContainsPlayer.get();
    }

     BooleanBinding notContainsPlayerProperty() {
        return notContainsPlayer;
    }

     Boolean getNotContainsGoal() {
        return notContainsGoal.get();
    }

     BooleanBinding notContainsGoalProperty() {
        return notContainsGoal;
    }

     Boolean getNotContainsBox() {
        return notContainsBox.get();
    }

     BooleanBinding notContainsBoxProperty() {
        return notContainsBox;
    }

     Boolean getContainsWall() {
        return containsWall.get();
    }

     BooleanBinding containsWallProperty() {
        return containsWall;
    }

     Boolean getBoxIsNotEqualToGoal() {
        return boxIsNotEqualToGoal.get();
    }

     BooleanBinding boxIsNotEqualToGoalProperty() {
        return boxIsNotEqualToGoal;
    }

     BooleanBinding getContaintErrorProperty() {
        return containsError;
    }
     void changeMap(CellDesign[][] cellDesigns){
        this.cellDesigns = cellDesigns;
        createBidings();
    }
}
