package app.controller;

import app.DataObject;
import app.MoneyTransformer;
import app.XlsParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static app.MoneyTransformer.writeLog;
import static app.controller.MoneyTransformerController.*;

public class GraphTabController implements Initializable {
    public Button exitButtonGraphTab;
    public Button mainmenuButtonGraphTab;
    public MenuButton tableInfoButton;
    public ImageView imageviewGraphTab;
    public Button moreButton;
    public LineChart inflationChart;
    public LineChart ronToUsdChart;
    public LineChart ronToEurChart;
    public Pane graphPane;
    public XYChart.Series ronInfSeries;
    public XYChart.Series ronToUsdSeries;
    public XYChart.Series ronToEurSeries;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        inflationChart.setLegendVisible(false);
        ronToUsdChart.setLegendVisible(false);
        ronToEurChart.setLegendVisible(false);

        MoneyTransformerController.buttons.add(exitButtonGraphTab);
        MoneyTransformerController.buttons.add(mainmenuButtonGraphTab);
        MoneyTransformerController.buttons.add(moreButton);
        MoneyTransformerController.panes.add(graphPane);
        MoneyTransformerController.images.add(imageviewGraphTab);

        graphTabTranslation(DataObject.settingsObj.language);

        if (DataObject.settingsObj.theme.equals("dark")) {
            toDarkMode();
        } else {
            toLightMode();
        }

        for (int i = 0; i < XlsParser.tableKeys.size(); i++) {
            System.out.println(XlsParser.tableKeys.get(i));
        }

        ronToEurSeries = new XYChart.Series();
        MoneyTransformer.euroArchiveMap.entrySet().forEach(entry -> {
            ronToEurSeries.getData().add(new XYChart.Data(entry.getKey(), Double.valueOf(entry.getValue().replace(",", "."))));
        });
        ronToEurChart.getData().add(ronToEurSeries);

        ronInfSeries = new XYChart.Series();
        for (int i = 0; i < XlsParser.tableValues.size(); i++) {
            ronInfSeries.getData().add(new XYChart.Data(XlsParser.tableKeys.get(i), XlsParser.tableValues.get(i)));
        }
        inflationChart.getData().add(ronInfSeries);

        ronToUsdSeries = new XYChart.Series();
        MoneyTransformer.dolarArchiveMap.entrySet().forEach(entry ->
            ronToUsdSeries.getData().add(new XYChart.Data(entry.getKey(), Double.valueOf(entry.getValue().replace(",", "."))))
        );//lambda cu o instructiune
        ronToUsdChart.getData().add(ronToUsdSeries);

        inflationChart.setVisible(false);
        ronToUsdChart.setVisible(false);
        ronToEurChart.setVisible(false);

    }

    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        hideStage(actionEvent);
        getStage(actionEvent).setAlwaysOnTop(true);
        getStage(actionEvent).setAlwaysOnTop(false);
    }

    public void ExitTransform(ActionEvent actionEvent) {
        MoneyTransformer.exitApp();
    }

    public void toTransformTab(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/resources/fxml/TransformTab.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage2 = new Stage();
        stage2.setTitle("Money Transformer");
        stage2.getIcons().add(new Image("app/logo_black.png"));
        stage2.setScene(new Scene(root));
        hideStage(actionEvent);
        stage2.show();
    }

    public void changeToRon(ActionEvent actionEvent) {
        inflationChart.setVisible(true);
        ronToUsdChart.setVisible(false);
        ronToEurChart.setVisible(false);
    }

    public static void hideStage(ActionEvent actionEvent) {
        writeLog("Went back to main window from transform window");
        getStage(actionEvent).close();
    }

    public static Stage getStage(ActionEvent ae) {
        Node node = (Node) ae.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        return thisStage;
    }

    public void changeToRontoUSD(ActionEvent actionEvent) {
        inflationChart.setVisible(false);
        ronToUsdChart.setVisible(true);
        ronToEurChart.setVisible(false);
    }

    public void changeToRontoEUR(ActionEvent actionEvent) {
        inflationChart.setVisible(false);
        ronToUsdChart.setVisible(false);
        ronToEurChart.setVisible(true);
    }

    public  void graphTabTranslation(String language) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(languageFile);

            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList nodeList = doc.getElementsByTagName("language");

            for (int i = 0; i < nodeList.getLength(); ++i) {
                org.w3c.dom.Node node = nodeList.item(i);
                System.out.println("\nNode Name :" + node.getNodeName());

                if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    Element tElement = (Element) node;

                    System.out.println(tElement.getElementsByTagName("id").item(0).getTextContent().startsWith(language.toLowerCase()));
                    if (tElement.getElementsByTagName("id").item(0).getTextContent().startsWith(language.toLowerCase())) {
                        System.out.println("changing language...");
                        mainmenuButtonGraphTab.setText(tElement.getElementsByTagName("mainMenuText").item(0).getTextContent());
                        exitButtonGraphTab.setText(tElement.getElementsByTagName("exitText").item(0).getTextContent());
                        tableInfoButton.setText(tElement.getElementsByTagName("selectTableInfoText").item(0).getTextContent());
                        moreButton.setText(tElement.getElementsByTagName("conversionTabText").item(0).getTextContent());

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
