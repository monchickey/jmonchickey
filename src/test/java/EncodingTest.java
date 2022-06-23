import com.monchickey.dataprocess.Encoding;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EncodingTest {
    @Test
    public void testHex() {
        String h = "01a8e97236";
        byte[] b = Encoding.hexDecode(h);
        Assertions.assertEquals(b.length, 5);
        Assertions.assertEquals(Encoding.hex(b), h);
    }
}
