import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ProjectDto} from "../common/project-dto";
import {User} from "../common/user";


@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private apiUrl = 'http://localhost:8080/api/project'; // Base URL for the API

  constructor(private http: HttpClient) {}

  // Method to create HTTP headers with authorization token
  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('accessToken');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  // Method to get all projects
  getAllProjects(): Observable<ProjectDto[]> {

    return this.http.get<ProjectDto[]>(`http://localhost:8080/api/project/list-all`, {
      headers: this.getHeaders()
    });
  }
  getAllProjectsByManager(): Observable<ProjectDto[]> {
    return this.http.get<ProjectDto[]>(`http://localhost:8080/api/project/list-all/manager`, {
      headers: this.getHeaders()
    });
  }

  // Method to create a new project
  createProject(projectDto: ProjectDto): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}`, projectDto, {
      headers: this.getHeaders()
    });
  }

  // Method to update an existing project
  updateProject(id: number, projectDto: ProjectDto): Observable<void> {
    console.log(projectDto)
    return this.http.put<void>(` http://localhost:8080/api/project/edit/${id}`, projectDto, {
      headers: this.getHeaders()
    });
  }

  // Method to delete a project
  deleteProject(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, {
      headers: this.getHeaders()
    });
  }

  // Method to mark a project as complete
  completeProject(id: number): Observable<void> {
    return this.http.get<void>(`${this.apiUrl}/complete/${id}`, {
      headers: this.getHeaders()
    });
  }

  getProjectById(projectId: number):Observable<ProjectDto>  {
    return this.http.get<ProjectDto>(`http://localhost:8080/api/project/list-all/${projectId}` ,{headers: this.getHeaders()})
  }
}
