package es.clb.quarkus;

import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


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

    List<Greeting> greetings = new ArrayList<Greeting>();
    greetings.add(new Greeting(1, "Hello from Quarkus!"));
    greetings.add(new Greeting(2, "Hola, bienvenudo a Quarkus!"));
    return Response.ok(greetings).build();
  }
}
