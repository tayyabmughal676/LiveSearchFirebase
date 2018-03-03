package com.example.mrtayyab.livesearchfirebase

/**
 * Created by MrTayyab on 2/24/2018.
 */
class User {


    /// MOdel class
    var name : String? = null
    var status : String? = null
    var image : String? = null

    constructor(){

    }

    constructor(name: String?, status: String?, image: String?) {
        this.name = name
        this.status = status
        this.image = image
    }
}