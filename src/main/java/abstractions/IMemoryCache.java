package abstractions;

import implementations.KVInput;

public interface IMemoryCache extends IBackup {

         void add(KVInput kvInput);

         String get(String key);

         void update(String key, String value);

         void delete(KVInput kvInput);

}
