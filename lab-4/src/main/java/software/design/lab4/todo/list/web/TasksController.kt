package software.design.lab4.todo.list.web

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import software.design.lab4.todo.list.domain.model.list.TodoListId
import software.design.lab4.todo.list.domain.model.task.Task
import software.design.lab4.todo.list.domain.model.task.TaskId
import software.design.lab4.todo.list.domain.model.task.TaskStatus
import software.design.lab4.todo.list.domain.repository.TaskRepository
import software.design.lab4.todo.list.domain.repository.TodoListRepository
import software.design.lab4.todo.list.web.dto.TaskCreateRqDto
import software.design.lab7.profiler.api.Profiled

@Controller
class TasksController @Autowired constructor(
    private val todoListRepository: TodoListRepository,
    private val tasksRepository: TaskRepository,
) {

    @Profiled
    @GetMapping("/tasks")
    fun getTasks(
        @RequestParam("todoListId") listIdParam: Long,
        model: Model,
    ): String {
        logger.info { "Getting tasks for list with id = $listIdParam" }
        val listId = TodoListId(listIdParam)
        val list = todoListRepository.getTodoList(listId)
        val tasks = tasksRepository.getTasks(listId).toList()
        model.addAttribute("list", list)
        model.addAttribute("tasks", tasks)
        model.addAttribute("taskCreateRq", TaskCreateRqDto("", ""))
        return "tasks"
    }

    @Profiled
    @PostMapping("/tasks/create")
    fun createTask(
        @RequestParam("todoListId") listIdParam: Long,
        @ModelAttribute taskCreateRq: TaskCreateRqDto,
        model: Model,
    ): String {
        logger.info { "Creating tasks in list with id = $listIdParam" }
        tasksRepository.createTask(
            Task(
                listId = TodoListId(listIdParam),
                title = taskCreateRq.title,
                description = taskCreateRq.description,
            ),
        )
        return getTasks(listIdParam, model)
    }

    @Profiled
    @PostMapping("/tasks/patch")
    fun patchTask(
        @RequestParam("todoListId") listIdParam: Long,
        @RequestParam("taskId") taskIdParam: Long,
        model: Model,
    ): String {
        logger.info { "Patching tasks in list with id = $listIdParam" }
        val task = tasksRepository.getTask(TaskId(taskIdParam))
        tasksRepository.updateTask(
            task.copy(
                status = if (task.status == TaskStatus.ACTIVE) TaskStatus.FINISHED else TaskStatus.ACTIVE,
            ),
        )
        return getTasks(listIdParam, model)
    }

    @Profiled
    @PostMapping("/tasks/delete")
    fun deleteTask(
        @RequestParam("todoListId") listIdParam: Long,
        @RequestParam("taskId") taskIdParam: Long,
        model: Model,
    ): String {
        logger.info { "Deleting tasks in list with id = $listIdParam" }
        tasksRepository.deleteTask(TaskId(taskIdParam))
        return getTasks(listIdParam, model)
    }

    companion object {
        val logger = KotlinLogging.logger { }
    }
}
