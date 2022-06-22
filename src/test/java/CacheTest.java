import com.monchickey.cache.LRUCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CacheTest {

    @Test
    public void testLRUCache() {
        LRUCache<String, Integer> lruCache = new LRUCache<>(100);
        for(int i = 0; i < 100; i++) {
            lruCache.put(i + "", 1);
        }
        Assertions.assertEquals(lruCache.size(), 100);
        System.out.println(lruCache.get("0"));
        lruCache.put("101", 3);
        Assertions.assertEquals(lruCache.size(), 100);
        Assertions.assertTrue(lruCache.containsKey("0"));
        Assertions.assertFalse(lruCache.containsKey("1"));
    }
}
