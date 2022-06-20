package es.clb.quarkus;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import es.clb.quarkus.model.Weather;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;


@Path("/weather")
public class WeatherResource {

  @Inject
  @RestClient
  WeatherService weatherService;

  @Operation(summary = "Returns Madrid current temperature")
  @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Weather.class, type = SchemaType.OBJECT)))
  @APIResponse(responseCode = "500", description = "Error retrieving temperature")
  @Counted(name = "countGetMadridTemperature")
  @Timed(name = "timeGetMadridTemperature", unit = MetricUnits.MILLISECONDS)
  @GET()
  @Path("/madrid")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMadridTemperature() {
    try {
      Weather response = weatherService.getWeather(40.4167,-3.7033,"temperature_2m", true);
      return Response.ok(response).build();  
    } catch (Exception e) {
      return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Greeting not Found").build();
    }
  }
}