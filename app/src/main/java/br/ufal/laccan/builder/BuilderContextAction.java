package br.ufal.laccan.builder;

import implementations.dm_kernel.MessageGenericImpl;
import implementations.util.ObjectWrap;
import interfaces.kernel.JCL_IoTfacade;
import interfaces.kernel.JCL_message_generic;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Map;

public class BuilderContextAction {
    public ItemContextAction get(java.util.Map.Entry<String, String> deviceNickname,
                                 Map<String, Map<String, String>> devices) {
        ItemContextAction itemContextAction = new ItemContextAction();
        itemContextAction.setIP(devices.get(deviceNickname.getKey()).get("IP"))
                .setPort(devices.get(deviceNickname.getKey()).get("PORT"))
                .setMac(devices.get(deviceNickname.getKey()).get("MAC"))
                .setPortS(devices.get(deviceNickname.getKey()).get("PORT_SUPER_PEER"))
                .setHostIP(devices.get(deviceNickname.getKey()).get("IP"))
                .setHostport(devices.get(deviceNickname.getKey()).get("PORT"))
                .setMsg(new MessageGenericImpl());
        return itemContextAction;
    }

    public ItemContextAction get(Map.Entry<String, Map<String, String>> hostPort, Object Key, String gvName) {
        ItemContextAction itemContextAction = new ItemContextAction();

        itemContextAction.setIP(hostPort.getValue().get("IP"))
                .setPort(hostPort.getValue().get("PORT"))
                .setMac(hostPort.getValue().get("MAC"))
                .setPortS(hostPort.getValue().get("PORT_SUPER_PEER"));

        // ################ Serialization key ########################
        LinkedBuffer buffer = LinkedBuffer.allocate(1048576);
        ObjectWrap objW = new ObjectWrap(Key);
        Schema scow = RuntimeSchema.getSchema(ObjectWrap.class);
        Key = ProtobufIOUtil.toByteArray(objW, scow, buffer);
        // ################ Serialization key ########################

        Object[] ob = {gvName, Key};
        itemContextAction.getMsg().setRegisterData(ob);
        return itemContextAction;
    }

    public ItemContextAction get(Map<String, Map<String, String>> devices, String contextNickname) {
        ItemContextAction itemContextAction = new ItemContextAction();
        String deviceKey = (String) JCL_IoTfacade.PacuHPC.getValue(contextNickname + "_CONTEXT").getCorrectResult();
        itemContextAction.setDeviceKey(deviceKey)
                .setIP(devices.get(deviceKey).get("IP"))
                .setPort(devices.get(deviceKey).get("PORT"))
                .setMac(devices.get(deviceKey).get("MAC"))
                .setPortS(devices.get(deviceKey).get("PORT_SUPER_PEER"));
        return itemContextAction;
    }
}
