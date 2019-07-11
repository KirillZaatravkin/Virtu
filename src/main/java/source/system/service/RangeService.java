package source.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.plugin.javascript.navig.Array;

import javax.xml.soap.SAAJResult;
import java.util.ArrayList;

/**
 * Created by кирюха on 11.07.2019.
 */

@Service
public class RangeService {



    public boolean validIp(String addr) {
        try {
            if ( addr == null || addr.isEmpty() ) {
                return false;
            }

            String[] parts = addr.split( "\\." );
            if ( parts.length != 4 ) {
                return false;
            }

            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < 0) || (i > 255) ) {
                    return false;
                }
            }
            if ( addr.endsWith(".") ) {
                return false;
            }
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }


    public int[] splitIp(String addr) {

        String[] resuit = addr.split("\\.");
        int[] resultInt = new int[4];
        for (int i = 0; i < 4; i++) {
            resultInt[i] = Integer.valueOf(resuit[i]);
        }
        return resultInt;



    }

    public int convertIpToInt(int[] addr) {

        return  addr[0]*256*256*256+addr[1]*256*256+addr[2]*256+addr[3];
    }


    public String convertIntToIp(int addr) {

        int number0 = (int) (addr / Math.pow(256, 3));
        int number1 = (int) ((addr - number0 * Math.pow(256, 3)) / Math.pow(256, 2));
        int number2 = (int) ((addr - number0 * Math.pow(256, 3) - number1 * Math.pow(256, 2)) / 256);
        int number3 = (int) (addr - number0 * Math.pow(256, 3) - number1 * Math.pow(256, 2) - number2 * 256);
        return String.valueOf(number0) + '.' + String.valueOf(number1) + '.' + String.valueOf(number2) + '.' + String.valueOf(number3);
    }


    public ArrayList<String> getRange(String addres1, String addres2) {

        int[] addr1=splitIp(addres1);
        int[] addr2=splitIp(addres2);
        int a1 = convertIpToInt(addr1);
        int a2 = convertIpToInt(addr2);
        ArrayList<String> result = new ArrayList<String>();
        while (a1 != a2-1) {
            a1++;
            result.add(convertIntToIp(a1));
        }
        return result;
    }

}
