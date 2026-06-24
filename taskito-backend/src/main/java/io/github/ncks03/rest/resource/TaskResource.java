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
import java.util.Comparator;
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
    public Response getAllTasks(@QueryParam("limit") Optional<Integer> limit) {
        List<TaskResponseDTO> tasks;

        tasks = limit.map(intLimit -> controller.getTasks().stream()
                .map(TaskResponseDTO::fromTask)
                .sorted(Comparator.comparing(TaskResponseDTO::dateCreated))
                .limit(intLimit)
                .toList()
        ).orElseGet(() -> controller.getTasks().stream()
                .map(TaskResponseDTO::fromTask)
                .sorted(Comparator.comparing(TaskResponseDTO::dateCreated))
                .toList()
        );

        if (tasks.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(tasks).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTask(@PathParam("id") UUID id) {
        Optional<Task> found = controller.getTask(id);

        if (found.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(TaskResponseDTO.fromTask(found.get())).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTask(@PathParam("id") UUID id, TaskPostDTO dto) {
        Optional<Task> found = controller.getTask(id);

        if (found.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Task task = found.get();

        task.setCompleted(dto.completed());
        task.setDescription(dto.description());

        this.controller.update();

        return Response.created(URI.create("/tasks/" + task.getId())).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTasks(List<TaskPostDTO> tasks) {
        List<Task> newTasks = tasks.stream()
                .map(task -> new Task(task.description()))
                .toList();

        this.controller.addTasks(newTasks);

        return Response.created(URI.create("/tasks")).build();
    }

    @DELETE
    @Path("/delete")
    public Response removeTask(@QueryParam("id") UUID id) {
        this.controller.removeTask(id);

        return Response.noContent().build();
    }
}
