package budgetplanner

class BootStrap {

    def init = { servletContext ->
        User user = new User(
                name: "admin",
                username: "admin",
                birthDate: new Date(),
                password: "secret"
        )
        user.save(flush:true)
        Role role = new Role(authority: Role.ROLE_ADMIN)
        role.save(flush:true)
        UserRole.create(user,role)

        user = new User(
                name: "user",
                username: "user",
                birthDate: new Date(),
                password: "secret"
        )
        user.save(flush:true)
        role = new Role(authority: Role.ROLE_USER)
        role.save(flush:true)
        UserRole.create(user,role)
    }
    def destroy = {
    }
}
