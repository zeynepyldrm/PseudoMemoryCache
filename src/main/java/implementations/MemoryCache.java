package implementations;

import abstractions.IMemoryCache;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

public class MemoryCache implements IMemoryCache {
    
    private ObjectMapper objectMapper = new ObjectMapper();
    private HashMap<String, String> list = new HashMap<String, String>();

    @Override
    public void add(KVInput kvInput) {
        list.put(kvInput.getKey(), kvInput.getValue());
    }

    @Override
    public String get(String key) {
        return list.get(key);
    }

    @Override
    public void update(String key,String value) {

        if(list.containsKey(key)) {
            list.put(key,value);
        }
    }

    @Override
    public void delete(KVInput kvInput) {
        list.remove(kvInput.getKey(), kvInput.getValue());
    }

    @Override
    public void backupToFile(String filePath) {
        try {
            while (true) {
                Thread.sleep(5000);
                System.out.println(" The file is printing....");
                ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
                objectWriter.writeValue(new File(filePath),list);
                //System.out.println(" Test backup...."+ list);
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    @Override
    public void restoreFromFile(String filePath) {
        try {
            this.list =  objectMapper.readValue(Path.of(filePath).toFile(), HashMap.class);
            System.out.println("Backup is fetching...:  " + list);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
}
