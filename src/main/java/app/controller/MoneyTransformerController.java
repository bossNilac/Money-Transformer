package app.controller;

import app.MoneyTransformer;
import app.DataObject;
import app.XlsParser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.script.CompiledScript;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static app.MoneyTransformer.*;


public class MoneyTransformerController implements Initializable {

    public ProgressBar splashScreenLoading;

    public Pane mainMenu;
    public Pane aboutMenu;
    public Pane settingsMenu;
    public Pane splashScreen;

    public ImageView imageviewTransformPane;
    public ImageView emailImageView;
    public ImageView instaImageView;
    public ImageView telegramImageView;
    public ImageView mainmenuImageView;
    public ImageView splashscreenImageView;

    public Button lightModeButton;
    public Button darkModeButton;
    public Button logsButton;
    public Button languageDisplay;
    public Button exitButtonMainPane;
    public Button transfromButtonMainPane;
    public Button settingsButtonMainPane;
    public Button aboutButtonMainPane;
    public Button exitButtonSettingsPane;
    public Button mainmenuButtonSettingsPane;
    public Button saveButtonExitPane;
    public Button mainmenuAboutPane;
    public Button exitButtonAboutPane;


    public Label titleLabelSettingsPane;
    public Label progressLabel;
    public Label emailLabel;
    public Label instaLabel;
    public Label telegramLabel;
    public Label appearanceLabelSettingsPane;
    public Label miscLabelSettingsPane;
    public Label networkingLabelSettingsPane;
    public Label versionLabelAboutPane;
    public Label descriptionLabelAboutPane;
    public Label titleLabelAboutPane;
    public Label contactTitleAboutPane;
    public Label logsLabelSettingsPane;
    public Label languageLabelSettingsPane;

    public static File logFile =new File("src/main/resources/others/logs.txt");


    static public String [] languages ={"English","Romana","Francais","Deutsch","Espanol","Italiano"};

    public Button changeLanguageButton1;
    public Button changeLanguageButton2;
    public Label privacyLabelSettingsPane;
    public Button internetConLabel;
    public Button internetOnOffButton;
    public Button dataBaseConLabel;
    public Button dataBaseConOnOffButton;
    public Button calculatorButtonMainPane;
    public Button cryptoButtonMainPane;
    public Button helpButtonMainPane;
    public Pane cryptoPane;


    public static File languageFile = new File("src/main/resources/xml/languages.xml");
    public Button mainmenuButtonCrypto;
    public Button exitButtonCrypto;
    public Button crpytoTitlePane;
    public Label providedByLabel;


    boolean loadingFlag=true;


    static Image lightModeImage= new Image("app/logo_lightmode.png");
    static Image darkModeImage = new Image("app/logo_darkmode.png");


    static ArrayList <Button> buttons = new ArrayList<>();
    static ArrayList <Pane>   panes   = new ArrayList<>();
    static ArrayList <Label>  labels  = new ArrayList<>();
    static ArrayList <ImageView>  images  = new ArrayList<>();
    public static int currIndex;
    public static Scene MainScene;
    public Label comingSoonLabelSettingsPane;
    public WebView browser;
    public ImageView cryptoImageView;


    //logo colors:crem:#FDC68A
//logo colors:grey:#EBEBEB
    //turquise:1ABBB4
    //dark grey:2b2b2b


/*
1.Splash screen (3 secunde)?
2.Main screen?
3.Preferences screen?
4.About screen (info despre app. [versiune], info despre autor)?

UI:
- setari si preferinte?
- fisier separat de log-uri?
- un ecran de istoric ?
- monitorizare ?
- splash screen loading?
- un ecran About?
- multi language??
- stilul de navigare?
-bursa pe viitor ?
-despre inflatie??? (Sa studiez inflatie pe diferiti ani );
-logo-ul meu la iconita din window?
*/


    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {


        WebEngine webEngine = browser.getEngine();
        webEngine.load("https://www.coingecko.com/en");


        labels.add(titleLabelSettingsPane);
        labels.add(progressLabel);
        labels.add(emailLabel);
        labels.add(instaLabel);
        labels.add(telegramLabel);
        labels.add(appearanceLabelSettingsPane);
        labels.add(miscLabelSettingsPane);
        labels.add(networkingLabelSettingsPane);
        labels.add(versionLabelAboutPane);
        labels.add(descriptionLabelAboutPane);
        labels.add(titleLabelAboutPane);
        labels.add(contactTitleAboutPane);
        labels.add(logsLabelSettingsPane);
        labels.add(languageLabelSettingsPane);
        labels.add(privacyLabelSettingsPane);
        labels.add(providedByLabel);



        buttons.add(lightModeButton);
        buttons.add(darkModeButton);
        buttons.add(logsButton);
        buttons.add(languageDisplay);
        buttons.add(exitButtonAboutPane);
        buttons.add(transfromButtonMainPane);
        buttons.add(settingsButtonMainPane);
        buttons.add(aboutButtonMainPane);
        buttons.add(exitButtonSettingsPane);
        buttons.add(mainmenuButtonSettingsPane);
        buttons.add(saveButtonExitPane);
        buttons.add(mainmenuAboutPane);
        buttons.add(exitButtonMainPane);
        buttons.add(changeLanguageButton2);
        buttons.add(changeLanguageButton1);
        buttons.add(changeLanguageButton1);
        buttons.add(changeLanguageButton2);
        buttons.add(internetConLabel);
        buttons.add(internetOnOffButton);
        buttons.add(dataBaseConLabel);
        buttons.add(dataBaseConOnOffButton);
        buttons.add(cryptoButtonMainPane);
        buttons.add(calculatorButtonMainPane);
        buttons.add(helpButtonMainPane);
        buttons.add(mainmenuButtonCrypto);
        buttons.add(exitButtonCrypto);
        buttons.add(crpytoTitlePane);

        labels.add(comingSoonLabelSettingsPane);

        panes.add(mainMenu);
        panes.add(aboutMenu);
        panes.add(settingsMenu);
        panes.add(splashScreen);
        panes.add(cryptoPane);

        images.add(mainmenuImageView);
        images.add(splashscreenImageView);
        images.add(cryptoImageView);


        splashScreen.setVisible(true);
        cryptoPane.setVisible(false);
        mainMenu.setVisible(false);
        settingsMenu.setVisible(false);
        aboutMenu.setVisible(false);



        for (int j=0;j<languages.length;j++){
            if(languages[j].toLowerCase().startsWith(DataObject.settingsObj.language)){
                languageDisplay.setText(languages[j]);
                currIndex = j;
                languageXmlParser(DataObject.settingsObj.language);
            }
        }

        if(DataObject.settingsObj.logs) {
            try {
                if (logFile.createNewFile()) {

                    writeLog("Logs file created");
                    writeLog("App started");
                } else {

                    writeLog("App started");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        if(DataObject.settingsObj.theme.equals("dark")){
            toDarkMode();
            splashScreenLoading.setStyle("-fx-accent: #fdc68a ;");
            darkModeButton.setUnderline(true);
        }
        if(DataObject.settingsObj.theme.equals("light")){
            toLightMode();
            splashScreenLoading.setStyle("-fx-accent: #1abbb4 ;");
            lightModeButton.setUnderline(true);
        }
        /*
        TODO:sa pun textele in traducere
        personalizare prin taguri
        shortcut din tastatura CTRL +cv
        combinatii de taste pt selectare din listview (EX CTRL+R=ron si ctrl+y =yen)
        cand tin apasat pe ujn element din listview si dau CTRL-S sa se salveze cursul
        export de csv la operatiunile pe care le face utilzatorul


        */
        /**
         * TODO:sa pun textele in traducere
         * personalizare prin taguri
         *         shortcut din tastatura CTRL +cv
         *         combinatii de taste pt selectare din listview (EX CTRL+R=ron si ctrl+y =yen)
         *         cand tin apasat pe ujn element din listview si dau CTRL-S sa se salveze cursul
         *         export de csv la operatiunile pe care le face utilzatorul
         *         notificari
         */




        Thread timerThread = new Thread(() -> {
            while (loadingFlag) {
                try {
                    MoneyTransformer.downloadFromUrl(xmlUrl, xmlFile);
                } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    splashScreenLoading.setProgress(0.16);
                    progressLabel.setText("Connecting to the Internet...");
                });

                XlsParser.main();
                Platform.runLater(() -> {
                    splashScreenLoading.setProgress(0.33);
                    progressLabel.setText("Loading Assets(1/3)...");
                });
                MoneyTransformer.parseRonCurrArchive();
                Platform.runLater(() -> {
                    splashScreenLoading.setProgress(0.66);
                    progressLabel.setText("Loading Assets(2/3)...");
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    splashScreenLoading.setProgress(0.85);
                    progressLabel.setText("Loading Assets(3/3)...");
                });
                MoneyTransformer.parseCurrencyXML();
                Platform.runLater(() -> {
                    splashScreenLoading.setProgress(0.99);
                    progressLabel.setText("Getting the latest currency Exchange Values...");
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    splashScreenLoading.setProgress(1);
                    progressLabel.setText("Loading the User Interface...");
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loadingFlag=false;
                splashScreen.setVisible(false);
                mainMenu.setVisible(true);
            }
        });   timerThread.start();
    }


    public static void copyToClipboard(String s) {

        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();

        content.putString(s);
        clipboard.setContent(content);

    }

    public void mainMenuExit(ActionEvent actionEvent) {
        writeLog("App exited");
        System.exit(0);
    }

    public void goToTransformPane(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/resources/fxml/TransformTab.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage2 =new Stage();
        stage2.setTitle("Money Transformer");
        stage2.getIcons().add(new Image("app/logo_black.png"));
        MainScene=new Scene(root);
        stage2.setScene(MainScene);
        MoneyTransformerController.MainScene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (TransformTabController.saveShortcut.match(event)) {
                    System.out.println("da");
                }
            }

        });
        stage2.show();



    }

    public void goAbout(ActionEvent actionEvent) {
        aboutMenu.setVisible(true);
        mainMenu.setVisible(false);
    }

    public void saveSettings(ActionEvent actionEvent) {
        try {
            SystemTray tray = SystemTray.getSystemTray();
            java.awt.Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
            TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("System tray icon demo");
            tray.add(trayIcon);
            trayIcon.displayMessage("Money Transformer","The settings have been saved", TrayIcon.MessageType.INFO);
        } catch (AWTException e) {

            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        languageXmlParser(languageDisplay.getText());
    }

    public void ExitFromSettings(ActionEvent actionEvent) {
        writeLog("App exited");
        System.exit(0);
    }

    public void goSettingsMenu(ActionEvent actionEvent) {
        settingsMenu.setVisible(true);
        mainMenu.setVisible(false);
    }


    public void copyEmail(MouseEvent mouseEvent) {
        copyToClipboard(emailLabel.getText());

        Thread emailPopUpThread = new Thread(() -> {
            try {
                Thread.sleep(1250);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                try {
                    SystemTray tray = SystemTray.getSystemTray();
                    java.awt.Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
                    TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
                    trayIcon.setImageAutoSize(true);
                    trayIcon.setToolTip("System tray icon demo");
                    tray.add(trayIcon);
                    trayIcon.displayMessage("Money Transformer","The email has been copied to the clipboard", TrayIcon.MessageType.INFO);
                } catch (AWTException e) {
                    e.printStackTrace();
                }
                accesLink("https://mail.google.com/mail/u/0/#inbox?compose=new");
            });
        });
        emailPopUpThread.start();
    }

    public void accessInsta(MouseEvent mouseEvent) {
        accesLink("https://www.instagram.com/calin.bcs/");
    }
    public static void  accesLink(String url){

        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                System.err.println("Process not found/URL error");
            }
        }else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException e) {

                System.err.println("Process not found");
            }
        }
    }

    public void aboutToMainMenu(ActionEvent actionEvent) {
        mainMenu.setVisible(true);
        aboutMenu.setVisible(false);
    }

    public void accessTelegram(MouseEvent mouseEvent) {
        accesLink("https://t.me/Calin_Baculescu");
    }

    public void aboutExit(ActionEvent actionEvent) {
        writeLog("App exited");
        System.exit(0);
    }

    public void underLineButton(MouseEvent mouseEvent) {
        //email
        emailLabel.setUnderline(true);
    }

    public void deUnderLineButton(MouseEvent mouseEvent) {
        //email
        emailLabel.setUnderline(false);
    }

    public void underLineButton2(MouseEvent mouseEvent) {
        //insta
        instaLabel.setUnderline(true);
    }

    public void deUnderLineButton2(MouseEvent mouseEvent) {
        //insta
        instaLabel.setUnderline(false);
    }

    public void underLineButton3(MouseEvent mouseEvent) {
        //telegram
        telegramLabel.setUnderline(true);
    }

    public void deUnderLineButton3(MouseEvent mouseEvent) {
        //telegram
        telegramLabel.setUnderline(false);
    }

    public void emailAccessLabel(MouseEvent mouseEvent) {
        copyToClipboard(emailLabel.getText());

        Thread emailPopUpThread = new Thread(() -> {
                try {
                    Thread.sleep(1250);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    try {
                        SystemTray tray = SystemTray.getSystemTray();
                        java.awt.Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
                        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
                        trayIcon.setImageAutoSize(true);
                        trayIcon.setToolTip("System tray icon demo");
                        tray.add(trayIcon);
                        trayIcon.displayMessage("Money Transformer","The email has been copied to the clipboard", TrayIcon.MessageType.INFO);
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }
                    accesLink("https://mail.google.com/mail/u/0/#inbox?compose=new");
                });
        });
        emailPopUpThread.start();
    }

    public void instaAccessLabel(MouseEvent mouseEvent) {
        accesLink("https://www.instagram.com/calin.bcs/");
    }
    public void telegramAccessLabel(MouseEvent mouseEvent) {
        accesLink("https://t.me/Calin_Baculescu");
    }


    public void onOffSwitch(MouseEvent mouseEvent) {
        if(logsButton.getText().equals("✓")){
            logsButton.setText("X");
            DataObject.writeProperties("theme","false");
        }else {
            logsButton.setText("✓");
            DataObject.writeProperties("theme","true");
        }
    }
    
    public void toMainMenuFromSettings(ActionEvent actionEvent) {
        settingsMenu.setVisible(false);
        mainMenu.setVisible(true);
    }

    public void darkModeSwitch(ActionEvent actionEvent) {
            if(!darkModeButton.isUnderline() && lightModeButton.isUnderline()){
                darkModeButton.setUnderline(true);
            }
            if(!darkModeButton.isUnderline() && lightModeButton.isUnderline()){
                darkModeButton.setUnderline(true);
            }
            if(darkModeButton.isUnderline() && lightModeButton.isUnderline()){
            lightModeButton.setUnderline(false);
            }
            changeTheme();

    }
    public void lightModeSwitch(ActionEvent actionEvent) {
        if(darkModeButton.isUnderline() && !lightModeButton.isUnderline()){
            lightModeButton.setUnderline(true);
        }
        if(darkModeButton.isUnderline() && lightModeButton.isUnderline()){
            darkModeButton.setUnderline(false);
        }
        if(darkModeButton.isUnderline() && !lightModeButton.isUnderline()){
            lightModeButton.setUnderline(true);
        }
        changeTheme();
    }
    public  void changeTheme(){
        //logo colors:crem:#FDC68A
        //logo colors:grey:#EBEBEB
        //turquise:1ABBB4
        //dark grey:2b2b2b

            if(darkModeButton.isUnderline()){
                toDarkMode();
                DataObject.writeProperties("theme","dark");
                writeLog("Changed theme to: "+"dark");
            }
            if(lightModeButton.isUnderline()){
                toLightMode();
            DataObject.writeProperties("theme","light");
                writeLog("Changed theme to: "+"light ");
            }

    }

            public static void toDarkMode(){
                for(int i = 0; i<buttons.size();  i++){

                    if(buttons.get(i).getTextFill().toString().equals("0x2b2b2bff")) {
                        buttons.get(i).setStyle("-fx-text-fill: #ebebeb ;-fx-background-color: transparent");
                    }
                    if(buttons.get(i).getTextFill().toString().equals("0x1abbb4ff")) {
                        buttons.get(i).setStyle("-fx-text-fill: #fdc68a ;-fx-background-color: transparent");
                    }
                }

                for(int i = 0; i<panes.size();  i++){
                    panes.get(i).setStyle("-fx-background-color: #121111");
                }

                for(int i = 0; i<labels.size();i++){
                    if(labels.get(i).getTextFill().toString().equals("0x2b2b2bff")) {
                        labels.get(i).setStyle("-fx-text-fill: #ebebeb ");
                    }
                    if(labels.get(i).getTextFill().toString().equals("0x1abbb4ff")) {
                        labels.get(i).setStyle("-fx-text-fill: #fdc68a ");
                    }
                }
                for(int i = 0; i<images.size();i++){
                    images.get(i).setImage(darkModeImage);
                }
                DataObject.settingsObj.theme="dark";
            }

            public static void toLightMode(){
                for(int i = 0; i<buttons.size();  i++){

                    if(buttons.get(i).getTextFill().toString().equals("0xebebebff")) {
                        buttons.get(i).setStyle("-fx-text-fill: #2b2b2b ;-fx-background-color: transparent");

                    }
                    if(buttons.get(i).getTextFill().toString().equals("0xfdc68aff")) {
                        buttons.get(i).setStyle("-fx-text-fill: #1abbb4 ;-fx-background-color: transparent");
                    }
                }
                for(int i = 0; i<panes.size();  i++){
                    panes.get(i).setStyle("-fx-background-color: #ffffff");
                }
                for(int i = 0; i<labels.size();i++){
                    if(labels.get(i).getTextFill().toString().equals("0xebebebff")) {
                        labels.get(i).setStyle("-fx-text-fill: #2b2b2b");
                    }
                    if(labels.get(i).getTextFill().toString().equals("0xfdc68aff")) {
                        labels.get(i).setStyle("-fx-text-fill: #1abbb4");
                    }
                }
                for(int i = 0; i<images.size();i++){
                    images.get(i).setImage(lightModeImage);
                }
                DataObject.settingsObj.theme="light";
            }

    public  void languageXmlParser(String language){
        try {
            // creating a constructor of file class and
            // parsing an XML file


            // Defines a factory API that enables
            // applications to obtain a parser that produces
            // DOM object trees from XML documents.
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            // we are creating an object of builder to parse
            // the xml file.
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(languageFile);

			/*here normalize method Puts all Text nodes in
			the full depth of the sub-tree underneath this
			Node, including attribute nodes, into a "normal"
			form where only structure separates
			Text nodes, i.e., there are neither adjacent
			Text nodes nor empty Text nodes. */
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            // Here nodeList contains all the nodes with
            // name geek.
            NodeList nodeList = doc.getElementsByTagName("language");

            // Iterate through all the nodes in NodeList
            // using for loop.
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node node = nodeList.item(i);
                System.out.println("\nNode Name :" + node.getNodeName());

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element tElement = (Element)node;

                    System.out.println(tElement.getElementsByTagName("id").item(0).getTextContent().startsWith(language.toLowerCase()));
                    if(tElement.getElementsByTagName("id").item(0).getTextContent().startsWith(language.toLowerCase())){
                        System.out.println("changing language...");
                        exitButtonAboutPane.setText(tElement.getElementsByTagName("exitText").item(0).getTextContent());
                        exitButtonMainPane.setText(tElement.getElementsByTagName("exitText").item(0).getTextContent());
                        exitButtonSettingsPane.setText(tElement.getElementsByTagName("exitText").item(0).getTextContent());
                        exitButtonCrypto.setText(tElement.getElementsByTagName("exitText").item(0).getTextContent());

                        transfromButtonMainPane.setText(tElement.getElementsByTagName("transformText").item(0).getTextContent());

                        aboutButtonMainPane.setText(tElement.getElementsByTagName("aboutText").item(0).getTextContent());
                        titleLabelAboutPane.setText(tElement.getElementsByTagName("aboutText").item(0).getTextContent());

                        settingsButtonMainPane.setText(tElement.getElementsByTagName("settingsText").item(0).getTextContent());

                        appearanceLabelSettingsPane.setText(tElement.getElementsByTagName("appearanceText").item(0).getTextContent());

                        darkModeButton.setText(tElement.getElementsByTagName("darkmodetext").item(0).getTextContent());
                        lightModeButton.setText(tElement.getElementsByTagName("lightmodetext").item(0).getTextContent());

                        languageLabelSettingsPane.setText(tElement.getElementsByTagName("languageText").item(0).getTextContent());

                        miscLabelSettingsPane.setText(tElement.getElementsByTagName("miscText").item(0).getTextContent());

                        logsLabelSettingsPane.setText(tElement.getElementsByTagName("logText").item(0).getTextContent());
                        versionLabelAboutPane.setText(tElement.getElementsByTagName("versionText").item(0).getTextContent());

                        descriptionLabelAboutPane.setText(tElement.getElementsByTagName("aboutappText").item(0).getTextContent());
                        contactTitleAboutPane.setText(tElement.getElementsByTagName("contact_me_at_Text").item(0).getTextContent());

                        internetConLabel.setText(tElement.getElementsByTagName("networkingText").item(0).getTextContent());
                        dataBaseConLabel.setText(tElement.getElementsByTagName("privacyText").item(0).getTextContent());

                        mainmenuButtonSettingsPane.setText(tElement.getElementsByTagName("mainMenuText").item(0).getTextContent());

                        mainmenuAboutPane.setText(tElement.getElementsByTagName("mainMenuText").item(0).getTextContent());
                        mainmenuButtonSettingsPane.setText(tElement.getElementsByTagName("mainMenuText").item(0).getTextContent());
                        mainmenuButtonCrypto.setText(tElement.getElementsByTagName("mainMenuText").item(0).getTextContent());

                        helpButtonMainPane.setText(tElement.getElementsByTagName("helpText").item(0).getTextContent());

                        calculatorButtonMainPane.setText(tElement.getElementsByTagName("calculatorsText").item(0).getTextContent());

                        privacyLabelSettingsPane.setText(tElement.getElementsByTagName("privacyText").item(0).getTextContent());
                        networkingLabelSettingsPane.setText(tElement.getElementsByTagName("networkingText").item(0).getTextContent());



                        System.out.println("finished");

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchLanguage(int i) {
        if (i == -1) {
            if (currIndex == 0) {
                languageDisplay.setText(languages[5]);
                currIndex = 5;
            } else {
                languageDisplay.setText(languages[currIndex + i]);
                --currIndex;
            }
        }

        if (i == 1) {
            if (currIndex == 5) {
                languageDisplay.setText(languages[0]);
                currIndex = 0;
            } else {
                languageDisplay.setText(languages[currIndex + i]);
                currIndex++;
            }
        }

    }

    public void changeLanguageForward(ActionEvent actionEvent) {
        switchLanguage(1);
        writeLog("Changed Language to: "+languages[currIndex]);
        languageXmlParser(MoneyTransformer.firstXChars(languages[currIndex].toLowerCase(),2));
        DataObject.settingsObj.writeProperties("language",MoneyTransformer.firstXChars(languages[currIndex].toLowerCase(),2));
        DataObject.settingsObj.language=MoneyTransformer.firstXChars(languages[currIndex].toLowerCase(),2);
    }

    public void changeLanguageBack(ActionEvent actionEvent) {
        switchLanguage(-1);
        writeLog("Changed Language to: "+languages[currIndex]);
        languageXmlParser(MoneyTransformer.firstXChars(languages[currIndex].toLowerCase(),2));
        DataObject.settingsObj.writeProperties("language",MoneyTransformer.firstXChars(languages[currIndex].toLowerCase(),2));
        DataObject.settingsObj.language=MoneyTransformer.firstXChars(languages[currIndex].toLowerCase(),2);
    }





    public void goToCalculatorMenu(ActionEvent actionEvent) throws IOException {
            URL url = new File("src/main/resources/fxml/CalculatorsTab.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Stage stage2 =new Stage();
            stage2.setTitle("Money Transformer");
            stage2.getIcons().add(new Image("app/logo_black.png"));
            MainScene=new Scene(root);
            stage2.setScene(MainScene);
            stage2.show();
    }



    public void goHelpMenu(ActionEvent actionEvent) {
        File htmlFile = new File("src/main/resources/others/indexHelp.html");
        try {
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToCryptoPane(ActionEvent actionEvent){
        cryptoPane.setVisible(true);
        mainMenu.setVisible(false);
    }


    public void toMainMenuFromCrpyto(ActionEvent actionEvent) {
        cryptoPane.setVisible(false);
        mainMenu.setVisible(true);
    }
}

