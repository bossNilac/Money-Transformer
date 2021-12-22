package app.controller;

import app.DataObject;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.ResourceBundle;

import static app.MoneyTransformer.exitApp;
import static app.MoneyTransformer.writeLog;
import static app.controller.MoneyTransformerController.*;

public class CalculatorTabController implements Initializable {
    public Button calculateDobandaButon;
    public ImageView mainImageViewCalculatorTab;
    public TextField interestRateInput;
    public TextField depositInput;
    public TextField daysInput;
    public Label resultLabelCalculatorTab;
    public Pane composedInterestRateCalcPane;
    public Pane basicInterestRateCalcPane;
    public TextField depositInput2;
    public TextField interestRateInput2;
    public TextField yearInput;
    public Button calculateDobanda2Buton;
    public Label resultLabelCalculatorTab2;
    public Button exitButtonCalc;
    public Button mainmenuButtonCalc;
    public TextField periodInput;
    public SplitMenuButton interestRateButton;
    public Label calculatorLabel;
    public AnchorPane main;

    public void initialize(URL location, ResourceBundle resourceBundle) {
        composedInterestRateCalcPane.setVisible(false);
        basicInterestRateCalcPane.setVisible(false);
        buttons.add(exitButtonCalc);
        buttons.add(mainmenuButtonCalc);
        buttons.add(calculateDobanda2Buton);
        buttons.add(calculateDobandaButon);

        panes.add(composedInterestRateCalcPane);
        panes.add(basicInterestRateCalcPane);

        labels.add(resultLabelCalculatorTab);
        labels.add(resultLabelCalculatorTab2);
        labels.add(calculatorLabel);

        images.add(mainImageViewCalculatorTab);

        calcTabTranslation(DataObject.settingsObj.language);
        if (DataObject.settingsObj.theme.equals("dark")) {
            toDarkMode();
            interestRateButton.setStyle("-fx-text-fill: #fdc68a ;-fx-background-color: transparent");
            main.setStyle("-fx-background-color: #000000");

        } else {
            toLightMode();
            interestRateButton.setStyle("-fx-text-fill: #ebebeb ;-fx-background-color: transparent");
            main.setStyle("-fx-background-color: #ffffff");

        }
    }

    public double calculeazaDobanda(String valNom,String nrZile,String rata){
        return (Double.parseDouble(valNom)*Double.parseDouble(nrZile)*Double.parseDouble(rata))/(360*100);
    }
    public double calculeazaDobandaCompusa(double deposit,double rate,double perioada,double nr_zile_an,double nr_perioade){
        return deposit* (1 + rate * perioada / nr_zile_an)*nr_perioade;//nr_perioade=ani,perioada ex:30=luna
    }


    public void calculateDobanda(ActionEvent actionEvent) {
        resultLabelCalculatorTab.setText("Result:"+ calculeazaDobanda(depositInput.getText(),daysInput.getText(),interestRateInput.getText()));
    }

    public void toBasicPane(ActionEvent actionEvent) {
        composedInterestRateCalcPane.setVisible(false);
        depositInput.setText("");
        daysInput.setText("");
        interestRateInput.setText("");
        basicInterestRateCalcPane.setVisible(true);
    }

    public void toComposedPane(ActionEvent actionEvent) {
        composedInterestRateCalcPane.setVisible(true);
        depositInput2.setText("");
        yearInput.setText("");
        interestRateInput2.setText("");
        periodInput.setText("");
        basicInterestRateCalcPane.setVisible(false);
    }

    public void calculateDobandaComposed(ActionEvent actionEvent) {
        resultLabelCalculatorTab2.setText("Result:"+calculeazaDobandaCompusa(Double.parseDouble(
                depositInput2.getText())//deposit
                ,Double.parseDouble(interestRateInput2.getText())//rata
                ,Double.parseDouble(periodInput.getText())//perioada
                ,365//nr zile an
                ,Double.parseDouble(yearInput.getText())));
    }

    public void ExitFromCalc(ActionEvent actionEvent) {
        exitApp();
    }

    public void toMainMenuFromCalc(ActionEvent actionEvent) {
        writeLog("Went back to main window from transform window");
        Node node = (Node) actionEvent.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
        thisStage.setAlwaysOnTop(true);
        thisStage.setAlwaysOnTop(false);
    }

    public double calculateInflation() {
        return 0.0;
    }

    public  void calcTabTranslation(String language) {
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
                        calculateDobandaButon.setText(tElement.getElementsByTagName("convertText").item(0).getTextContent());
                        calculateDobanda2Buton.setText(tElement.getElementsByTagName("convertText").item(0).getTextContent());
                        mainmenuButtonCalc.setText(tElement.getElementsByTagName("mainMenuText").item(0).getTextContent());
                        exitButtonCalc.setText(tElement.getElementsByTagName("exitText").item(0).getTextContent());
                        interestRateButton.setText(tElement.getElementsByTagName("interestRateText").item(0).getTextContent());
                        calculatorLabel.setText(tElement.getElementsByTagName("calculatorTabText").item(0).getTextContent());

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
