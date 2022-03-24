//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.Socket;
//import java.text.ParseException;
//
//public class HttpClient {
//
//    public static void main(String[] args) {
//        try {
//            String header = null;
//            if (args.length == 0) {
//                header = readHeader(System.in);
//            } else {
//                FileInputStream fis = new FileInputStream(args[0]);
//                header = readHeader(fis);
//                fis.close();
//            }
//            System.out.println("Заголовок: \n" + header);
//            /* отправляем запрос на сервер */
//            String answer = sendRequest(header);
//            /* выводим ответ в консоль */
//            System.out.println("Ответ от сервера: \n");
//            System.out.write(answer.getBytes());
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//            e.getCause().printStackTrace();
//        }
//    }
//
//    /**
//     * читаем поток и возвращаем содержимое в строку
//     */
//
//    public static String readHeader(InputStream strm) throws IOException {
//        byte[] buff = new byte[64 * 1024];
//        int length = strm.read(buff);
//        String res = new String(buff, 0, length);
//        return res;
//    }
//
//    /**
//     * отправляем запрос в соответствии с заголовком
//     */
//
//    public static String sendRequest(String httpHeader) throws Exception {
//        /* Из http заголовка берется арес сервера */
//        String host = null;
//        int port = 0;
//        try {
//            host = getHost(httpHeader);
//            port = getPort(host);
//            host = getHostWithoutPort(host);
//        } catch (Exception e) {
//            throw new Exception("Не удалось получить адрес сервера.", e);
//        }
//        /**
//         * отправляем запрос на сервер
//         */
//
//        Socket socket = null;
//        try {
//            socket = new Socket(host, port);
//            System.out.println("сокет создан: " + host + " port:" + port);
//            socket.getOutputStream().write(httpHeader.getBytes());
//            System.out.println("заголовок отправлен. \n");
//        } catch (Exception e) {
//            throw new Exception("Ошибка при отправке запроса: "
//                    + e.getMessage(), e);
//        }
//
//        /**
//         * ответ с сервера записываем в итоговую строку
//         */
//
//        String res = null;
//        try {
//            InputStreamReader isr = new InputStreamReader(socket
//                    .getInputStream());
//            BufferedReader bfr = new BufferedReader(isr);
//            StringBuffer sbf = new StringBuffer();
//            int ch = bfr.read();
//            while (ch != -1) {
//                sbf.append((char) ch);
//                ch = bfr.read();
//            }
//            res = sbf.toString();
//        } catch (Exception e) {
//            throw new Exception("Ошибка при прочтении ответа от сервера.", e);
//        }
//        socket.close();
//        return res;
//    }
//
//    /**
//     * возвращаем имя хоста из http заголовка.
//     */
//
//    private static String getHost(String header) throws ParseException {
//        final String host = "Host: ";
//        final String normalEnd = "\n";
//        final String msEnd = "\r\n";
//
//        int s = header.indexOf(host, 0);
//        if (s < 0) {
//            return "localhost";
//        }
//        s += host.length();
//        int e = header.indexOf(normalEnd, s);
//        e = (e > 0) ? e : header.indexOf(msEnd, s);
//        if (e < 0) {
//            throw new ParseException("В заголовке запроса не найдено " + "закрывающих символов после пункта Host.", 0);
//        }
//        String res = header.substring(s, e).trim();
//        return res;
//    }
//
//    /**
//     * Возвращаем номер порта
//     */
//
//    private static int getPort(String hostWithPort) {
//        int port = hostWithPort.indexOf(":", 0);
//        port = (port < 0) ? 80 : Integer.parseInt(hostWithPort
//                .substring(port + 1));
//        return port;
//    }
//
//    /**
//     * Возвращаем имя хоста без его порта
//     */
//
//    private static String getHostWithoutPort(String hostWithPort) {
//        int portPosition = hostWithPort.indexOf(":", 0);
//        if (portPosition < 0) {
//            return hostWithPort;
//        } else {
//            return hostWithPort.substring(0, portPosition);
//        }
//    }
//}