import com.monchickey.dataprocess.Transform;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransformTest {
    @Test
    public void testIPv4() {
        Assertions.assertEquals(Transform.IPv4StringToNumber("196.99.100-3"), -1L);
        Assertions.assertEquals(Transform.IPv4StringToNumber("192.168.10.5"), 3232238085L);
    }

    @Test
    public void testMAC() {
        Assertions.assertEquals(Transform.MACStringToNumber("ab:63:ccc:32:56"), -1L);
        Assertions.assertEquals(Transform.MACStringToNumber("48-5F-99-B9-70-9D"), 79575438160029L);
    }

}
