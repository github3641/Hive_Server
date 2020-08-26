package org.example.dc.srv.utils;

import org.apache.commons.lang.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * Project: Hive_Server
 * Package: org.example.dc.srv.utils
 * Class: YamlUtils
 * Author: Peng Sun
 * Date: 2020/8/26
 * Version: 1.0
 * Description:
 */
public class YamlUtils {

    /**
     * 以指定class为定位,获取制定文件名的配置文件,并读取
     *
     * @param fileName 文件名为空,读取默认配置文件
     * @return
     */
    public static Map<String, String> getYamlByFileName(String fileName) {
        Map<String, String> result = new HashMap<String, String>();
        try {
            if (StringUtils.isBlank(fileName)) {
                throw new RuntimeException("必须指定一个配置文件!");
            }

            System.out.println("加载配置文件:" + fileName);
            Yaml yaml = new Yaml();
            InputStream is = new FileInputStream(fileName);
            Map<String, Object> params = yaml.loadAs(is, Map.class);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof Map) {
                    eachYaml(entry.getKey(), (Map<String, Object>) entry.getValue(), result);
                } else {
                    result.put(entry.getKey(), entry.getValue().toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 使用递归进行深度转换,将Map<String,Object>转换为Map<String,String>;
     *
     * @param key 父级key
     * @param map 父级entry
     */
    private static void eachYaml(String key, Map<String, Object> map, Map<String, String> result) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String newKey = "";
            if (StringUtils.isNotEmpty(key)) {
                newKey = (key + "." + entry.getKey());
            } else {
                newKey = entry.getKey();
            }
            if (entry.getValue() instanceof Map) {
                eachYaml(newKey, (Map<String, Object>) entry.getValue(), result);
            } else {
                if (entry != null && entry.getValue() != null)
                    result.put(newKey, entry.getValue().toString());
            }
        }
    }


    /**
     * 根据key 获取指定的值(指定文件)
     */
    public static String getValue(String fileName, String key) {
        Map<String, String> result = getYamlByFileName(fileName);
        if (result == null || StringUtils.isBlank(result.get(key))) {
            return null;
        }
        return result.get(key);
    }

}
