import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {TaskDto} from "../common/task-dto";



@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private apiUrl = 'http://localhost:8080/api/task'; // Base URL for the API

  constructor(private http: HttpClient) {}

  // Method to create HTTP headers with authorization token
  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('accessToken');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  // Method to get all tasks
  getAllTasks(): Observable<TaskDto[]> {
    return this.http.get<TaskDto[]>(`${this.apiUrl}/all-tasks`, {
      headers: this.getHeaders()
    });
  }

  // Method to get a task by ID
  getTaskById(id: number): Observable<TaskDto> {
    return this.http.get<TaskDto>(`${this.apiUrl}/${id}`, {
      headers: this.getHeaders()
    });
  }

  // Method to create a new task
  createTask(taskDto: TaskDto): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/create`, taskDto, {
      headers: this.getHeaders()
    });
  }

  // Method to update an existing task
  updateTask(id: number, taskDto: TaskDto): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/edit/${id}`, taskDto, {
      headers: this.getHeaders()
    });
  }

  // Method to delete a task
  deleteTask(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`, {
      headers: this.getHeaders()
    });
  }

  // Method to get all not completed tasks (status is not COMPLETE)
  getNotCompletedTasks(): Observable<TaskDto[]> {
    return this.http.get<TaskDto[]>(`${this.apiUrl}/not-completed`, {
      headers: this.getHeaders()
    });
  }

  // Method to get all completed tasks (status is COMPLETE)
  getCompletedTasks(): Observable<TaskDto[]> {
    return this.http.get<TaskDto[]>(`${this.apiUrl}/completed`, {
      headers: this.getHeaders()
    });
  }
}
