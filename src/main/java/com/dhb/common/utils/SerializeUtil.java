package com.dhb.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhb.common.exception.SerializeException;

/**
 * Created by darkstar on 2017/4/24.
 */
public class SerializeUtil {

	private static final Logger log = LoggerFactory.getLogger(SerializeUtil.class);
    /**
     * 序列化
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
    	log.debug("开始序列化");
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            log.debug("结束序列化");
            return bytes;
        } catch (Exception e) {
        	log.error("序列化失败："+e);
        }
        
        return null;
    }

    /**
     * 反序列化
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
    	log.debug("开始反序列化");
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            log.debug("结束反序列化");
            return ois.readObject();
        } catch (Exception e) {
        	log.error("反序列化失败："+e);
        }
        return null;
    }
    public static Object deserialize(byte[] bytes){
		ObjectInputStream ois = null;
		ByteArrayInputStream bis = null;
		Object obj = null;
		try{
			bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);
			obj = ois.readObject();
		} catch(Exception e){
			log.error("deserialize error !", e);
			throw new SerializeException("deserialize error !");
		} finally{
			try {
				if(ois != null){
					ois.close();
				}
				if(bis != null){
					bis.close();
				}
			} catch (IOException e) {
				log.error("closed stream error !", e);
			}
		}
		return obj;
	}


}
