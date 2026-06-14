import {inject, Service} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Task} from '../interfaces/task';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Service()
export class TaskService {
  private readonly API_ROOT = environment.API_ROOT;
  private readonly http: HttpClient;

  constructor() {
    this.http = inject(HttpClient);
  }

  get(id: string): Observable<Task> {
    return this.http.get<Task>(this.API_ROOT + "/tasks/" + id);
  }

  getAll(): Observable<Task[]> {
    let tasks =  this.http.get<Task[]>(this.API_ROOT + "/tasks");

    if (!tasks) {
      throw new Error("No tasks found");
    }

    return tasks;
  }

  create(description: string) {
    return this.http.post(this.API_ROOT + "/tasks", {"description": description});
  }
}
