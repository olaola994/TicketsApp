
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.beans.binding.ObjectBinding;

import java.util.HashMap;

public class View {
    private VBox rootNode;
    private Label destinationLabel;
    private Label discountLabel;
    private Label payLabel;
    private Label destination;
    private Label discount;
    private Label price;

    private Label destinationComboLabel;

    private Label discountComboLabel;

    private ComboBox destinationComboBox;
    private ComboBox discountComboBox;

    public VBox getRootNode(){
        return rootNode;
    }
    public View(){
        rootNode = new VBox();

        HBox labelContainer = new HBox();

        Insets padding = new Insets(0, 10, 0, 10);

        destinationLabel = new Label("Destination");
        destinationLabel.setPadding(padding);

        discountLabel = new Label("discount");
        discountLabel.setPadding(padding);

        payLabel = new Label("you pay");
        payLabel.setPadding(padding);

        destination = new Label();
        destination.setPadding(padding);

        discount = new Label();
        discount.setPadding(padding);

        price = new Label();
        price.setPadding(padding);


        labelContainer.setAlignment(Pos.CENTER);
        labelContainer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        labelContainer.getChildren().add(destinationLabel, destination, discountLabel, discount, payLabel, price);

        HBox comboBoxContainer = new HBox();
        comboBoxContainer.setAlignment(Pos.CENTER);
        comboBoxContainer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        destinationComboLabel = new Label("Destination");
        destinationComboLabel.setPadding(padding);

        destinationComboBox = new ComboBox<>();
        destinationComboBox.setPadding(padding);
        HashMap<String, Double> mapDest = new HashMap<>();
        mapDest.put("Kalkota", 200.0);
        mapDest.put("Katowice", 140.5);
        mapDest.put("Warszawa", 99.99);
        mapDest.put("Gdansk", 78.0);

        HashMap<String, Double> mapDisc = new HashMap<>();
        mapDisc.put("Full price", 0.0);
        mapDisc.put("Senior", 0.2);
        mapDisc.put("Student", 0.5);

        destinationComboBox.getItems().addAll("Kalkota", "Katowice", "Warszawa", "Gdansk");
        discountComboLabel = new Label("Discount");
        discountComboLabel.setPadding(padding);

        discountComboBox = new ComboBox<>();
        destinationComboBox.setPadding(padding);
        discountComboBox.getItems().addAll("Full price", "Senior", "Student");

        destination.textProperty().bind(createDestinationBinding());
        discount.textProperty().bind(createDiscountBinding());
        price.textProperty().bind(createPayBinding(mapDisc,mapDest));

        comboBoxContainer.getChildren().addAll(destinationComboLabel, destinationComboBox, discountComboLabel,discountComboBox);

        rootNode.getChildren().add(labelContainer);
        rootNode.getChildren().add(comboBoxContainer);
    }
    private StringBinding createDestinationBinding() {
        return Bindings.createStringBinding(() -> {
            if (destinationComboBox.getValue() != null) {
                return destinationComboBox.getValue().toString() + ",";
            } else {
                return ""; // Domyślna wartość, jeśli nie ma wybranej wartości w ComboBox
            }
        }, destinationComboBox.valueProperty());
    }
    private StringBinding createDiscountBinding(){
        return Bindings.createStringBinding(()->{
            if(discountComboBox.getValue() != null){
                return "\"" + discountComboBox.getValue().toString() + "\"";
            }
            else{
                return "";
            }
        }, discountComboBox.valueProperty());
    }
    private StringBinding createPayBinding(HashMap<String, Double> mapDisc, HashMap<String, Double> mapDest){

        return Bindings.createStringBinding(()->{
            String dest = (String) destinationComboBox.getValue();
            String disc = (String) discountComboBox.getValue();

            if (dest != null && disc != null) {
                Double price = mapDest.get(dest);
                Double discPrice = mapDisc.get(disc);

                if (price != null && discPrice != null) {
                    double finalPrice = price * (1.0 - discPrice);
                    return String.format("%.2f", finalPrice);
                }
            }

            return null;
        }, discountComboBox.valueProperty(), destinationComboBox.valueProperty());
    }

}
