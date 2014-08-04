package Service;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Мост
 * Date: 27.07.14
 * Time: 18:05
 * To change this template use File | Settings | File Templates.
 */
public class ServiceLogger {
    public static final byte[] CONTENT = { 'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd' };
    static public final int PORT = Integer.parseInt(System.getProperty("port", "8081"));
    private static int count_query = 0;
    private static HashSet<String> unique_hosts = new HashSet<>();
    private static ArrayList<ServiceQueryByIP> count_query_by_ip = new ArrayList<>();
    private static HashMap<String, Integer> count_url_requests = new HashMap<>();
    private static int count_of_open_channels = 0;
    private static ArrayList<ServiceCustomConnection> connections = new ArrayList<>();

    public static List<ServiceCustomConnection> getConnections() {
        if (connections.size()>16)
        return connections.subList(connections.size()-16,connections.size());
        else
        return connections;
    }

    public static void setConnections(ArrayList<ServiceCustomConnection> connections) {
        ServiceLogger.connections = connections;
    }

    public synchronized static int getCount_of_open_channels() {
        return count_of_open_channels;
    }

    public synchronized static void inc_Count_of_open_channels() {
        ServiceLogger.count_of_open_channels++;
    }
    public synchronized static void dec_Count_of_open_channels() {
        ServiceLogger.count_of_open_channels--;
    }


    public synchronized static void inc_count_url_requests(String url)
    {
        if (!count_url_requests.containsKey(url))
            count_url_requests.put(url, new Integer(1));
        else
           for (Map.Entry<String,Integer> map : count_url_requests.entrySet())
           {
               if (map.getKey().equals(url))
                   map.setValue(map.getValue()+1);
           }

    }
    public synchronized static HashMap<String,Integer> getCount_url_requests()
    {
        return count_url_requests;
    }
     public synchronized static void inc_count_unique_hosts(String host)
     {
         if (!unique_hosts.contains(host))
             unique_hosts.add(host);
     }
    public synchronized static int get_count_unique_hosts()
    {
        return unique_hosts.size();
    }

    public synchronized static void inc_count()
    {
        count_query++;
    }
    public synchronized static int getCount_query()
    {
        return count_query;
    }

    public synchronized static HashSet<String> getUnique_hosts() {
        return unique_hosts;
    }

    public synchronized static void setUnique_hosts(HashSet<String> unique_hosts) {
        ServiceLogger.unique_hosts = unique_hosts;
    }

    public synchronized static ArrayList<ServiceQueryByIP> getCount_query_by_ip() {
        return count_query_by_ip;
    }
    public synchronized static void setCount_query_by_ip(String host) {
        ServiceQueryByIP query_by_ip =  new ServiceQueryByIP(host);
        if (!count_query_by_ip.contains(query_by_ip))
            count_query_by_ip.add(query_by_ip);
        if (count_query_by_ip.contains(query_by_ip))
        {
         ServiceQueryByIP second_query = new ServiceQueryByIP(host);
          query_by_ip =  count_query_by_ip.get(count_query_by_ip.indexOf(query_by_ip));
          second_query.setCount(query_by_ip.getCount()+1);
          count_query_by_ip.remove(query_by_ip);
          count_query_by_ip.add(second_query);
         }
        }
}



