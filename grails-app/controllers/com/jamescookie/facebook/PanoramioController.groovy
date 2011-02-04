package com.jamescookie.facebook

class PanoramioController {

    def index = { }

    def checkUser = {
        def user = PanoramioUser.findByFacebookId(params.id)
        if (user) {
            chain(action: 'displayMap', id: user.facebookId)
        } else {
            render(view: 'setUser', model: [user: new PanoramioUser(facebookId: params.id)])
        }
    }

    def updateUser = {
        def user = PanoramioUser.findByFacebookId(params.id)
        if (user) {
            user.properties = params
        } else {
            user = new PanoramioUser(params)
        }

        render(view: 'setUser', model: [user: user])
    }

    def displayMap = {

    }
}
