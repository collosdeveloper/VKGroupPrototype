package groups.vk.test.api.model.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupList implements Serializable {
    private static final String TAG = GroupList.class.getSimpleName();

    private Response response;

    public Response getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "GroupList{" +
                "response=" + response +
                '}';
    }

    public class Response implements Serializable {
        private final String TAG = Response.class.getSimpleName();

        private Long count;
        private ArrayList<GroupOneItem> items;

        public Long getCount() {
            return count;
        }

        public ArrayList<GroupOneItem> getItems() {
            return items;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "TAG='" + TAG + '\'' +
                    ", count=" + count +
                    ", items=" + items +
                    '}';
        }

        public class GroupOneItem implements Serializable {
            private final String TAG = GroupOneItem.class.getSimpleName();

            private long id;
            private String name;
            private String photo_50;
            private String screen_name;

            private String access_token;

            public String getTAG() {
                return TAG;
            }

            public long getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getPhoto_50() {
                return photo_50;
            }

            public String getScreenName() {
                return screen_name;
            }

            public String getAccessToken() {
                return access_token;
            }

            public void setAccessToken(String access_token) {
                this.access_token = access_token;
            }

            @Override
            public String toString() {
                return "GroupOneItem{" +
                        "TAG='" + TAG + '\'' +
                        ", id=" + id +
                        ", name='" + name + '\'' +
                        ", photo_50='" + photo_50 + '\'' +
                        ", screen_name='" + screen_name + '\'' +
                        ", access_token='" + access_token + '\'' +
                        '}';
            }
        }
    }
}
