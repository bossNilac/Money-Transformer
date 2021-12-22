package app.controller;

import app.CryptoApi;
import app.DataObject;
import app.MoneyTransformer;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import static app.FavouritesCurr.*;
import static app.MoneyTransformer.*;
import static app.CryptoApi.cryptoArr;
import static app.controller.MoneyTransformerController.*;

public class TransformTabController  implements Initializable {

    public VBox vbox;
    public Pane transMainPane;
    public ListView listView1;
    public ListView listView2;
    public Label resultLabel;
    public TextField modifierTextField;
    public Button transformButton;
    public Button exitButtonTransformPane;
    public Button mainMenuButtonTransformPane;
    public ImageView imageViewTransformPane;
    public Button moreButton;
    public AnchorPane main;

    Image emptyStarImg =new Image("app/empty_star.png");
    Image starImg =new Image("app/star.png");


    public static final KeyCombination saveShortcut = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
    public MenuButton dropdownMenuCrypto2;
    public ListView cryptoListResults;
    public MenuButton dropdownMenuCrypto1;
    public Label toBConvLabelCryptoPane;
    public Label convertLabelCryptoPane;
    public Button favButton2;
    public ImageView starImageView2;
    public Button favButton1;
    public ImageView starImageView;



    static DecimalFormat resultDecimalFormat = new DecimalFormat("#.####");
    public static DecimalFormat displayDecimalFormat = new DecimalFormat("#.####");
    static DecimalFormat cryptoDisplayFormat = new DecimalFormat("#.############");


    static String separator = "       ";
    static String space = " ";
    public static Double fiatMoneyValues[] = MoneyTransformer.currencies.values().toArray(new Double[0]);
    public static String fiatMoneyKeys[] = MoneyTransformer.currencies.keySet().toArray(new String[0]);



    //logo colors:crem:#FDC68A
    //logo colors:grey:#EBEBEB
    //turquise:#1ABBB4
    //dark grey:#2b2b2b

    @Override
    @SuppressWarnings({"unchecked"})
    public void initialize(URL location, ResourceBundle resourceBundle) {
        buttons.add(transformButton);
        buttons.add(exitButtonTransformPane);
        buttons.add(mainMenuButtonTransformPane);
        buttons.add(moreButton);

        labels.add(resultLabel);
        labels.add(convertLabelCryptoPane);
        labels.add(toBConvLabelCryptoPane);



        panes.add(transMainPane);

        images.add(imageViewTransformPane);
        System.out.println("Theme:"+DataObject.settingsObj.theme);
        if(DataObject.settingsObj.theme.equals("dark")){
            toDarkMode();
        }
        if(DataObject.settingsObj.theme.equals("light")){
            toLightMode();

        }

        transformTabTranslation(DataObject.settingsObj.language);



        for (int i = 0; i < fiatMoneyValues.length; i++) {
            if (fiatMoneyValues[i] < 10) {
                listView1.getItems().add(fiatMoneyKeys[i] + separator + space + space + displayDecimalFormat.format(fiatMoneyValues[i]));
            } else {
                if (fiatMoneyValues[i] < 100) {
                    listView1.getItems().add(fiatMoneyKeys[i] + separator + space + displayDecimalFormat.format(fiatMoneyValues[i]));
                } else {
                    if (fiatMoneyValues[i] > 100) {
                        listView1.getItems().add(fiatMoneyKeys[i] + separator + displayDecimalFormat.format(fiatMoneyValues[i]));
                    }
                }
            }

            if (fiatMoneyValues[i] < 10) {
                listView2.getItems().add(fiatMoneyKeys[i] + separator + space + space + displayDecimalFormat.format(fiatMoneyValues[i]));
            } else {
                if (fiatMoneyValues[i] < 100) {
                    listView2.getItems().add(fiatMoneyKeys[i] + separator + space + displayDecimalFormat.format(fiatMoneyValues[i]));
                } else {
                    if (fiatMoneyValues[i] > 100) {
                        listView2.getItems().add(fiatMoneyKeys[i] + separator + displayDecimalFormat.format(fiatMoneyValues[i]));
                    }
                }
            }
        }


        Thread listViewChecker = new Thread(() -> {
            while (true) {
                if (listView1.getSelectionModel().getSelectedItem() == null || listView2.getSelectionModel().getSelectedItem() == null) {
                    resultLabel.setVisible(false);
                } else {
                    resultLabel.setVisible(true);
                }
            }
        });
        listViewChecker.start();

//        menuItemL1.setOnAction(event -> {
//            changeValueToCurrency(menuItemL1.getText());
//        });



//        MoneyTransformerController.MainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                switch (event.getCode()) {
//                    case UP:
//                        System.out.println("up");    break;
//                }
//            }
//        });

        listView1.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + listView1.getSelectionModel().getSelectedItem());
                String code=listView1.getSelectionModel().getSelectedItem().toString().replaceAll("\\d","").replace(" ","").replace(".","");
                System.out.println("Is "+code+"favourite:"+isFavourite(code));
                System.out.println("File contents:"+readFile());
                if(isFavourite(code)){
                    starImageView.setImage(starImg);
                    favButton1.setText("Favourite");
                }else{
                    starImageView.setImage(emptyStarImg);
                    favButton1.setText("Not Favourite");
                }
            }
        });
        listView2.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + listView2.getSelectionModel().getSelectedItem());
                String code=listView2.getSelectionModel().getSelectedItem().toString().replaceAll("\\d","").replace(" ","").replace(".","");
                System.out.println("Is "+code+"favourite:"+isFavourite(code));
                System.out.println("File contents:"+readFile());
                if(isFavourite(code)){
                    starImageView2.setImage(starImg);
                    favButton2.setText("Favourite");
                }else{
                    starImageView2.setImage(emptyStarImg);
                    favButton2.setText("Not Favourite");
                }
            }
        });

    }

    public void convertCurrencies(ActionEvent actionEvent) {
        cryptoListResults.getItems().add("Converted "
                +listView1.getSelectionModel().getSelectedItem().toString().replaceAll("\\d", "").replace(" ","").replace(".","")+"/"
                +listView2.getSelectionModel().getSelectedItem().toString().replaceAll("\\d", "").replace(" ","").replace(".","")+" to "
                +convertCurrenciesToText(Double.parseDouble(
                listView1.getSelectionModel().getSelectedItem().toString().replaceAll("[^\\d.]", "")),
                getModifier()
                ,Double.parseDouble(listView2.getSelectionModel().getSelectedItem().toString().replaceAll("[^\\d.]", ""))));

        writeLog("Converted " + MoneyTransformer.firstXChars((String) listView1.getSelectionModel().getSelectedItem(), 3) + " to " + MoneyTransformer.firstXChars((String) listView2.getSelectionModel().getSelectedItem(), 3) + " (" + resultLabel.getText() + ")");
    }

    public void ExitTransform(ActionEvent actionEvent) {
        MoneyTransformer.exitApp();
    }

    public void toMainMenu(ActionEvent actionEvent) {
        writeLog("Went back to main window from transform window");
        Node node = (Node) actionEvent.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

    public void copyLabelText(MouseEvent mouseEvent) {
        try {
            SystemTray tray = SystemTray.getSystemTray();
            java.awt.Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
            TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("System tray icon demo");
            tray.add(trayIcon);
            trayIcon.displayMessage("Money Transformer", "The result has been copied to your clipboard", TrayIcon.MessageType.INFO);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        MoneyTransformerController.copyToClipboard(resultLabel.getText());
        writeLog("Copied conversion result to clipboard");
    }


    public void toGraphTab(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/resources/fxml/GraphTab.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage2 =new Stage();
        stage2.setTitle("Money Transformer");
        stage2.getIcons().add(new Image("app/logo_black.png"));
        stage2.setScene(new Scene(root));
        stage2.show();
        GraphTabController.hideStage(actionEvent);
    }

    String convertCurrenciesToText(double first, double modifier, double second) {
        return resultDecimalFormat.format(first * modifier / second);
    }

    double getModifier() {
        return Double.parseDouble(modifierTextField.getText());
    }

    public void loadCrypto(){
        try {
            CryptoApi.main(new String[]{"", ""});
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < cryptoArr.length; i++) {
            if(cryptoArr[i].getCode().length()==3) {
                listView1.getItems().add(cryptoArr[i].getCode() + "    " + cryptoDisplayFormat.format(cryptoArr[i].getRate()));
                listView2.getItems().add(cryptoArr[i].getCode() + "    " + cryptoDisplayFormat.format(cryptoArr[i].getRate()));
            }if(cryptoArr[i].getCode().length()==4) {
                listView1.getItems().add(cryptoArr[i].getCode() + "   " + cryptoDisplayFormat.format(cryptoArr[i].getRate()));
                listView2.getItems().add(cryptoArr[i].getCode() + "   " + cryptoDisplayFormat.format(cryptoArr[i].getRate()));
            }if(cryptoArr[i].getCode().length()==5) {
                listView1.getItems().add(cryptoArr[i].getCode() + "  " + cryptoDisplayFormat.format(cryptoArr[i].getRate()));
                listView2.getItems().add(cryptoArr[i].getCode() + "  " + cryptoDisplayFormat.format(cryptoArr[i].getRate()));
            }
        }
    }

    public void cryptoItemAction(ActionEvent actionEvent) {
        listView1.getItems().clear();
        loadCrypto();
    }

    public void cryptoItemAction2(ActionEvent actionEvent) {
        listView2.getItems().clear();
        loadCrypto();
    }

    public void moneyItemAction(ActionEvent actionEvent) {
        listView1.getItems().clear();
        for (int i = 0; i < fiatMoneyValues.length; i++) {
            if (fiatMoneyValues[i] < 10) {
                listView1.getItems().add(fiatMoneyKeys[i] + "    " + displayDecimalFormat.format(fiatMoneyValues[i]));
            } else {
                if (fiatMoneyValues[i] < 100) {
                    listView1.getItems().add(fiatMoneyKeys[i] + "   "+ displayDecimalFormat.format(fiatMoneyValues[i]));
                } else {
                    if (fiatMoneyValues[i] > 100) {
                        listView1.getItems().add(fiatMoneyKeys[i] + "  " + displayDecimalFormat.format(fiatMoneyValues[i]));
                    }
                }
            }
        }
    }
    public void moneyItemAction2(ActionEvent actionEvent) {
        listView2.getItems().clear();
        for (int i = 0; i < fiatMoneyValues.length; i++) {
            if (fiatMoneyValues[i] < 10) {
                listView2.getItems().add(fiatMoneyKeys[i] + "    " + displayDecimalFormat.format(fiatMoneyValues[i]));
            } else {
                if (fiatMoneyValues[i] < 100) {
                    listView2.getItems().add(fiatMoneyKeys[i] + "   "+ displayDecimalFormat.format(fiatMoneyValues[i]));
                } else {
                    if (fiatMoneyValues[i] > 100) {
                        listView2.getItems().add(fiatMoneyKeys[i] + "  " + displayDecimalFormat.format(fiatMoneyValues[i]));
                    }
                }
            }
        }
    }

    public void setFavourite(ActionEvent actionEvent) {
        if(modifyFavourite(listView1.getSelectionModel().getSelectedItem().toString().replaceAll("\\d", "").replace(" ","").replace(".",""))==1){
            starImageView.setImage(starImg);
            favButton1.setText("Favourite");
            System.out.println("size:"+(prepareFavouritesValues().size()));
            for (int i = 0; i < prepareFavouritesKeys().size(); i++) {
                System.out.println("i:" + prepareFavouritesKeys().get(i));
                System.out.println("i:" + prepareFavouritesValues().get(i));
            }
        }else{
            starImageView.setImage(emptyStarImg);
            favButton1.setText("Not Favourite");
        }
    }

    public void setFavourite2(ActionEvent actionEvent) {
        if(modifyFavourite(listView1.getSelectionModel().getSelectedItem().toString().replaceAll("\\d", "").replace(" ","").replace(".",""))==1){
            starImageView2.setImage(starImg);
            favButton2.setText("Favourite");
        }else{
            starImageView2.setImage(emptyStarImg);
            favButton2.setText("Not Favourite");
        }
    }


    public void favouritesItemAction2(ActionEvent actionEvent) {
        listView2.getItems().clear();
        generateFavouriteList(listView2);
    }

    public void favouritesItemAction(ActionEvent actionEvent) {
        listView1.getItems().clear();
        System.out.println("size:"+prepareFavouritesKeys().size());
        System.out.println("size:"+prepareFavouritesValues().size());
        generateFavouriteList(listView1);
    }

    private static void generateFavouriteList(ListView listView) {
        listView.getItems().clear();
        System.out.println("size:"+prepareFavouritesKeys().size());
        System.out.println("size:"+prepareFavouritesValues().size());
        for (int i = 0; i < prepareFavouritesKeys().size(); i++) {
                listView.getItems().add(prepareFavouritesKeys().get(i) + prepareFavouritesValues().get(i));
            System.out.println(prepareFavouritesKeys().get(i) + prepareFavouritesValues().get(i));
        }
    }

    public  void transformTabTranslation(String language){
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
                    Element tElement = (Element)node;

                    System.out.println(tElement.getElementsByTagName("id").item(0).getTextContent().startsWith(language.toLowerCase()));
                    if(tElement.getElementsByTagName("id").item(0).getTextContent().startsWith(language.toLowerCase())){
                        System.out.println("changing language...");
                        exitButtonTransformPane.setText(tElement.getElementsByTagName("exitText").item(0).getTextContent());
                        dropdownMenuCrypto2.setText(tElement.getElementsByTagName("convertToText").item(0).getTextContent());
                        dropdownMenuCrypto1.setText(tElement.getElementsByTagName("convertText").item(0).getTextContent());
                        toBConvLabelCryptoPane.setText(tElement.getElementsByTagName("convertText").item(0).getTextContent());
                        convertLabelCryptoPane.setText(tElement.getElementsByTagName("convertToText").item(0).getTextContent());
                        moreButton.setText(tElement.getElementsByTagName("moreText").item(0).getTextContent());
                        mainMenuButtonTransformPane.setText(tElement.getElementsByTagName("mainMenuText").item(0).getTextContent());
                        transformButton.setText(tElement.getElementsByTagName("convertText").item(0).getTextContent());





                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

//
//    public void setOnAction( new EventHandler<ActionEvent>() {
//        public void handle(ActionEvent event){
//            System.out.println("Button clicked");
//        }
//    });



