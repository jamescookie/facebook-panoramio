package com.jamescookie.facebook

class PanoramioUser {
    String facebookId
    String panoramioId

    static constraints = {
        facebookId(unique:true, blank:false, nullable:false)
        panoramioId(unique:true, blank:false, nullable:false)
    }

    public String toString() {
        return "PanoramioUser{" +
                "facebookId='" + facebookId + '\'' +
                ", panoramioId='" + panoramioId + '\'' +
                '}';
    }
}
