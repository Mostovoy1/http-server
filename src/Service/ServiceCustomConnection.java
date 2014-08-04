package Service;

/**
 * Created with IntelliJ IDEA.
 * User: Мост
 * Date: 02.08.14
 * Time: 13:52
 * To change this template use File | Settings | File Templates.
 */
public class ServiceCustomConnection {
    private String ip;
    private String uri;
    private long timestamp;
    private long sendbytes;
    private long recievedbytes;
    private long connection_registred;
    private long connection_inactive;
    private int speed;

    public ServiceCustomConnection(long connection_registred) {
        this.connection_registred = connection_registred;
    }

    public ServiceCustomConnection(String ip, String uri, long timestamp, long sendbytes, long recievedbytes, long connection_registred, long connection_inactive) {
        this.ip = ip;
        this.uri = uri;
        this.timestamp = timestamp/1000;
        this.sendbytes = sendbytes;
        this.recievedbytes = recievedbytes;
        this.connection_registred = connection_registred;
        this.connection_inactive = connection_inactive;
        this.speed = getSpeed();
    }

    public ServiceCustomConnection(long timestamp, int sendbytes, int recievedbytes, long connection_registred, long connection_inactive) {
        this.timestamp = timestamp;
        this.sendbytes = sendbytes;
        this.recievedbytes = recievedbytes;
        this.connection_registred = connection_registred;
        this.connection_inactive = connection_inactive;
        this.speed = this.getSpeed();
    }
    public int getSpeed()
    {
        return (int) (((sendbytes+recievedbytes)*1000)/(connection_inactive - connection_registred));
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getSendbytes() {
        return sendbytes;
    }

    public void setSendbytes(long sendbytes) {
        this.sendbytes = sendbytes;
    }

    public long getRecievedbytes() {
        return recievedbytes;
    }

    public void setRecievedbytes(long recievedbytes) {
        this.recievedbytes = recievedbytes;
    }

    public long getConnection_registred() {
        return connection_registred;
    }

    public void setConnection_registred(long connection_registred) {
        this.connection_registred = connection_registred;
    }

    public long getConnection_inactive() {
        return connection_inactive;
    }

    public void setConnection_inactive(long connection_inactive) {
        this.connection_inactive = connection_inactive;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setSpeed() {
        this.speed = (int) (((sendbytes+recievedbytes)*1000/(connection_inactive - connection_registred)));
    }

    @Override
    public String toString() {
        return  "ip='" + ip + '\'' +
                ", uri='" + uri + '\'' +
                ", timestamp=" + timestamp +
                ", sendbytes=" + sendbytes +
                ", recievedbytes=" + recievedbytes +
                ", connection_registred=" + connection_registred +
                ", connection_inactive=" + connection_inactive +
                ", speed=" + speed + "\n\r"
                ;
    }
}
