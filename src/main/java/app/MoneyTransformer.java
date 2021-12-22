package app;

import app.controller.MoneyTransformerController;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.JSONException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.net.ssl.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static app.controller.TransformTabController.fiatMoneyKeys;
import static app.controller.TransformTabController.fiatMoneyValues;

public class MoneyTransformer extends Application {

    public static TreeMap<String, Double> currencies;
    static final String exitLogText = "Exited App";
    public static Map<String, String> euroArchiveMap;
    public static Map<String, String> dolarArchiveMap;

    public SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");

    public final static String xmlUrl = "https://www.bnr.ro/nbrfxrates.xml";
    public final static String configFile = "src/main/resources/others/config.properties";
    public static File xmlFile = new File("src/main/resources/xml/Curs.xml");


    //json cu un model sau un xml
//clasa de tip model
    @Override

    public void start(Stage primaryStage) throws Exception {
        DataObject.loadProperties();
        CryptoApi.main(new String[]{"",""});
        URL url = new File("src/main/resources/fxml/MoneyTransformer.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Money Transformer");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image("app/logo_black.png"));
        primaryStage.show();

    }

    public static String firstXChars(String str, int x) {
        return str.length() < x ? str : str.substring(0, x);
    }


    public static void main(String[] args) {
        UpdateThread updateExchange = new UpdateThread();
        updateExchange.start();
        launch(args);
    }

    public void error() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Could not read the settings file!");
        alert.setContentText("Starting in default mode");
        alert.showAndWait();
    }

    public static void downloadFromUrl(String url, File file) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        /*
         * start of the fix
         */
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509ExtendedTrustManager() {
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] xcs, String string, Socket socket) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] xcs, String string, Socket socket) throws CertificateException {

                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] xcs, String string, SSLEngine ssle) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] xcs, String string, SSLEngine ssle) throws CertificateException {

                    }

                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        /*
         * end of the fix
         */

        URL urlWeb = new URL(url);
        URLConnection con = urlWeb.openConnection();
        ReadableByteChannel rbc = Channels.newChannel(urlWeb.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }


    public static void parseCurrencyXML() {

        try {
            Scanner myReader = new Scanner(xmlFile);
            String data = "";
            while (myReader.hasNextLine()) {
                data = data + "/n" + myReader.nextLine();
            }
            myReader.close();
            currencies = new TreeMap<String, Double>();
            Pattern pattern = Pattern.compile("<Rate currency=\"([^\"]{3})\">(.+?)</Rate>");
            Pattern pattern2 = Pattern.compile("<Rate currency=\"([^\"]{3})\" multiplier=\"100\">(.+?)</Rate>");
            Matcher matcher = pattern.matcher(data);
            Matcher matcher2 = pattern2.matcher(data);
            int pos = 0;
            int pos2 = 0;
            while (matcher.find(pos)) {
                System.out.println("Found: " + matcher.group(1) + ": " + Double.valueOf(matcher.group(2)));
                currencies.put(matcher.group(1), Double.valueOf(matcher.group(2)));
                pos = matcher.end();
            }
            while (matcher2.find(pos2)) {
                System.out.println("Found: " + matcher2.group(1) + ": " + Double.valueOf(matcher2.group(2)) / 100);
                currencies.put(matcher2.group(1), Double.valueOf(matcher2.group(2)) / 100);
                pos2 = matcher2.end();
            }


        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        fiatMoneyValues = MoneyTransformer.currencies.values().toArray(new Double[0]);
        fiatMoneyKeys = MoneyTransformer.currencies.keySet().toArray(new String[0]);


    }

    public static void writeLog(String action) {
        if (DataObject.settingsObj.logs) {
            PrintStream log = null;
            try {
                log = new PrintStream(new FileOutputStream(MoneyTransformerController.logFile, true));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            log.println(new Date().toString() + ":" + action);
            log.flush();
            log.close();
        }
    }

    public static void writeLog(String action, String error) {
        if (DataObject.settingsObj.logs) {
            PrintStream log = null;
            try {
                log = new PrintStream(new FileOutputStream(MoneyTransformerController.logFile, true));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            log.println(new Date().toString() + ":" + "Error:" + error + "while " + action);
            log.flush();
            log.close();
        }
    }

    public static void exitApp() {
        writeLog(exitLogText);
        System.exit(0);
    }

    public static void parseRonCurrArchive() {
        dolarArchiveMap=new TreeMap<String, String>();
        euroArchiveMap=new TreeMap<String, String>();
        try {
            File file = new File("src/main/resources/xml/RonToCurr_archive.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Row");
            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                Node node = nodeList.item(itr);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    euroArchiveMap.put(
                            eElement.getElementsByTagName("Data").item(0).getTextContent(),
                            eElement.getElementsByTagName("CURSA_EURM").item(0).getTextContent());
                    dolarArchiveMap.put(
                            eElement.getElementsByTagName("Data").item(0).getTextContent(),
                            eElement.getElementsByTagName("CURSA_USDM").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            }
        }

}
class UpdateThread extends Thread {
     Calendar now = Calendar.getInstance();
     Calendar timeAt13oClock =Calendar.getInstance();
     long millisLeft;
     final  long DAY_IN_MILLIS =86400000;
    @Override
    public void run() {
        System.out.println(" updater running!!!!!!!!!!!!");
        timeAt13oClock.set(Calendar.HOUR_OF_DAY,13);
        timeAt13oClock.set(Calendar.MINUTE,0);
        timeAt13oClock.set(Calendar.SECOND,0);
        millisLeft=now.getTime().getTime()- timeAt13oClock.getTime().getTime();

        if(millisLeft<0){
            try {
                Thread.sleep(DAY_IN_MILLIS-toPositive(millisLeft));
                MoneyTransformer.parseCurrencyXML();
                CryptoApi.main(new String[]{"",""});
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (UnirestException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(millisLeft>0){
            try {
                Thread.sleep(millisLeft);
                MoneyTransformer.parseCurrencyXML();
                CryptoApi.main(new String[]{"",""});
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (UnirestException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(millisLeft==0){
            MoneyTransformer.parseCurrencyXML();
            try {
                CryptoApi.main(new String[]{"",""});
            } catch (UnirestException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public long toPositive(long n){
        if(n<0) {
            return -n;
        }
        return n;
    }
}




