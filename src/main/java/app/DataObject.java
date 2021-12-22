package app;

import java.io.*;
import java.util.Properties;

public class DataObject {
    static final String configFileName = "src/main/resources/others/config.properties";
    static Properties prop = new Properties();
    public static DataObject settingsObj=new DataObject();
    public String theme;
    public String language;
    public boolean logs;
    public boolean dbcon;
    public boolean online;

    public static void main(String[] args){
        loadProperties();
    }

    static void writeProperties(String theme, String language, String logs) {
        try (OutputStream output = new FileOutputStream(configFileName)) {

            // set the properties value
            prop.setProperty("theme", theme);
            prop.setProperty("language", language);
            prop.setProperty("logs", logs);

            // save properties to project root folder
            prop.store(output, null);

            System.out.println(prop);
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println("Err write: " + io.getMessage());
        }

    }
    public static void writeProperties(String target, String value) {
        try (OutputStream output = new FileOutputStream(configFileName)) {

            // set the properties value
            prop.setProperty(target, value);


            // save properties to project root folder
            prop.store(output, null);

            System.out.println(prop);
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println("Err write: " + io.getMessage());
        }
    }


    public static void loadProperties() {
        try (InputStream input = new FileInputStream(configFileName)) {

            // load a properties file
            prop.load(input);

            settingsObj.theme = prop.getProperty("theme");
            settingsObj.language = prop.getProperty("language");
            System.out.println(settingsObj.language);
            if(prop.getProperty("logs").equals("true")){settingsObj.logs = true;}
            if(prop.getProperty("logs").equals("false")){settingsObj.logs = false;}
            if(prop.getProperty("dbcon").equals("true")){settingsObj.dbcon = true;}
            if(prop.getProperty("dbcon").equals("false")){settingsObj.dbcon = false;}
            if(prop.getProperty("online").equals("true")){settingsObj.online = true;}
            if(prop.getProperty("online").equals("false")){settingsObj.online = false;}
            defaultSettingsSetter();
            System.out.println(settingsObj.language);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Err load: " + ex.getMessage());
        }

    }

    /*
    TODO:caz in care nu am fisier ,switch?
     */
    public static void defaultSettingsSetter(){
        if(settingsObj.theme.equals("")||!(settingsObj.theme.equals("dark")||settingsObj.theme.equals("light"))){
            settingsObj.theme ="dark";
        }
        if(prop.getProperty("logs").equals("")||!(prop.getProperty("logs").equals("true")||prop.getProperty("logs").equals("false"))){
            settingsObj.logs =false;
        }
        if(settingsObj.language.equals("")||!(settingsObj.language.equals("ro")||settingsObj.language.equals("en")||settingsObj.language.equals("it")||settingsObj.language.equals("es")||settingsObj.language.equals("de")||settingsObj.language.equals("fr"))){
            settingsObj.language="en";
        }

        if(prop.getProperty("online").equals("")||!(prop.getProperty("online").equals("true")||prop.getProperty("online").equals("false"))){
            settingsObj.online =true;
        }

        if(prop.getProperty("dbcon").equals("")||!(prop.getProperty("dbcon").equals("dbcon")||prop.getProperty("dbcon").equals("dbcon"))){
            settingsObj.dbcon =false;
        }
    }
}



