package groups.vk.test.api;

public class APIConstants {
    private static final String TAG = APIConstants.class.getSimpleName();

    public static final String SCHEME = "https";
    public static final String BASIC_URL = "api.vk.com";
    public static final String PATH = "method";

    public static final String METHOD_GROUP_SEARCH = "groups.search";
    public static final String METHOD_WALL_GET = "wall.get";

    public static final String SEARCH_TEXT_PARAM = "q";
    public static final String VK_VERSION_PARAM = "v";
    public static final String ACCESS_TOKEN_PARAM = "access_token";
    public static final String GROUP_TYPE_PARAM = "type";

    public static final String GROUP_TYPE_OWNER_ID = "owner_id";
    public static final String GROUP_TYPE_COUNT = "count";
    public static final String GROUP_TYPE_FILTER = "filter";

    public static final String GROUP_TYPE_WALL = "w";
}
