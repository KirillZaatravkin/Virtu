import org.junit.Test;
import source.system.service.RangeService;

import static junit.framework.Assert.*;


public class TestRange {
    @Test
    public void testConvertIpToInt() {
        RangeService rangeService = new RangeService();
        assertEquals(16843009, rangeService.convertIpToInt(rangeService.splitIp("1.1.1.1")));
    }

    @Test
    public void testValidIp() {
        RangeService rangeService = new RangeService();
        assertNotNull(rangeService.validIp(null));
        assertTrue(rangeService.validIp("10.33.2.2"));
    }

    @Test
    public void testConvertIntToIp() {
        RangeService rangeService = new RangeService();
        String ip="1.1.1.1";
        assertEquals(ip, rangeService.convertIntToIp(16843009));
    }
    @Test(timeout = 100)
    public void testGetRange() {
        RangeService rangeService = new RangeService();
       rangeService.getRange("1.1.1.1","1.1.1.2");
    }

}


