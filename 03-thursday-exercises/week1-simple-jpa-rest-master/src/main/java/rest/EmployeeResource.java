package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Employee;
import facades.EmployeeFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("employee")
public class EmployeeResource {

    //NOTE: Change Persistence unit name according to your setup
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    EmployeeFacade facade = EmployeeFacade.getFacadeExample(emf);

    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllEmployees() {
        List<Employee> employees = facade.getAllEmployees();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(employees);

    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getEmployeeById(@PathParam("id") Long id) {
            Employee emp = facade.getEmployeeById(id);
            return new Gson().toJson(emp);
    }

    @Path("highestpaid")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getHighestPaidEmployee() {
        Employee employees = facade.getEmployeeWithHighestSalary();
        return new Gson().toJson(employees);
    }
    
    @Path("name/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getEmployeeById(@PathParam("name") String name) {
            List<Employee> emp = facade.getEmployeesByName(name);
            return new Gson().toJson(emp);
    }
    
    
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"succes\"}";
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Employee entity) {
        throw new UnsupportedOperationException();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void update(Employee entity, @PathParam("id") int id) {
        throw new UnsupportedOperationException();
    }
}
