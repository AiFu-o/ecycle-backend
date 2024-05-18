import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * @author wangweichen
 * @Date 2024/5/17
 * @Description TODO
 */
public class AddressArrayToJSONObject {

    // 源数据从这拿 文件太大先删了 https://gitee.com/modood/Administrative-divisions-of-China
    public static void main(String[] args) {
        Path source = Path.of("/Users/wangweichen/Documents/git/ecycle/ecycle-backend/ecycle-commodity/src/main/resources/address/cities.json");
        Path input = Path.of("/Users/wangweichen/Documents/git/ecycle/ecycle-backend/ecycle-commodity/src/main/resources/address/city-list.json");
        build(source, input, "provinceCode");
    }


    private static void build(Path source, Path input, String pKey){
        JSONObject resultList = new JSONObject();
        String cityJsonStr;
        try (InputStream cityFile = Files.newInputStream(source)) {
            cityJsonStr = new String(cityFile.readAllBytes());
            JSONArray json = JSONArray.parseArray(cityJsonStr);
            for (Object obj : json) {
                JSONArray list = new JSONArray();
                JSONObject data = (JSONObject) obj;
                JSONObject saveData = new JSONObject();
                String pCode = data.getString(pKey);
                if (resultList.containsKey(pCode)) {
                    list = resultList.getJSONArray(pCode);
                }
                saveData.put("code", data.get("code"));
                saveData.put("name", data.get("name"));
                list.add(saveData);
                resultList.put(pCode, list);
            }
            try (OutputStream ot = Files.newOutputStream(input)) {
                ot.write(resultList.toJSONString().getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
