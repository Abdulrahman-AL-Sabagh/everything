package everything.service

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped


@ApplicationScoped
class UserRepository : PanacheRepository<User>
