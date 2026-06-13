package fr.univ_amu.iut.exercice5;

import fr.univ_amu.iut.exercice4.AireTriangle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * Exercice 5 - Calculatrice de triangle avec dessin.
 *
 * <p>Cet exercice réutilise la classe {@link AireTriangle} de l'exercice 4 comme modèle, et la
 * connecte à une interface graphique. Six sliders contrôlent les coordonnées des trois points,
 * l'aire est calculée automatiquement via les bindings du modèle, et le triangle est dessiné en
 * temps réel.
 *
 * <p>Concepts :
 *
 * <ul>
 *   <li>Binding entre objets : {@code slider.valueProperty()} -> {@code modele.x1Property()}
 *   <li>{@link GridPane} avec {@link ColumnConstraints}
 *   <li>{@link Slider} : configuration (min, max, tick marks, snap)
 *   <li>{@link Line} avec coordonnées liées au modèle (facteur d'échelle 50:1)
 * </ul>
 */
public class CalculatriceTriangle extends Application {

  private final AireTriangle modele = new AireTriangle();

  private final Slider sliderX1 = new Slider(0, 10, 0);
  private final Slider sliderY1 = new Slider(0, 10, 0);
  private final Slider sliderX2 = new Slider(0, 10, 0);
  private final Slider sliderY2 = new Slider(0, 10, 0);
  private final Slider sliderX3 = new Slider(0, 10, 0);
  private final Slider sliderY3 = new Slider(0, 10, 0);

  private final Label labelX1 = new Label("X1 :");
  private final Label labelY1 = new Label("Y1 :");
  private final Label labelX2 = new Label("X2 :");
  private final Label labelY2 = new Label("Y2 :");
  private final Label labelX3 = new Label("X3 :");
  private final Label labelY3 = new Label("Y3 :");

  private final Label labelP1 = new Label("P1");
  private final Label labelP2 = new Label("P2");
  private final Label labelP3 = new Label("P3");

  private final Label labelAire = new Label("Aire :");
  private final TextField textFieldAire = new TextField();

  private final Line ligneP1P2 = new Line();
  private final Line ligneP2P3 = new Line();
  private final Line ligneP3P1 = new Line();

  private final Pane panneauDessin = new Pane();

  private final GridPane grille = new GridPane();

  @Override
  public void start(Stage primaryStage) {
    configGrille();
    configSliders();
    ajouterSliders();
    ajouterAire();
    ajouterLabelsPoints();
    ajouterPanneauDessin();
    creerBindings();

    Scene scene = new Scene(grille);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  static void configSlider(Slider slider) {
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setMajorTickUnit(1);
    slider.setMinorTickCount(0);
    slider.setBlockIncrement(1);
    slider.setSnapToTicks(true);
  }

  void configSliders() {
    configSlider(sliderX1);
    sliderX1.setId("slider-x1");

    configSlider(sliderY1);
    sliderY1.setId("slider-y1");

    configSlider(sliderX2);
    sliderX2.setId("slider-x2");

    configSlider(sliderY2);
    sliderY2.setId("slider-y2");

    configSlider(sliderX3);
    sliderX3.setId("slider-x3");

    configSlider(sliderY3);
    sliderY3.setId("slider-y3");
  }

  void configGrille() {
    grille.setHgap(10);
    grille.setVgap(10);
    grille
        .getColumnConstraints()
        .addAll(new ColumnConstraints(200), new ColumnConstraints(200), new ColumnConstraints(200));
  }

  void ajouterSliders() {
    grille.add(labelX1, 0, 1);
    grille.add(sliderX1, 1, 1);

    grille.add(labelY1, 0, 2);
    grille.add(sliderY1, 1, 2);

    grille.add(labelX2, 0, 3);
    grille.add(sliderX2, 1, 3);

    grille.add(labelY2, 0, 4);
    grille.add(sliderY2, 1, 4);

    grille.add(labelX3, 0, 5);
    grille.add(sliderX3, 1, 5);

    grille.add(labelY3, 0, 6);
    grille.add(sliderY3, 1, 6);
  }

  void ajouterAire() {
    grille.add(labelAire, 0, 7);
    grille.add(textFieldAire, 1, 7);
    textFieldAire.setId("aire");
    textFieldAire.setEditable(false);
  }

  void ajouterLabelsPoints() {
    grille.add(labelP1, 0, 0);
    grille.add(labelP2, 1, 0);
    grille.add(labelP3, 2, 0);
  }

  void ajouterPanneauDessin() {
    panneauDessin.setId("dessin");
    panneauDessin.setPrefSize(500, 500);
    panneauDessin.getChildren().addAll(ligneP1P2, ligneP2P3, ligneP3P1);
    grille.add(panneauDessin, 0, 8, 3, 1);
  }

  void creerBindings() {
    modele.x1Property().bind(sliderX1.valueProperty());
    modele.y1Property().bind(sliderY1.valueProperty());
    modele.x2Property().bind(sliderX2.valueProperty());
    modele.y2Property().bind(sliderY2.valueProperty());
    modele.x3Property().bind(sliderX3.valueProperty());
    modele.y3Property().bind(sliderY3.valueProperty());

    textFieldAire.textProperty().bind(modele.areaProperty().asString());

    ligneP1P2.startXProperty().bind(modele.x1Property().multiply(50));
    ligneP1P2.startYProperty().bind(modele.y1Property().multiply(50));
    ligneP1P2.endXProperty().bind(modele.x2Property().multiply(50));
    ligneP1P2.endYProperty().bind(modele.y2Property().multiply(50));

    ligneP2P3.startXProperty().bind(modele.x2Property().multiply(50));
    ligneP2P3.startYProperty().bind(modele.y2Property().multiply(50));
    ligneP2P3.endXProperty().bind(modele.x3Property().multiply(50));
    ligneP2P3.endYProperty().bind(modele.y3Property().multiply(50));

    ligneP3P1.startXProperty().bind(modele.x3Property().multiply(50));
    ligneP3P1.startYProperty().bind(modele.y3Property().multiply(50));
    ligneP3P1.endXProperty().bind(modele.x1Property().multiply(50));
    ligneP3P1.endYProperty().bind(modele.y1Property().multiply(50));
  }

  public static void main(String[] args) {
    launch(args);
  }
}
