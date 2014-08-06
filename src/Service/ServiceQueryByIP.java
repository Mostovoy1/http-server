package Service;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Мост
 * Date: 28.07.14
 * Time: 23:29
 * To change this template use File | Settings | File Templates.
 */
public class ServiceQueryByIP
{
    private  String ip ;
    private Integer count;
    private Date date_last_query;

    public ServiceQueryByIP(String ip) {
      synchronized (this)
      {
        this.ip = ip;
        this.count = 0;
        this.date_last_query = new Date();
    }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }




    public Date getDate_last_query() {
        return date_last_query;
    }

    public void setDate_last_query(Date date_last_query) {
        this.date_last_query = date_last_query;
    }

    public  Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return
                "\n\r" +  "ip='" + ip + '\'' +
                ", count='" + count + '\'' +
                ", date_last_query=" + date_last_query  ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceQueryByIP queryByIP = (ServiceQueryByIP) o;

        if (ip != null ? !ip.equals(queryByIP.ip) : queryByIP.ip != null) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = ip != null ? ip.hashCode() : 0;
        result = 31 * result + (count != null ? count.hashCode() : 0);
        return result;
    }
}
