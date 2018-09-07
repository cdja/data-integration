import com.chedaojunan.report.model.DatahubDeviceData;
import com.chedaojunan.report.utils.WriteDatahubUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class DataHubTest002 {

  private static final Logger logger = LoggerFactory.getLogger(DataHubTest002.class);


  public void runDatahub(int i) {

    WriteDatahubUtil datahubUtil = WriteDatahubUtil.getInstance();
    long serverTime = System.currentTimeMillis();

    ArrayList<DatahubDeviceData> list = new ArrayList<>();
    System.out.println("serverTime Start:="+serverTime);
    for (int m = 0; m < 10000; m++) {
      for (int j = 0; j < 30; j++) {
        DatahubDeviceData accessData;
        accessData = new DatahubDeviceData();
        accessData.setDeviceImei("test000002");
        accessData.setDeviceId("test000002" + j + "");
        accessData.setTripId(i + "");
        accessData.setLocalTime("1521478861000");
        accessData.setServerTime(serverTime + "");
        accessData.setLatitude(39.990475 + 0.00001 * j);
        accessData.setLongitude(116.481499 + 0.00001 * j);
        accessData.setAltitude(30.98);
        accessData.setDirection(98.00);
        accessData.setGpsSpeed(98.00);

        list.add(accessData);
      }
      try {
        datahubUtil.putRecords(list);
        list.clear();
        long tt = System.currentTimeMillis() - serverTime;
        System.out.println("第" + m + "批数据，用时" + tt + "毫秒");
      } catch (Exception ex) {
        ex.printStackTrace();//handle exception here
      }
    }
    long tt = System.currentTimeMillis() - serverTime;
    System.out.println("总用时" + tt + "毫秒");
  }

  public static void main(String[] args) {
    DataHubTest002 dataHubTest = new DataHubTest002();
    try {
      int i = 0;
//      while(true){
//        i++;
        dataHubTest.runDatahub(i);
//        Thread.sleep(0);
//      }
    } catch (Exception e) {
    }
  }
}
