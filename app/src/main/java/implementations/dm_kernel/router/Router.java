package implementations.dm_kernel.router;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import br.ufal.laccan.NoDuplicate;
import commom.GenericConsumer;
import commom.GenericResource;
import commom.JCL_handler;
import implementations.dm_kernel.Server;

public class Router extends Server implements Runnable {

    private ConcurrentMap<String, List<JCL_handler>> superPeer;
    private ConcurrentMap<String, String> superPeerHost;
    private AtomicInteger peerID;
    private GenericResource<JCL_handler> re;
    private Integer routerPort;
    private String nic;

    public Router(int routerPort, GenericResource<JCL_handler> re, String nic) throws IOException {
        // TODO Auto-generated constructor stub
        super(routerPort);
        this.superPeer = new ConcurrentHashMap<String, List<JCL_handler>>();
        this.superPeerHost = new ConcurrentHashMap<String, String>();
        peerID = new AtomicInteger(0);
        this.routerPort = routerPort;
        this.re = re;
        this.nic = nic;
        //	LinkedList<JCL_handler>
    }

    @Override
    public <K extends JCL_handler> GenericConsumer<K> createSocketConsumer(GenericResource<K> r, AtomicBoolean kill) {
        // TODO Auto-generated method stub
        String IP = getIP();
        return new SocketConsumer<K>(r, kill, superPeer, peerID, re, this.routerPort, IP, this.superPeerHost);
    }

    @Override
    protected void beforeListening() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void duringListening() {
        // TODO Auto-generated method stub

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        this.begin();
    }

    //	private String[] getNameIPPort(){
    private String getIP() {
        Map<String, String> IPPort = new HashMap<String, String>();
        try {
            //InetAddress ip = InetAddress.getLocalHost();
            InetAddress ip = getLocalHostLANAddress();

            return ip.getHostAddress();

        } catch (Exception e) {

            try {
                InetAddress ip = InetAddress.getLocalHost();

                return ip.getHostAddress();
            } catch (UnknownHostException e1) {
                System.err.println("cannot collect host address");
                return null;
            }


        }
    }

    private InetAddress getLocalHostLANAddress() throws UnknownHostException {
        return NoDuplicate.getLocalHostLANAddress(nic);
    }

}
