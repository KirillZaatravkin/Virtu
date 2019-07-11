import org.apache.taglibs.standard.lang.jstl.NullLiteral;
import org.junit.Test;
import source.system.service.RangeService;

import static junit.framework.Assert.*;


/**
 * Created by кирюха on 11.07.2019.
 */
public class TestRange {
    @Test
    public void testConvertIpToInt() {
        RangeService rangeService = new RangeService();

        assertEquals(16843009, rangeService.convertIpToInt(rangeService.splitIp("1.1.1.1")));

    }

    @Test
    public void testValidIp() {
        RangeService rangeService = new RangeService();
        assertNotNull(null);
    }
}
