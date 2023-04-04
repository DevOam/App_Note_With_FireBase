package com.nottes

import android.icu.text.CaseMap.Title
import java.util.Date
class Notte{
    var id:String ?=null
    var title:String ?=null
    var notte:String ?=null
    var timestamp:String ?=null
    constructor()
    constructor(id:String, title:String, notte: String, timestamp:String) {
        this.id=id
        this.title=title
        this.notte=notte
        this.timestamp=timestamp

    }
}