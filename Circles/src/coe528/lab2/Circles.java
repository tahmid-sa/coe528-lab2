/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe528.lab2;

//Import the correct and necessary libraries needed for the JavaFX application
import static java.lang.String.valueOf;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Lab #2 file.
 * @author Tahmid Sajin, Student number: 500830210, Section 2 of COE528
 */
public class Circles extends Application {
    //Declare all the final values for the rows, columns, cell size, xscale and yscale that is 
    //default when the application is started. Note the variables XSCALE and YSCALE were added and 
    //all were public to start with from the Circles.java application
    public static final int ROWS = 4;
    public static final int COLS = 5;
    public static final int CELL_SIZE = 100;
    public static final int XSCALE = 0;
    public static final int YSCALE = 0;
    
    //Declare BorderPane layout. Declare the HBox, VBox and Circle nodes to be used.
    private BorderPane canvas = new BorderPane();
    private HBox hboxCircle;
    private VBox vboxCircle;
    private Circle circle;
    
    @Override
    public void start(Stage primaryStage) {
        //Initially the application will jump to updateHandler, which will update the circles to the control's
        //default values. Also the canvas size will be set.
        updateHandler(ROWS, COLS, CELL_SIZE, XSCALE, YSCALE);
        canvas.setPrefSize(COLS * CELL_SIZE, ROWS * CELL_SIZE);

        //Create the labels for the controls
        Label labelRows = new Label("Rows");
        Label labelCols = new Label("Columns");
        Label labelCellSize = new Label("Cell Size");
        Label labelXScale = new Label("X Scale");
        Label labelYScale = new Label("Y Scale");
          
        //Create the overall controls: two spinners and one slider as shown.
        Spinner spinnerRows = new Spinner(0, 5, ROWS, 1);
        spinnerRows.setPrefWidth(70);

        Spinner spinnerCols = new Spinner(0, 5, COLS, 1);
        spinnerCols.setPrefWidth(70);

        Slider slider = new Slider(50, 150, CELL_SIZE);
        Label sliderValue = new Label(valueOf((int)slider.getValue()));

        Spinner spinnerXScale = new Spinner(-3, 3, 0, 1);
        spinnerXScale.setPrefWidth(70);

        Spinner spinnerYScale = new Spinner(-3, 3, 0, 1);
        spinnerYScale.setPrefWidth(70);
        
        //Create the grid layout to be used for the controls. Add alignment, spacing, padding as shown:
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.BOTTOM_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 0, 0, 0));

        //Add the labels to the grid and set their alignments accordingly to the grid.
        grid.add(labelRows, 0, 0);
        grid.setHalignment(labelRows, HPos.CENTER);
        grid.add(labelCols, 1, 0);
        grid.setHalignment(labelCols, HPos.CENTER);
        grid.add(labelCellSize, 2, 0);
        grid.setHalignment(labelCellSize, HPos.CENTER);
        grid.add(labelXScale, 4, 0);
        grid.setHalignment(labelXScale, HPos.CENTER);
        grid.add(labelYScale, 5, 0);
        grid.setHalignment(labelYScale, HPos.CENTER);

        //Add the two spinners and one slider controls to the grid as shown and position them in their columns, rows format.
        grid.add(spinnerRows, 0, 1);
        grid.add(spinnerCols, 1, 1);
        grid.add(slider, 2, 1);
        grid.add(sliderValue, 3, 1);
        grid.add(spinnerXScale, 4, 1);
        grid.add(spinnerYScale, 5, 1);

        //Set the grid lines visibility to false, set the grid to be put in the bottom and add the canvas to the scene.
        grid.setGridLinesVisible(false);
        canvas.setBottom(grid);
        Scene scene = new Scene(canvas);       
        
        //The following are the event handlers for the Row and Column spinners which will handle if the values change.
        //It will override and jump to the updateHandler method by sending the 5 parameters:
        //-number of rows
        //-number of columns
        //-slider value or cell size
        //-xscale value
        //-yscale value
        spinnerRows.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateHandler((int)spinnerRows.getValue(), (int)spinnerCols.getValue(), (int)slider.getValue(), (int)spinnerXScale.getValue(), (int)spinnerYScale.getValue());
            }
        });
          
        spinnerCols.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateHandler((int)spinnerRows.getValue(), (int)spinnerCols.getValue(), (int)slider.getValue(), (int)spinnerXScale.getValue(), (int)spinnerYScale.getValue());
            }
        });

        //The following is the event handler for the slider will will handle if the cell size changes.
        //The slider value is also shown beside the slider
        //It will override and jump to the updateHandler method by sending the 5 parameters:
        //-number of rows
        //-number of columns
        //-slider value or cell size
        //-xscale value
        //-yscale value
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sliderValue.setText(valueOf((int)slider.getValue()));
                updateHandler((int)spinnerRows.getValue(), (int)spinnerCols.getValue(), (int)slider.getValue(), (int)spinnerXScale.getValue(), (int)spinnerYScale.getValue());
            }
        });

        //The following are the event handlers xscale and yscale spinners which will handle if the values change.
        //It will override and jump to the updateHandler method by sending the 5 parameters:
        //-number of rows
        //-number of columns
        //-slider value or cell size
        //-xscale value
        //-yscale value
        spinnerXScale.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateHandler((int)spinnerRows.getValue(), (int)spinnerCols.getValue(), (int)slider.getValue(), (int)spinnerXScale.getValue(), (int)spinnerYScale.getValue()); 
            }
        });

        spinnerYScale.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateHandler((int)spinnerRows.getValue(), (int)spinnerCols.getValue(), (int)slider.getValue(), (int)spinnerXScale.getValue(), (int)spinnerYScale.getValue());
            }
        });
          
        //This allows the scene to show and update the title of the scene as shown.
        primaryStage.setTitle("Lab #2 Exercise");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * This method adds the handler to the controllers that gives
     * this application its behavior.
     */
    private void updateHandler(int rows, int cols, int cellSize, int xScale, int yScale) {
        //First remove the vboxCircle or any circles in the canvas before beginning and create a new vboxCircle for the circles
        canvas.getChildren().remove(vboxCircle);
        vboxCircle = new VBox(10);

        //The for loop will loop through each indicated, row and column and generate a hbox filled with circles with the 
        //column number and repeat for the number of rows as shown.
        //A random color is also generated when the updateHandler is called from the controllers.
        //The controllers can change as follows through this for loop:
        //-number of rows and columns
        //-cell size of each cell of the circle
        //-the x and y scale transition (even a negative value)
        for (int r = 1; r <= rows; r++) {
            canvas.getChildren().remove(hboxCircle);
            hboxCircle = new HBox(10);
            hboxCircle.setPadding(new Insets(10, 10, 10, 10));
            hboxCircle.setAlignment(Pos.TOP_LEFT);

            for (int c = 1; c<= cols; c++) {
                circle = new Circle((cellSize / 5));   //The cell size 150 was too big for my screen, therefore I adjusted it
                circle.setFill(Color.color(Math.random(), Math.random(), Math.random()));
                
                //Implement the translation transition
                double toXValue = cols * cellSize + cellSize / 3D;
                double toYValue = rows * cellSize + cellSize / 3D;
                double fromXValue = cols * cellSize - cellSize / 3D;
                double fromYValue = rows * cellSize - cellSize / 3D;
                circle.setCenterX(fromXValue);
                circle.setCenterY(fromYValue);
                hboxCircle.getChildren().addAll(circle);
                
                TranslateTransition transitionTranslate = new TranslateTransition(Duration.seconds(0.5));
                transitionTranslate.setNode(circle);
                transitionTranslate.setByX(toXValue - fromXValue);
                transitionTranslate.setByY(toYValue - fromYValue);
                
                transitionTranslate.setCycleCount(2);
                transitionTranslate.setAutoReverse(true);
                transitionTranslate.play();
                    
                //Implement the scale transition
                ScaleTransition transitionScale = new ScaleTransition(Duration.seconds(1), circle);

                transitionScale.setByX(xScale);
                transitionScale.setByY(yScale);
                transitionScale.setCycleCount(ScaleTransition.INDEFINITE);
                transitionScale.setAutoReverse(true);

                transitionScale.play();
            }

            vboxCircle.setAlignment(Pos.TOP_LEFT);
            vboxCircle.getChildren().addAll(hboxCircle);
        }
        //Finally the vboxCircle containing the hboxCircle which thereby contains the circles are added to the canvas to appear
        canvas.getChildren().add(vboxCircle);
    }
    /**
     * @param args the command line arguments
     * The following code is responsible for actually running the program
     */
    public static void main(String[] args) {
        launch(args);
    }
}