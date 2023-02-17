package abstractions;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public interface IRequestHandler extends HttpHandler {
    @Override
    void handle(HttpExchange httpExchange) throws IOException;

}
