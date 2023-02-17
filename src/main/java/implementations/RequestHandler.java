package implementations;

import abstractions.IRequestHandler;
import com.sun.net.httpserver.HttpExchange;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements IRequestHandler {
    MemoryCache memoryCache;
    public  RequestHandler (MemoryCache memoryCache) {
        this.memoryCache = memoryCache;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            InputStream inStream = httpExchange.getRequestBody();
            ObjectMapper mapper = new ObjectMapper();
            String data = new String(inStream.readAllBytes(), StandardCharsets.UTF_8);
            KVInput kvInput = mapper.readValue(data, KVInput.class);
            switch (httpExchange.getRequestMethod()) {
                case "GET":
                    memoryCache.get(kvInput.getKey());
                    System.out.printf("get  %s to key: %s \n", kvInput.getValue(), kvInput.getKey());
                case "POST":
                    memoryCache.add(kvInput);
                    System.out.printf("added %s to key: %s \n", kvInput.getValue(), kvInput.getKey());
                case "DELETE":
                    memoryCache.delete(kvInput);
                    System.out.printf("delete %s to key: %s \n", kvInput.getValue(), kvInput.getKey());
                case "PUT" :
                    memoryCache.update(kvInput);
            }
            httpExchange.sendResponseHeaders(200, 0);
            httpExchange.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

}
