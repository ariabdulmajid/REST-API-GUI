package com.java.gui.restapigui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Ari Abdul Majid
 * @version :$, (Created on 06/03/2023)
 * @since 1.0.Alpha1
 */

public class RestApi extends JFrame {
    public JPanel panelMain;
    private JTextField txtIp;
    private JTextField txtPath;
    private JTextArea txtMessage;
    private JTextArea txtResponse;
    private JTextField txtUser;
    private JTextField txtInKey;
    private JTextField txtPass;
    private JTextField txtHeader;
    private JTextArea txtHMAC;
    private JLabel lblIp;
    private JLabel lblPass;
    private JLabel lblUser;
    private JLabel lblInKey;
    private JLabel lblHeader;
    private JLabel lblHMAC;
    private JButton btnGen;
    private JButton btnKirim;
    private JButton btnHapus;
    private JComboBox txtCombo;

    public RestApi() {
        btnGen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = txtUser.getText();
                String pass = txtPass.getText();
                String inKey = txtInKey.getText();
                String header = txtHeader.getText();

                byte[] hmac;
                try {
                    hmac = generateHmac(user+pass, inKey);
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                } catch (InvalidKeyException ex) {
                    throw new RuntimeException(ex);
                }

                String hexHmac = javax.xml.bind.DatatypeConverter.printHexBinary(hmac);

                txtHMAC.setText(hexHmac);
            }
        });

        btnKirim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = txtIp.getText();
                String path = txtPath.getText();

                String url = ip+path;

                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<Object> result = restTemplate.getForEntity(url, Object.class);

                txtResponse.setText(result.getBody().toString());
            }
        });

        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtUser.setText("");
                txtPass.setText("");
                txtInKey.setText("");
                txtHeader.setText("");
                txtHMAC.setText("");
            }
        });
        txtCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String message = "{\n" +
                        "   \"pan\":\"0000000812341234\"\n" +
                        "   \"processingCode\":\"360000\"\n" +
                        "   \"transactionAmount\":100000.00\n" +
                        "   \"transmissionDateTime\":\"20220728031702\"\n" +
                        "   \"systemTraceAuditNumber\":\"000009\"\n" +
                        "   \"localTransactionDateTime\":\"20220728031702\"\n" +
                        "   \"settlementDate\":\"20220728\"\n" +
                        "   \"captureDate\":\"20220728\"\n" +
                        "   \"merchantType\":\"6017\"\n" +
                        "   \"posEntryMode\":\"011\"\n" +
                        "   \"feeType\":\"C\"\n" +
                        "   \"feeAmount\":123456\n" +
                        "   \"acquiredId\":\"00000008\"\n" +
                        "   \"issuerId\":\"009\"\n" +
                        "   \"forwardingId\":\"008\"\n" +
                        "   \"rrn\":\"000009\"\n" +
                        "   \"approvalCode\":\"121212\"\n" +
                        "   \"terminalId\":\"123456\"\n" +
                        "   \"merchantId\":\"MH111111\"\n" +
                        "   \"merchantName\":\"alfamart\"\n" +
                        "   \"merchantCity\":\"Jakarta\"\n" +
                        "   \"merchantCountry\":\"62\"\n" +
                        "   \"productIndicator\":\"Q001\"\n" +
                        "   \"customerData\":\"AAAAAAAAAA\"\n" +
                        "   \"currencyCode\":\"360\"\n" +
                        "   \"postalCode\":\"12270\"\n" +
                        "   \"additionalField\":\"\"\n" +
                        "   \"customerPan\":\"\6666777788889999\"\n" +
                        "}";


                txtMessage.setText(message);
            }
        });
    }

    public static byte[] generateHmac(String message, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        mac.init(secretKeySpec);
        return mac.doFinal(message.getBytes());
    }
}
