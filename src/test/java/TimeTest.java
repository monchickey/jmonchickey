import com.monchickey.dataprocess.TimeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TimeTest {
    @Test
    public void testWeek() {
        Assertions.assertEquals(TimeUtil.dateToWeek(2022, 6, 3), 5);
    }
}
