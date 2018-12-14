/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networksecurityform;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import networksecurityform.algorithms.Caser;
import networksecurityform.algorithms.Playfair;
import networksecurityform.algorithms.DES;
import networksecurityform.algorithms.RC4;
import networksecurityform.algorithms.RSA;

/**
 * FXML Controller class
 *
 * @author Elesdody
 */
public class FXMLController implements Initializable {

    @FXML
    TextArea planeText;
    @FXML
    TextArea ciphertext;
    @FXML
    Button encrypt;
    @FXML
    Button decrypt;
    @FXML
    ComboBox<String> combobox;
    @FXML
    TextField key;
    // initailize rsa algorithm gloable because use same key in encrypt and decyrpt
    RSA rsa;
    private static final String CASER = "Caser";
    private static final String PLAY_FAIR = "Playfair";
    private static final String DES = "DES";
    private static final String RC4 = "RC4";
    private static final String RSA = "RSA";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        combobox.getItems().add(CASER);
        combobox.getItems().add(PLAY_FAIR);
        combobox.getItems().add(DES);
        combobox.getItems().add(RC4);
        combobox.getItems().add(RSA);

    }

    @FXML
    void encrypt(MouseEvent event) {
        // to clear cipher text befor encryption
        ciphertext.clear();
        String[] input = planeText.getText().split(" ");
        String[] output = new String[input.length];
        //to take every word after space 
        for (int i = 0; i < input.length; i++) {

            output[i] = encyrptword(input[i]);
            ciphertext.appendText(" " + output[i]);
            // because output length not same the input length in some algorthims like playfair
            if (output[i] == null) {
                break;
            }

        }

    }

    @FXML
    void decrypt(MouseEvent event) {
// to clear plane text befor encryption
        planeText.clear();

        String[] input = ciphertext.getText().split(" ");
        String[] output = new String[input.length];
        //to take every word after space 
        for (int i = 0; i < input.length; i++) {

            output[i] = decryptword(input[i]);
            planeText.appendText(" " + output[i]);
            if (output[i] == null) {
                break;
            }

        }

    }

    private boolean checkAlphabet(String input) {
        // varible to check if the input is only text or not 
        boolean isText = true;
        // to check every letter is from alphabets befor ecryption 
        for (int i = 0; i < input.length(); i++) {
            String letter = String.valueOf(input.charAt(i));
            if (!letter.matches("[a-z]")) {
                // to display alert if any character is not alphabet
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Input");
                alert.setHeaderText("Please enter only Text ");
                alert.showAndWait();

                isText = false;
                break;
            }
        }
        return isText;

    }

    private void showAleart() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Algorithm");
        alert.setHeaderText("Please choose algorithm ");
        alert.showAndWait();
    }

    private String encyrptword(String input) {

        String output = null;

        if (combobox.getValue() != null) {
            String algorithm = combobox.getValue();
            switch (algorithm) {
                case CASER: {
                    // to make sure the plaintext is only alphbets
                    boolean isText = checkAlphabet(input);
                    if (isText) {
                        Caser caser = new Caser();
                        try {
                            output = caser.encrypt(input, Integer.parseInt(key.getText()));
                            return output;

                        } catch (NumberFormatException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Input");
                            alert.setHeaderText("Key must be number");
                            alert.showAndWait();
                        }
                    }

                    break;
                }
                case PLAY_FAIR: {
                    boolean isText = checkAlphabet(input);

                    if (isText) {
                        Playfair playfair = new Playfair(input, key.getText());
                        output = playfair.encrypt();
                        return output;
                    }

                    break;

                }
                case DES: {

                    DES des = new DES();
                    if (key.getText().length() == 8 && input.length() == 8) {
                        output = des.encrypt(input, key.getText());
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Input");
                        alert.setHeaderText("Key and PlainText must be 8 character");
                        alert.showAndWait();
                    }
                    System.out.println("output lengnth:" + output.length());
                    return output;
                }
                case RC4: {
                    RC4 rc4 = new RC4(key.getText());
                    output = rc4.encrypt(input);
                    break;
                }
                case RSA: {
                    rsa = new RSA();
                    int cipher = rsa.encrypt(Integer.parseInt(input));
                    output = cipher + "";
                    break;
                }

                default:
                    System.out.println("Error ");
            }

        } else {
            showAleart();
        }
        return output;
    }

    private String decryptword(String input) {

        String output = null;

        if (combobox.getValue() != null) {
            String algorithm = combobox.getValue();
            switch (algorithm) {
                case CASER: {
                    boolean isText = checkAlphabet(input);
                    if (isText) {
                        Caser caser = new Caser();
                        try {
                            output = caser.decrypt(input, Integer.parseInt(key.getText()));

                            return output;
                        } catch (NumberFormatException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Input");
                            alert.setHeaderText("Key must be number");
                            alert.showAndWait();
                        }
                    }
                    break;
                }
                case PLAY_FAIR: {
                    boolean isText = checkAlphabet(input);
                    if (isText) {
                        Playfair playfair = new Playfair(input, key.getText());
                        output = playfair.decrypt();
                        return output;
                    }
                }
                case DES: {
                    DES des = new DES();
                    if (key.getText().length() == 8) {
                        output = des.dycrypt(input, key.getText());
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Input");
                        alert.setHeaderText("Key must be 8 character");
                        alert.showAndWait();
                    }
                    System.out.println("Decryption" + output.length());
                    return output;
                }
                case RC4: {
                    RC4 rc4 = new RC4(key.getText());
                    output = rc4.encrypt(input);
                    break;
                }
                case RSA: {

                    int plain = rsa.decrypt(Integer.parseInt(input));
                    output = plain + "";
                    break;
                }
                default:
                    System.out.println("error");
                    break;
            }
        }

        return output;
    }
}
