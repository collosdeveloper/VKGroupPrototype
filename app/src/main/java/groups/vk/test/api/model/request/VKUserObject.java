package groups.vk.test.api.model.request;

import java.io.Serializable;

public class VKUserObject implements Serializable {
    private static final String TAG = VKUserObject.class.getSimpleName();

    private long userId;
    private String accessToken;

    public VKUserObject(long userId, String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }

    public long getUserId() {
        return userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String toString() {
        return "VKUserObject{" +
                "userId=" + userId +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
