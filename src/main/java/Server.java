import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import implementations.MemoryCache;
import implementations.RequestHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    String file = "./kvBackup.txt" ;
    public void runServer() throws IOException {
        MemoryCache memoryCache = new MemoryCache();
        memoryCache.restoreFromFile(file);
        HttpServer server = HttpServer.create(new InetSocketAddress(8000),0);
        HttpContext context = server.createContext("/");
        context.setHandler(new RequestHandler(memoryCache));

        Thread thread = new Thread(()->{
            memoryCache.backupToFile(file);
        });
        server.start();
        thread.start();
    }
}
