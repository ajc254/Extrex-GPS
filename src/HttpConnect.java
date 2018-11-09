package extrex;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 *
 * @author Alexander Cossins 2018
 */
class HttpConnect {

    /**
     * Perform the HTTP connection and parse data received.
     *
     * @param method Type of HTTP connection.
     * @param url URL to connect to.
     * @param headers Header of request.
     * @param body Body of request.
     *
     * @return Map data.
     * @throws IOException If there are connection issues. (Failed HTTP request)
     */
    static byte[] httpConnect(String method,
            String url,
            String[][] headers,
            byte[] body
    ) throws IOException {

        try {

            // Setup connection.
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();

            conn.setRequestMethod(method);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(Const.HTTP_TIMEOUT);
            conn.setReadTimeout(Const.HTTP_TIMEOUT);
            for (int i = 0; i < headers.length; i++) {
                conn.setRequestProperty(headers[i][0], headers[i][1]);
            }
            conn.connect();

            // Send data.
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.write(body);
            dos.flush();
            dos.close();

            // Receive data.
            DataInputStream dis = new DataInputStream(conn.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            byte[] buffer = new byte[Const.HTTP_BUFFSIZE];
            for (;;) {
                int n = dis.read(buffer);
                if (n > 0) {
                    bos.write(buffer, 0, n);
                } else {
                    break;
                }
            }

            byte response[] = bos.toByteArray();
            dis.close();

            // Teardown connection.
            conn.disconnect();

            return response;

        // Catch all exceptions apart from the loss of internet connection.
        } catch(MalformedURLException | ProtocolException e) {
            System.out.println("\nError with URL request format.\n");
            return null;
        }
    }
}
