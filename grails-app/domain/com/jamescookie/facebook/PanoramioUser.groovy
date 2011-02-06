package com.jamescookie.facebook

class PanoramioUser {
    String facebookId
    String panoramioId

    static hasOne = [geoPreference:GeoPreference]

    static constraints = {
        facebookId(unique:true, blank:false, nullable:false)
        panoramioId(blank:false, nullable:false)
        geoPreference(nullable:true)
    }

    public String toString() {
        return "PanoramioUser{" +
                "facebookId='" + facebookId + '\'' +
                ", panoramioId='" + panoramioId + '\'' +
                '}';
    }
}
