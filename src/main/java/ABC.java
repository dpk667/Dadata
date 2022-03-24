import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ABC {
    public static void main(String[] args) {

        /* присваиваем необходимый URL для поиска городов */

        String url = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/address";
        HttpURLConnection connection = null;

        try {
            /* создаем новый объект, который откроет соединение по необходимому URL */
            connection = (HttpURLConnection) new URL(url).openConnection();

            /* присваиваем необходимые ключи  запросу */
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("X-Secret", "d1f291eeb4f82de8850813aa749aca5b771c682d");
            connection.setRequestProperty("Authorization", "Token 2d816ab0907319b434207bf5d7eb35e5f70c8965");
            connection.setRequestProperty("query", "краснодар моск");

            connection.connect();

            /* создаем переменную для вывода ответа запроса */
            StringBuilder sb = new StringBuilder();

            /* задаем условие корректности работы соединения */
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                String line;

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
                System.out.println(sb.toString());
            } else {
                System.out.println("fail: " + connection.getResponseCode() + ", " + connection.getResponseMessage());
            }

        } catch (Throwable cause) {
            cause.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}


