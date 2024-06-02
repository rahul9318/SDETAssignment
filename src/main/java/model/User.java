package model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private Address address;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address {
        private Geo geo;

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Geo {
            private String lat;
            private String lng;

            public String getLat() {
                return lat;
            }

            public String getLng() {
                return lng;
            }
        }

        public Geo getGeo() {
            return geo;
        }
    }

    public int getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }
}

