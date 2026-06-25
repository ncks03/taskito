import { Component, OnInit, signal, WritableSignal } from '@angular/core';
import {TaskTable} from '../../components/task-table/task-table';
import {TaskService} from '../../services/task-service';
import {Task} from '../../interfaces/task';
import { Spinner } from '../../components/spinner/spinner';

@Component({
  selector: 'app-task-page',
  imports: [TaskTable, Spinner],
  templateUrl: './task-page.html',
  styleUrl: './task-page.css',
})
export class TaskPage {
  loading = signal<boolean>(true);

  tasks?: Task[];
  error?: Error;

  constructor(private taskService: TaskService) {
    this.taskService.getAll().subscribe({
      next: (tasks) => {
        this.tasks = tasks;
        this.loading.set(false);
      },
      error: (err) => {
        this.error = err;
        this.loading.set(false);
      },
    });
  }
}
