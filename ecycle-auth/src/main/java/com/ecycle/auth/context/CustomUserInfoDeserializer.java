package com.ecycle.auth.context;

import com.ecycle.auth.model.User;
import com.ecycle.common.context.UserInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author wangweichen
 * @Date 2024/4/2
 * @Description TODO
 */
@Component
public class CustomUserInfoDeserializer extends JsonDeserializer<UserInfo> {
    @Override
    public UserInfo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        UserInfo userInfo = new UserInfo();

        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        ObjectNode root = mapper.readTree(jsonParser);

//        User user = mapper.treeToValue(root.get("user"), User.class);
//        userInfo.setUser(user);

        return userInfo;
    }
}
