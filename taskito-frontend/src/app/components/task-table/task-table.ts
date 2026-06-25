import { Component, Input } from '@angular/core';
import { Task } from '../../interfaces/task';
import { DatePipe } from '@angular/common';
import { TaskService } from '../../services/task-service';

@Component({
  selector: 'app-task-table',
  imports: [DatePipe],
  templateUrl: './task-table.html',
  styleUrl: './task-table.css',
})
export class TaskTable {
  @Input() tasks!: Task[];



  constructor(private taskService: TaskService) {}

  onChecked(task: Task): void {
    task.completed = !task.completed;
    let response= this.taskService.update(task);
    response.subscribe({
      next: () => { return; },
      error: () => {
        console.log("Error while updating task");
        task.completed = !task.completed;
        return;
      }
    });
  }
}
