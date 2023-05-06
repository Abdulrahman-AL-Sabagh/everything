package everything.service

import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.Response.Status
import org.jboss.logging.Logger
import kotlin.math.log


@Path("/api/users")
class UserResource {

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var logger: Logger

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUserById(@PathParam("id") id: Long): Response {
        val user = userRepository.findById(id)
        logger.log(Logger.Level.INFO, user)
        val responseBuilder = Response.status(Status.OK.statusCode)
        responseBuilder.entity(user);

        return responseBuilder.build()

    }


    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun createUser(user: User): Response {
        userRepository.persist(user)
        val responseBuilder = Response.status(Response.Status.CREATED)
        responseBuilder.entity(user)
        return responseBuilder.build()
    }
}