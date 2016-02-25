package groups.vk.test.api.model.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupWallList implements Serializable {
    private static final String TAG = GroupWallList.class.getSimpleName();

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

        private Integer count;
        private List<WallOneItem> items;

        public Integer getCount() {
            return count;
        }

        public List<WallOneItem> getItems() {
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

        public class WallOneItem implements Serializable {
            private final String TAG = WallOneItem.class.getSimpleName();

            private long id;
            private long from_id;
            private String text;

            private List<Attachments> attachments;

            public long getId() {
                return id;
            }

            public long getFromId() {
                return from_id;
            }

            public String getText() {
                return text;
            }

            public List<Attachments> getAttachments() {
                return attachments;
            }

            @Override
            public String toString() {
                return "WallOneItem{" +
                        "TAG='" + TAG + '\'' +
                        ", id=" + id +
                        ", from_id=" + from_id +
                        ", text='" + text + '\'' +
                        ", attachments=" + attachments +
                        '}';
            }

            public class Attachments implements Serializable {
                private final String TAG = Attachments.class.getSimpleName();

                private String type; // photo // link // video

                private Photo  photo;
                private Link   link;
                private Video  video;

                public String getType() {
                    return type;
                }

                public Photo getPhoto() {
                    return photo;
                }

                public Link getLink() {
                    return link;
                }

                public Video getVideo() {
                    return video;
                }

                @Override
                public String toString() {
                    return "Attachments{" +
                            "TAG='" + TAG + '\'' +
                            ", type='" + type + '\'' +
                            ", photo=" + photo +
                            ", link=" + link +
                            ", video=" + video +
                            '}';
                }

                public class Photo implements Serializable {
                    private final String TAG = Photo.class.getSimpleName();

                    private Long   id;
                    private String photo_130;

                    public Long getId() {
                        return id;
                    }

                    public String getPhoto() {
                        return photo_130;
                    }

                    @Override
                    public String toString() {
                        return "Photo{" +
                                "TAG='" + TAG + '\'' +
                                ", id=" + id +
                                ", photo_130='" + photo_130 + '\'' +
                                '}';
                    }
                }

                public class Link implements Serializable {
                    private final String TAG = Photo.class.getSimpleName();

                    private String url;
                    private String title;

                    public String getUrl() {
                        return url;
                    }

                    public String getTitle() {
                        return title;
                    }

                    @Override
                    public String toString() {
                        return "Link{" +
                                "TAG='" + TAG + '\'' +
                                ", url='" + url + '\'' +
                                ", title='" + title + '\'' +
                                '}';
                    }
                }

                public class Video implements Serializable {
                    private final String TAG = Video.class.getSimpleName();

                    private String title;
                    private String photo_130;

                    public String getTitle() {
                        return title;
                    }

                    public String getPhoto() {
                        return photo_130;
                    }

                    @Override
                    public String toString() {
                        return "Video{" +
                                "TAG='" + TAG + '\'' +
                                ", title='" + title + '\'' +
                                ", photo_130='" + photo_130 + '\'' +
                                '}';
                    }
                }
            }
        }
    }
}
