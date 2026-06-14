import {ChangeDetectionStrategy, Component} from '@angular/core';
import {TaskTable} from '../../components/task-table/task-table';
import {TaskService} from '../../services/task-service';
import {Task} from '../../interfaces/task';

@Component({
  selector: 'app-task-page',
  imports: [
    TaskTable
  ],
  templateUrl: './task-page.html',
  styleUrl: './task-page.css',
  changeDetection: ChangeDetectionStrategy.Eager
})
export class TaskPage {

  tasks?: Task[];
  error?: Error;

  constructor(private taskService: TaskService) {
    this.taskService.getAll().subscribe({
      next: tasks => this.tasks = tasks,
      error: err => this.error = err,
    });
  }
}
