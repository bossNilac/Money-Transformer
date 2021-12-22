package app;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static app.CryptoApi.cryptoArr;
import static app.CryptoApi.cryptoArrLength;
import static app.controller.TransformTabController.*;

public class FavouritesCurr {
    static File currFile;

    public static String readFile() {
        currFile = new File("src/main/resources/others/fav_curr.txt");
        try {
            return FileUtils.readFileToString(currFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            MoneyTransformer.writeLog("reading currency preferences", "" + e);
        }
        return "error";
    }

    public static boolean isFavourite(String code) {
        if (readFile().contains(code)) {
            return true;
        } else {
            return false;
        }
    }

    public static int modifyFavourite(String code) {
        if (!readFile().contains(code)) {
            try {
                FileUtils.writeStringToFile(currFile, code + " ", true);
                return 1;
            } catch (IOException e) {
                e.printStackTrace();
                MoneyTransformer.writeLog("writing currency preferences", "" + e);
            }
        } else {
            try {
                String fileString = FileUtils.readFileToString(currFile);
                String finalString = fileString.replaceAll(code + " ", "");
                FileUtils.writeStringToFile(currFile, finalString);
                return 0;//enumuri?
            } catch (IOException e) {
                e.printStackTrace();
                MoneyTransformer.writeLog("deleting currency preferences", "" + e);
            }

        }
        return 2;
    }

    public static ArrayList<String> prepareFavouritesKeys() {
        ArrayList<String> list = new ArrayList<String>();
        String[] keys = readFile().split(" ");
        for (int i = 0; i < fiatMoneyKeys.length; i++) {
            for (int j = 0; j < keys.length; j++) {
                if (keys[j].contains(fiatMoneyKeys[i])) {
                    list.add(keys[j]);
                }
            }
        }

        for (int i = 0; i < cryptoArr.length; i++) {
            for (int j = 0; j < keys.length; j++) {
                if (keys[j].contains(cryptoArr[i].getCode())) {
                    list.add(keys[j]);
                }
            }
        }

        return list;
    }

    public static ArrayList<String> prepareFavouritesValues() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < prepareFavouritesKeys().size(); i++) {
            for (int j = 0; j < fiatMoneyKeys.length; j++) {
                if (prepareFavouritesKeys().get(i).contains(fiatMoneyKeys[j])) {
                    if (fiatMoneyValues[i] < 10) {
                        list.add("    " + displayDecimalFormat.format(fiatMoneyValues[i]));
                    } else {
                        if (fiatMoneyValues[i] < 100) {
                            list.add("   " + displayDecimalFormat.format(fiatMoneyValues[i]));
                        } else {
                            if (fiatMoneyValues[i] > 100) {
                                list.add("  " + displayDecimalFormat.format(fiatMoneyValues[i]));
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < prepareFavouritesKeys().size(); i++) {
            for (int j = 0; j < cryptoArrLength; j++) {
                if (prepareFavouritesKeys().get(i).contains(fiatMoneyKeys[j])) {
                    if (cryptoArr[j].getRate() < 10) {
                        list.add("   " + displayDecimalFormat.format(cryptoArr[j].getRate()));
                    } else {
                        if (cryptoArr[j].getRate() < 100) {
                            list.add("  " + displayDecimalFormat.format(cryptoArr[j].getRate()));
                        } else {
                            if (cryptoArr[j].getRate() > 100) {
                                list.add(" " + displayDecimalFormat.format(cryptoArr[j].getRate()));
                            }
                        }
                    }
                }
            }
        }

        return list;
    }

    public static ArrayList<String> prepareFavourites(String[] currencyArray) {
        ArrayList<String> list = new ArrayList<String>();
        String[] keys = readFile().split(" ");

        for (int i = 0; i < fiatMoneyKeys.length; i++) {
            for (int j = 0; j < keys.length; j++) {
                if (keys[j].contains(fiatMoneyKeys[i])) {
                    list.add(keys[j]);
                    //String a = keys[j];
                }
            }
        }

        for (int i = 0; i < prepareFavouritesKeys().size(); i++) {
            for (int j = 0; j < fiatMoneyKeys.length; j++) {
                if (prepareFavouritesKeys().get(i).contains(fiatMoneyKeys[j])) {
                    if (fiatMoneyValues[i] < 10) {
                        list.add("    " + displayDecimalFormat.format(fiatMoneyValues[i]));
                        //String b = keys[j];
                    } else {
                        if (fiatMoneyValues[i] < 100) {
                            list.add("   " + displayDecimalFormat.format(fiatMoneyValues[i]));
                        } else {
                            if (fiatMoneyValues[i] > 100) {
                                list.add("  " + displayDecimalFormat.format(fiatMoneyValues[i]));
                            }
                        }
                    }
                }
            }
            //list.add(a+b);
        }

        return list;
    }

}
