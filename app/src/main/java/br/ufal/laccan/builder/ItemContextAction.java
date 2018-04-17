package br.ufal.laccan.builder;

import implementations.dm_kernel.MessageGenericImpl;
import interfaces.kernel.JCL_message_generic;

public class ItemContextAction {
    private String deviceKey;
    private String IP;
    private String port;
    private String mac;
    private String portS;
    private String hostIP;
    private String hostport;
    private JCL_message_generic msg;

    public ItemContextAction() {
        msg = new MessageGenericImpl();
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public ItemContextAction setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
        return this;
    }

    public String getIP() {
        return IP;
    }

    public ItemContextAction setIP(String IP) {
        this.IP = IP;
        return this;
    }

    public String getPort() {
        return port;
    }

    public ItemContextAction setPort(String port) {
        this.port = port;
        return this;
    }

    public String getMac() {
        return mac;
    }

    public ItemContextAction setMac(String mac) {
        this.mac = mac;
        return this;
    }

    public String getPortS() {
        return portS;
    }

    public ItemContextAction setPortS(String portS) {
        this.portS = portS;
        return this;
    }

    public String getHostIP() {
        return hostIP;
    }

    public ItemContextAction setHostIP(String hostIP) {
        this.hostIP = hostIP;
        return this;
    }

    public String getHostport() {
        return hostport;
    }

    public ItemContextAction setHostport(String hostport) {
        this.hostport = hostport;
        return this;
    }

    public JCL_message_generic getMsg() {
        return msg;
    }

    public ItemContextAction setMsg(JCL_message_generic msg) {
        this.msg = msg;
        return this;
    }
}
