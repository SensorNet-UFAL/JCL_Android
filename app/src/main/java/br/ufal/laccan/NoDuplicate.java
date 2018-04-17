package br.ufal.laccan;

import com.hpc.jcl_android.JCL_ANDROID_Facade;
import implementations.collections.JCLHashMap;
import implementations.dm_kernel.user.JCL_FacadeImpl;
import sensor.JCL_Sensor;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class NoDuplicate {
    public static void runAux(int max, int min, JCLHashMap<Integer,
            interfaces.kernel.JCL_Sensor> values, JCL_ANDROID_Facade jcl, interfaces.kernel.JCL_Sensor s) {
        if (!JCL_FacadeImpl.getInstance().containsGlobalVar(JCL_ANDROID_Facade.getInstance().getDevice() + JCL_Sensor.TypeSensor.TYPE_PHOTO.id + "_NUMELEMENTS")) {
            max = 0;
            min = 0;
            values = new JCLHashMap<Integer, interfaces.kernel.JCL_Sensor>(JCL_ANDROID_Facade.getInstance().getDevice() + JCL_Sensor.TypeSensor.TYPE_PHOTO.id + "_value");
            JCL_FacadeImpl.getInstance().instantiateGlobalVar(JCL_ANDROID_Facade.getInstance().getDevice() + JCL_Sensor.TypeSensor.TYPE_PHOTO.id + "_NUMELEMENTS", "0");
        }
        if ((max - min) > jcl.getSize().get(JCL_Sensor.TypeSensor.TYPE_PHOTO.id)) {
            values.remove(min++);
        }
        values.put(max, s);
    }

    public static InetAddress getLocalHostLANAddress(String nic) throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // Iterate all NICs (network interface cards)...
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();

                if (iface.getName().contains(nic)) {
                    // Iterate all IP addresses assigned to each card...
                    for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                        InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                        if (!inetAddr.isLoopbackAddress()) {

                            if (inetAddr.isSiteLocalAddress()) {
                                // Found non-loopback site-local address. Return it immediately...
                                return inetAddr;
                            } else if (candidateAddress == null) {
                                // Found non-loopback address, but not necessarily site-local.
                                // Store it as a candidate to be returned if site-local address is not subsequently found...
                                candidateAddress = inetAddr;
                                // Note that we don't repeatedly assign non-loopback non-site-local addresses as candidates,
                                // only the first. For subsequent iterations, candidate will be non-null.
                            }
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                // We did not find a site-local address, but we found some other non-loopback address.
                // Server might have a non-site-local address assigned to its NIC (or it might be running
                // IPv6 which deprecates the "site-local" concept).
                // Return this non-loopback candidate address...
                return candidateAddress;
            }
            // At this point, we did not find a non-loopback address.
            // Fall back to returning whatever InetAddress.getLocalHost() returns...
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }
}
