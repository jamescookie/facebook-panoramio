package com.jamescookie.facebook

class PanoramioController {

    static allowedMethods = [updateUser: "POST"]

    def index = { }

    def checkUser = {
        def user = PanoramioUser.findByFacebookId(params.id)
        if (user) {
            chain(action: 'displayMap', model:[user:user])
        } else {
            render(view: 'setUser', model: [user: new PanoramioUser(facebookId: params.id)])
        }
    }

    def setUser = {
        [user:PanoramioUser.findByFacebookId(params.id)]
    }

    def changeMapLocation = {
        //todo stuff
        println params
        render "success"
    }

    def updateUser = {
        def user = PanoramioUser.findByFacebookId(params.facebookId)
        if (user) {
            user.properties = params
        } else {
            user = new PanoramioUser(params)
        }
        if (!user.hasErrors() && user.save(flush: true)) {
            chain(action: 'displayMap', model:[user:user])
        } else {
            render(view: 'setUser', model: [user: user])
        }

    }

    def displayMap = {
        [user:chainModel.user]
    }
}
