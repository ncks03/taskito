import {Component, Input} from '@angular/core';
import {Task} from '../../interfaces/task';

@Component({
  selector: 'app-task-table',
  imports: [],
  templateUrl: './task-table.html',
  styleUrl: './task-table.css',
})
export class TaskTable {
  @Input() tasks!: Task[];
}
