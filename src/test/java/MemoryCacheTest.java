import abstractions.IMemoryCache;
import implementations.KVInput;
import implementations.MemoryCache;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
@RunWith(MockitoJUnitRunner.class)
public class MemoryCacheTest {
    @InjectMocks
    MemoryCache memoryCache;
    @Mock
    KVInput kvInput;

     AutoCloseable closeable;

    @BeforeEach
    void initMocks() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeMocks() throws Exception {
        closeable.close();
    }

    @Test
    public void add_WhenIsKvInput_ShouldAddedSuccess() throws NoSuchFieldException, IllegalAccessException {
        String key = "1";
        String value = "zeynep";
        Mockito.when(kvInput.getKey()).thenReturn(key);
        Mockito.when(kvInput.getValue()).thenReturn(value);
        Field privateListField = MemoryCache.class.getDeclaredField("list");
        privateListField.setAccessible(true);
        HashMap<String, String> privateMap = (HashMap<String, String>) privateListField.get(memoryCache);
        memoryCache.add(kvInput);

        Assert.assertNotNull(privateMap.get(kvInput.getKey()));
        Assert.assertEquals(value,privateMap.get(key));
        Assert.assertEquals(1,privateMap.size());

        Mockito.verify(kvInput, Mockito.times(2)).getKey();
        Mockito.verify(kvInput, Mockito.times(1)).getValue();
    }

    @Test
    public void get_WhenIsCalled_ShouldBeGet() throws NoSuchFieldException, IllegalAccessException {
        String key = "1";
        String value = "zeynep";
        Mockito.when(kvInput.getKey()).thenReturn(key);
        Mockito.when(kvInput.getValue()).thenReturn(value);
        memoryCache.add(kvInput);
        Field privateListField = MemoryCache.class.getDeclaredField("list");
        privateListField.setAccessible(true);
        HashMap<String, String> privateMap = (HashMap<String, String>) privateListField.get(memoryCache);

        memoryCache.get(key);

        //assertıons
        Assert.assertNotNull(value,privateMap.get(key));
        Assert.assertEquals(value,privateMap.get(key));
    }
    @Test
    public void update_WhenIsCalled_ShouldUpdateListEleman() throws NoSuchFieldException, IllegalAccessException {
        String key = "1";
        String value = "zeynep";
        String updatedValue = "zeynep2";
        Mockito.when(kvInput.getKey()).thenReturn(key);
        Mockito.when(kvInput.getValue()).thenReturn(value);


        Field privateListField = MemoryCache.class.getDeclaredField("list");
        privateListField.setAccessible(true);
        HashMap<String, String> privateMap = (HashMap<String, String>) privateListField.get(memoryCache);

        memoryCache.add(kvInput);
        memoryCache.update(key, updatedValue);
        Assert.assertEquals(updatedValue,privateMap.get(key));
    }
    @Test
    public void delete_WhenIsCalled_ShouldDeleteItem() throws NoSuchFieldException, IllegalAccessException {
        String key = "1";
        String value = "zeynep";
        Mockito.when(kvInput.getKey()).thenReturn(key);
        Mockito.when(kvInput.getValue()).thenReturn(value);


        Field privateListField = MemoryCache.class.getDeclaredField("list");
        privateListField.setAccessible(true);
        HashMap<String, String> privateMap = (HashMap<String, String>) privateListField.get(memoryCache);
        memoryCache.add(kvInput);

        memoryCache.delete(kvInput);
        Assert.assertEquals(0,privateMap.size());
        Assert.assertEquals(null, privateMap.get(key));
    }

}
