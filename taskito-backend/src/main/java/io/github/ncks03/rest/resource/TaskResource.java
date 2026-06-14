package io.github.ncks03.rest.resource;

import io.github.ncks03.controller.TaskController;
import io.github.ncks03.model.Task;
import io.github.ncks03.rest.dto.post.TaskPostDTO;
import io.github.ncks03.rest.dto.response.TaskResponseDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/tasks")
public class TaskResource {

    private final TaskController controller;

    @Inject
    public TaskResource(TaskController controller) {
        this.controller = controller;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTasks() {
        List<Task> tasks = controller.getTasks();

        if (tasks.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(tasks.stream().map(TaskResponseDTO::fromTask).toList()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTask(@PathParam("id") UUID id) {
        Optional<Task> found = controller.getTask(id);

        System.out.println(found);

        if (found.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(TaskResponseDTO.fromTask(found.get())).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTask(TaskPostDTO task) {
        Task newTask = new Task(task.description());

        controller.addTask(newTask);

        return Response.created(URI.create("/tasks/" + newTask.getId())).build();
    }
}
