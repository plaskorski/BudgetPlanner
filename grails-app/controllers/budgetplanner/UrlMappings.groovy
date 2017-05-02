package budgetplanner

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }


        "/"(view:"/index")
        "/about"(view:"/about")
        "/blog"(view:"/blog")
        "/tutorial"(view:"/tutorial")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
