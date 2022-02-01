package es.clb.quarkus;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;


@Path("/greeting")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class GreetingResource {

  @GET
  @Operation(summary = "Returns all the greetings")
  @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Greeting.class, type = SchemaType.ARRAY)))
  @APIResponse(responseCode = "204", description = "No Greetings")
  @Counted(name = "countGetGreetings")
  @Timed(name = "timeGetGreetings", unit = MetricUnits.MILLISECONDS)
  public Response getGreetings() {

    List<Greeting> greetings = Greeting.listAll();
    if (greetings.isEmpty()) {
      return Response.noContent().build();
    }
    return Response.ok(greetings).build();
  }

  @GET
  @Path("/{id}")
  @Operation(summary = "Returns Greeting by ID")
  @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Greeting.class, type = SchemaType.OBJECT)))
  @APIResponse(responseCode = "404", description = "Greeting not Found")
  public Response getGreetingById(@PathParam("id") Long id) {
    Greeting greeting = Greeting.findById(id);
    if (greeting == null) {
      return Response.status(Status.NOT_FOUND.getStatusCode(), "Greeting not Found").build();
    }
    return Response.ok(greeting).build();
  }

  @POST
  @Operation(summary = "Create Greeting")
  @APIResponse(responseCode = "201")
  @Transactional
  public Response createGreeting(@Valid Greeting greeting) {
    greeting.persist();
    return Response.created(URI.create("/api/Greetings/" + greeting.getId())).build();

  }

  @PUT
  @Path("/{id}")
  @Operation(summary = "Update Greeting by ID")
  @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Greeting.class, type = SchemaType.OBJECT)))
  @APIResponse(responseCode = "404", description = "Greeting not Found")
  @Transactional
  public Response updateGreeting(@PathParam("id") Long id, @Valid Greeting greeting) {
    Greeting existingGreeting = Greeting.findById(id);
    if (existingGreeting == null) {
      return Response.status(Status.NOT_FOUND.getStatusCode(), "Greeting not Found").build();
    }
    existingGreeting.setValue(greeting.getValue());
    return Response.ok(existingGreeting).build();
  }

  @DELETE
  @Path("/{id}")
  @Operation(summary = "Delete Greeting by ID")
  @APIResponse(responseCode = "204")
  @APIResponse(responseCode = "404", description = "Greeting not Found")
  @Transactional
  public Response deleteGreeting(@PathParam("id") Long id) {

    boolean deleted = Greeting.deleteById(id);
    return deleted ? Response.noContent().build() : Response.status(Status.NOT_FOUND.getStatusCode()).build();

  }
}
