package es.clb.quarkus;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import es.clb.quarkus.model.Weather;

@Path("/forecast")
@RegisterRestClient(configKey="weather-api")
public interface WeatherService {

  @GET
  @Path("")
  Weather getWeather(
    @QueryParam("latitude") Double latitude,
    @QueryParam("longitude") Double longitudee,
    @QueryParam("hourly") String hourly,
    @QueryParam("current_weather") Boolean current);

}